package com.coffeehouse.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coffeehouse.common.expection.NotFoundExpection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiang on 2017/2/13.
 */
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    //返回json 使用fastjson
    protected String getJSONString(int code){
        return getJSONString(code,null);
    }

    protected <T> String getJSONString(int code,T data){
        return getJSONString(code,"",data);
    }

    protected String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }

    protected <T> String getJSONString(int code,String msg,T data){
        JSONObject ret = new JSONObject();
        ret.put("code",code);
        ret.put("reMsg",msg);
        ret.put("data",data);
        //禁止循环引用检测
        return JSON.toJSONString(ret,SerializerFeature.DisableCircularReferenceDetect);
    }

    //异常处理
    /**
     * @param e
     * @return
     * 异常处理
     * 重定向
     * 异常处理 跳转异常 ajax返回错误信息，以及跳转到404或或者500页面
     */

    @ExceptionHandler(Exception.class)
    protected void expection(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        logger.error(e.getMessage());
        response.sendError(500);

    }

    /**
     *
     * @param e
     */
    @ExceptionHandler(NotFoundExpection.class)
    protected void notFoundExpection(NotFoundExpection e){
        //do something
    }

}
