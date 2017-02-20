package com.coffeehouse.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiang on 2017/2/16.
 */
public class ViewObject {
    private Map<String,Object> map = new HashMap<String, Object>();

    public void set(String key,Object o){
        map.put(key,o);
    }
    public Object get(String key){
        return map.get(key);
    }
}
