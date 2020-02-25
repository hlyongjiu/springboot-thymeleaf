package com.learn.springboot.virtual;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class PubaccUtil {

    public static JSONObject genernateFrom(String no, String pubId, String pubsercet, String nonce, String time) {
        JSONObject jsonFrom = new JSONObject();
        jsonFrom.put("no", no);
        jsonFrom.put("pub", pubId);
        jsonFrom.put("nonce", nonce);
        jsonFrom.put("time", time);
        String pubtoken = sha(no, pubId, pubsercet, nonce, time);
        jsonFrom.put("pubtoken", pubtoken);
        return jsonFrom;
    }

    private static String sha(String... data) {
        Arrays.sort(data);
        String join = StringUtils.join(data);
        String pubtoken = DigestUtils.sha1Hex(join);
        return pubtoken;
    }

    public static void main(String[] args) {
        String no = "18139194";
        String pubId = "XT-0dc6a9a5-5b74-4d79-8a2a-c71c04729b98";
        String pubsercet = "4c9828a62dbf8b9c8204e5705349c88a";
        String nonce = "f1e38986-989d-4ad0-99d5-131d2bcc33cb";
        String time = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObject = genernateFrom(no, pubId, pubsercet, nonce, time);
        System.out.println(jsonObject);
    }
}