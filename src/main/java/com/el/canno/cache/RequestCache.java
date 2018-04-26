package com.el.canno.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * User Canno
 * Date 2018/4/20
 * Time 11:41
 */
public class RequestCache {
    private static Map reqCacheMap = new HashMap();

    /**
     * 设置缓存
     *
     * @param cacheKey 缓存key
     * @param timeout 超时时间
     * @return 是否设置成功，true, false
     */
    public static boolean putCache(String cacheKey, long timeout){
        if(reqCacheMap.get(cacheKey) != null){
            if(System.currentTimeMillis() - (Long) reqCacheMap.get(cacheKey) < timeout){
                return false;
            }
        }else {
            reqCacheMap.put(cacheKey, System.currentTimeMillis());
        }

        return true;
    }

    public static synchronized boolean isDuplicate(String key, long timeout){
        return putCache(key,timeout);
    }
}
