package com.loavne.wsip.util;

import java.util.Map;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class MapUtils {

    public static void copy(Map<String,String> from, Map<String,String> to, ValueFilter valueFilter, String... keys){
        for(String key : keys){
            to.put(key,valueFilter.newValue(key, from.get(key)));
        }
    }

    public interface ValueFilter{

        String newValue(String key,String value);
    }
}
