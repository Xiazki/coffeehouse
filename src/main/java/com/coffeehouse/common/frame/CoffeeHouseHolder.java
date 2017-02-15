package com.coffeehouse.common.frame;


import com.coffeehouse.common.entity.Coffeer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CoffeeHouseHolder implements ApplicationContextAware{

    private static ThreadLocal<Coffeer> localCoffeer = new ThreadLocal<Coffeer>();
    private static ThreadLocal<HashMap<String,Object>> contextHolder = new ThreadLocal<HashMap<String, Object>>();

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.context = applicationContext;
    }

    public void setLocalCoffeer(Coffeer coffeer){
        localCoffeer.set(coffeer);
    }

    public Coffeer getLocalCoffeer(){
        return localCoffeer.get();
    }

    public void clear(){
        localCoffeer.remove();
    }

    public void set(String key,Object object){
        if(contextHolder.get()==null){
            contextHolder.set(new HashMap<String, Object>());
        }
        contextHolder.get().put(key,object);
    }

    public Object get(String key){
        if(contextHolder.get()==null){
            contextHolder.set(new HashMap<String, Object>());
            return null;
        }
        return contextHolder.get().get(key);
    }
}
