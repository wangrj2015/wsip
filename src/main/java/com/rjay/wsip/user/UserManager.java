package com.rjay.wsip.user;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by wangrenjie on 17/11/27.
 */
public class UserManager {

    private static final Map<String,String> USER_MAP = Maps.newHashMap();

    static{
        USER_MAP.put("rj","rj");
        USER_MAP.put("001","001");
        USER_MAP.put("002","002");
        USER_MAP.put("003","003");
        USER_MAP.put("004","004");
        USER_MAP.put("005","005");
        USER_MAP.put("006","006");
        USER_MAP.put("007","007");
    }

    public static String getPassWord(String userName){
        return USER_MAP.get(userName);
    }
}
