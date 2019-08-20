package com.icbc.index.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.index.mapper.CardDao;
import com.icbc.index.mapper.PredictDao;
import com.icbc.index.model.CardData;
import com.icbc.index.model.CoreInQuerySQL;
import com.icbc.index.util.TimeUtil;
import com.icbc.index.vo.PredictDataVo;
import com.icbc.index.vo.PredictScaleVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PredictService {

    @Resource
    PredictDao predictDao;

    @Resource
    CardDao cardDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisService redisService;

    /**
     * 将拿到的数据封装成json格式，暂时使用测试数据
     *
     * @param period
     * @param buss
     * @return
     */
    public String getScaleByPeriod(String period, String buss) {
        List<PredictScaleVo> scale = predictDao.getScaleByPeriod(period, buss);
        return JSON.toJSONString(scale);
    }

    public String getdefaultpredict() {
        // 这里先写测试数据
        List<PredictDataVo> out = new ArrayList<>();
        List<Double> list = new ArrayList<>();
        list.add((double) 7888);
        list.add((double) 8000);
        list.add((double) 8031);
        list.add((double) 9000);
        list.add((double) 9333);
        list.add((double) 9987);
        list.add((double) 10231);
        list.add((double) 10553);
        out.add(new PredictDataVo("开卡数", "广州分行", "season", list));
        return JSON.toJSONString(out);
    }

    /**
     * 用于预测详情页 多城市展示
     * @param business 业务，支持单一
     * @param period 预测时间长度
     * @param unit  预测时间单位
     * @param bankNames 银行名称
     * @return
     */
    public String getPredict(String business, int period, String unit, List<String> bankNames) {
        List<PredictDataVo> out = new ArrayList<>();
        String trainData = getTrainData(period, unit, business, bankNames);
        System.out.println(trainData);
        String result = trainByPython(trainData);
        JSONArray jsonArray = JSONArray.parseArray(result);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<Double> list = JSONObject.parseArray(jsonObject.getString("predicted"), Double.class);
            out.add(new PredictDataVo(business, jsonObject.getString("bankname"), unit, list));
        }
        return JSON.toJSONString(out);
    }

    /**
     * 通过业务进行预测，默认时间为6个月，展示银行默认为广州分行
     * 用于主页 较小界面 展示
     * @param business
     * @return
     */
    public String getSimplePredictByBus(String business) {
        List<PredictDataVo> out = new ArrayList<>();
        // 一些值为默认
        List<String> bankNames = new ArrayList<>();
        bankNames.add("广州分行");
        int period = 6;
        String unit = "month";
        String trainData = getTrainData(period, unit, business, bankNames);
        String result = trainByPython(trainData);
        JSONArray ja = JSONArray.parseArray(result);
        List<Double> list = JSONObject.parseArray(ja.getJSONObject(0).getString("predicted"), Double.class);
        out.add(new PredictDataVo(business, "广州分行", "month", list));
        return JSON.toJSONString(out);
    }

    /**
     * 返回json格式的请求（发送给python服务）
     * @param period  预测时长
     * @param unit 预测时间单位
     * @param business 预测业务
     * @param bankNames 银行的名称
     * @return
     */
    private String getTrainData(int period, String unit, String business, List<String> bankNames) {
        List<Map> out = new ArrayList<>();
        for (String bankName : bankNames) {
            if (redisService.getObj(bankName + "_" + business + "_traindata") != null) {
                String obj = (String) redisService.getObj(bankName + "_" + business + "_traindata");
                Map map = (Map) JSONObject.parseObject(obj);
                map.put("period",period);
                out.add(map);
            } else {
                List<Map> list = drawDefaultMap(bankNames, business, period, unit);
                CoreInQuerySQL msql = new CoreInQuerySQL("2009-01-01", "2019-12-31", business, bankNames);
                List<CardData> train_data = cardDao.getCountByBankName(msql);
                for (Map map : list) {
                    List<Double> parse_train_data = new ArrayList<>();
                    List<String> parse_train_time = new ArrayList<>();
                    for (int i = 0; i < train_data.size(); i++) {
                        if (map.get("bankname").equals(train_data.get(i).getBankName())) {
                            parse_train_data.add(train_data.get(i).getCardNum());
                            parse_train_time.add(TimeUtil.dateToFormatStr(train_data.get(i).getTime(), "yyyy-MM-dd"));
                        }
                    }
                    map.put("traindata", parse_train_data);
                    map.put("traintime", parse_train_time);
                    redisService.setObj(map.get("bankname") + "_" + business + "_traindata", JSON.toJSONString(map), 24 * 60);
                }
                return JSON.toJSONString(list);
            }
        }
        return JSON.toJSONString(out);

    }

    private List<Map> drawDefaultMap(List<String> bankNames, String business, int period, String unit) {
        List<Map> out = new ArrayList<>();
        for (String bankName : bankNames) {
            Map map = new HashMap();
            List<Double> parse_train_data = new ArrayList<>();
            List<String> parse_train_time = new ArrayList<>();
            map.put("bankname", bankName);
            map.put("business", business);
            map.put("unit", unit);
            map.put("period", period);
            map.put("traindata", parse_train_data);
            map.put("traintime", parse_train_time);
            out.add(map);
        }
//        System.out.println(JSON.toJSONString(out));
        return out;
    }

    /**
     * 连接python并获取预测数据
     *
     * @param trainData json格式的请求，包含训练数据和一些相关参数
     * @return json格式的返回值
     * return 样例：
     *         [{"predicted": [870.0, 59.0, 721.37, 703.09, 686.64, 651.53, 654.08, 613.18], "bankname": "深圳分行"},
     *         {"predicted": [889.0, 62.0, 851.86, 834.13, 853.93, 823.61, 844.04, 824.81], "bankname": "广州分行"},
     *        {"predicted": [545.0, 40.0, 522.32, 524.21, 517.59, 515.73, 521.04, 506.08], "bankname": "揭阳分行"}]
     */
    private String trainByPython(String trainData) {
        System.out.println("------开始与python服务连接------");
        String url = "http://127.0.0.1:8000/predict/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(trainData, headers);
        ResponseEntity<String> request = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println("------获得预测数据------");
        System.out.println(request.getBody());
        return request.getBody();
    }


}
