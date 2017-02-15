package com.coffeehouse.common.web;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by xiang on 2017/2/13.
 */
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected String getJSONString(int code){
        return getJSONString(code,null);
    }

    protected String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }

    protected <T> String getJSONString(int code,String msg,T data){
        JSONObject ret = new JSONObject();
        ret.put("code",code);
        ret.put("reMsg",msg);
        ret.put("data",data);
        return ret.toString();
    }
    /**
     * @param e
     * @return
     * 异常处理
     * 重定向
     */
    @ExceptionHandler
    protected void expection(Exception e){
        e.printStackTrace();
        logger.error(e.getMessage());
    }

}
