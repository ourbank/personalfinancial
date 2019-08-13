package com.icbc.index.util;



import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WordCutUtil {

    private static final String APP_KEY = "kQDUFIQOpbU1Y7IzQMMb76Zf";
    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private static final String SECRET_KEY = "LW8n6nmdHknqNEBo6lfBQsfEOTQS9R97";
    private static String CUID = "16736267";
    private static String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";
    private static String accessToken;

    //通用版分词url
    //private static String URL = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer";

    //定制版分词url
    private static String URL = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer_custom";

    public static void init(){
        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, "",TOKEN_URL);
        if(accessToken == null || accessToken.equals("")){
            try {
                holder.refresh();
                accessToken = holder.getToken();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DemoException e) {
                e.printStackTrace();
            }
            //System.out.println("Get AccessToken of Baidu Word API SUCCESS");
        }
    }


    public static String cutWord(String str){
        String result = null;
        try {
            result = runJsonPostMethod(accessToken,str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String  runJsonPostMethod(String token,String beingCutWord) throws DemoException, IOException {
        String url2 = URL + "?charset=UTF-8&access_token=" + token;
        JSONObject params = new JSONObject();
        params.put("text", beingCutWord);
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json");
        //System.out.println(params.toString());
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);
        return result;
    }


}
