package com.el.canno.common;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

/**
 * Created by Ak_Guili on 2017/6/21.
 */
public class HttpClientUtils {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);
    public static String sendHttpRequest(String url, Object data) {
        HttpEntity<Object> entity = new HttpEntity<Object>(data);
        RestTemplate restTemplate = new RestTemplate();
        try {
            long b = System.currentTimeMillis();
            ResponseEntity<byte[]> ret = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);
            LOG.info("res:{}",new String(ret.getBody(), "utf-8"));
            return new String(ret.getBody(), "utf-8");
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
