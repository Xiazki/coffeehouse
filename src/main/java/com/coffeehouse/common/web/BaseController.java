package com.coffeehouse.common.web;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by xiang on 2017/2/13.
 */
public class BaseController {

    /**
     *
     * @param e
     * @return
     * 异常处理
     */
    @ExceptionHandler
    protected String expection(Exception e){
        return null;
    }

}
