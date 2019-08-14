package com.icbc.index.util;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class VoiceRecognitionUtil {
    private static final boolean METHOD_RAW = false; // 默认以json方式上传音频文件
    //  填写网页上申请的 appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private static final String APP_KEY = "z8byo4jFgqSDs1vy2fTOPfhC";
    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private static final String SECRET_KEY = "xy5g4bcOmaabSu5eHntUTfiRmjoLFMkq";
    private static String CUID = "16697658";
    // 采样率固定值
    private static final int RATE = 16000;
    private static String URL;
    private static int DEV_PID;
    private static String SCOPE;
    private static String TOKEN_URL;
    private static String accessToken;

    //  免费版 参数

    static {
        TOKEN_URL = "http://openapi.baidu.com/oauth/2.0/token";
        URL = "http://vop.baidu.com/server_api"; // 可以改为https
        //  1537 表示识别普通话，使用输入法模型。1536表示识别普通话，使用搜索模型。 其它语种参见文档
        DEV_PID = 1537;
        SCOPE = "audio_voice_assistant_get";
    }


//    /* 付费极速版 参数*/
//    {
//        URL =   "http://vop.baidu.com/pro_api"; // 可以改为https
//        DEV_PID = 80001;
//        SCOPE = "brain_enhanced_asr";
//    }

    public static void init(String token){
        accessToken = token;
    }

    public static String getToken(){
        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE,TOKEN_URL);
        try {
            holder.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        }
        return holder.getToken();
    }

    public static String voiceToStr(String fileName) {
        String result = null;
            try {
                if (METHOD_RAW) {
                    result = runRawPostMethod(accessToken,fileName);}
                else
                {
                    result = runJsonPostMethod(accessToken,fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DemoException e) {
                e.printStackTrace();
            }
        return result;
    }

    private static String runRawPostMethod(String token,String fileName) throws IOException, DemoException {
        String format = fileName.substring(fileName.length() - 3);
        String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&token=" + token;
        String contentTypeStr = "audio/" + format + "; rate=" + RATE;
        byte[] content = getFileContent(fileName);
        /*HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", contentTypeStr);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(content);
        conn.getOutputStream().close();*/
        RestTemplate template=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentTypeStr));
        HttpEntity<String> httpEntity=new HttpEntity<>(new String(content),headers);
        JSONObject object = template.postForObject(url2, httpEntity, JSONObject.class);
        System.out.println("url is " + url2);
        System.out.println("header is  " + "Content-Type :" + contentTypeStr);
        if (object!=null){
            return (String) object.get("result");
        }
        return null;
    }

    public static String  runJsonPostMethod(String token,String fileName) throws DemoException, IOException {
        String format = fileName.substring(fileName.length() - 3);
        byte[] content = getFileContent(fileName);
        String speech = base64Encode(content);
        JSONObject params = new JSONObject();
        params.put("dev_pid", DEV_PID);
        params.put("format", format);
        params.put("rate", RATE);
        params.put("token", token);
        params.put("cuid", CUID);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);
        RestTemplate template=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=utf-8"));
        HttpEntity<String> entity=new HttpEntity<>(params.toJSONString(),headers);
        JSONObject object = template.postForObject(URL, entity, JSONObject.class);
        if (object!=null){
           String result= (String) object.getJSONArray("result").get(0);
           return result;
        }

        return null;
    }

    private static byte[]  getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String base64Encode(byte[] content) {

         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);


//        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
//        String str = new String(chars);

        return str;
    }
}
