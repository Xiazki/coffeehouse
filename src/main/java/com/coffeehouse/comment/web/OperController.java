package com.coffeehouse.comment.web;

import com.coffeehouse.comment.service.CommentService;
import com.coffeehouse.comment.service.OperService;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.event.EventCustomer;
import com.coffeehouse.common.event.EventProducer;
import com.coffeehouse.common.event.EventType;
import com.coffeehouse.common.event.eventimpl.EventMoudle;
import com.coffeehouse.common.event.eventimpl.LikeEvent;
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

    /**
     *
     * @param entityId 为评论和回答实体Id,非数据库中entity_id
     * @param type
     * @param enAtti
     * @return
     * @throws Exception
     */
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
