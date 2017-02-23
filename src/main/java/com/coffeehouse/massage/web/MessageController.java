package com.coffeehouse.massage.web;

import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Message;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.web.BaseController;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.massage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    @Autowired
    private CoffeerService coffeerService;

    @RequestMapping(path = "/mess/send")
    @ResponseBody
    public String sendMessage(@RequestParam(name = "coffeerName") String coffeerName,
                              @RequestParam(name = "content") String content) throws Exception {
        Coffeer coffeer = coffeerService.getCoffeerByName(coffeerName);
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();
        Message message = new Message();
        message.setFromCoffeer(local);
        message.setToCoffeer(coffeer);
        message.setContent(content);
        messageService.sendMessage(message);
        return getJSONString(CoffeerUtil.SUCCESS);
    }

    @RequestMapping(path = "/mess/receive/list")
    @ResponseBody
    public String receiveMessList(@RequestParam(name = "status", defaultValue = "true") boolean status,
                                  @RequestParam(name = "type") String type) throws Exception {
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();
        List<Message> messList = messageService.getReceiveMessageList(local.getId(), status, type);
        return getJSONString(CoffeerUtil.SUCCESS, messList);
    }

    @RequestMapping(path = "mess/send/list")
    @ResponseBody
    public String sendMessList(@RequestParam(name = "coffeerName") String coffeerName,
                               @RequestParam(name = "content") String content) throws Exception {
       return null;
    }

    @RequestMapping(path = "mess/his")
    @ResponseBody
    public String getMessSession(@RequestParam(name = "fromId") Long fromId) throws Exception {
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();
        List<Message> messList = messageService.getmessageSessionList(fromId,local.getId());
        return getJSONString(CoffeerUtil.SUCCESS,messList);
    }

}
