package com.coffeehouse.comment.web;

import com.coffeehouse.comment.service.OperService;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OperController extends BaseController{

    @Autowired
    private OperService operService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;


    @RequestMapping(path = "/opr/atti")
    @ResponseBody
    public String attitude(@RequestParam(name = "entityId") Long entityId,
                           @RequestParam(name = "type") String type,
                           @RequestParam(name = "enAtti") boolean enAtti) throws Exception {
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();

        if(local == null){
            return getJSONString(CoffeerUtil.FAIL,"登陆后才能执行操作");
        }
        Long[] retCount = operService.saveAtti(local.getId(),entityId,type,enAtti);

        return getJSONString(CoffeerUtil.SUCCESS,retCount);
    }

}
