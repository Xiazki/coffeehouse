package com.coffeehouse.login.service;

import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.CoffeerTaken;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.login.dao.ICoffeerDao;
import com.coffeehouse.login.dao.ICoffeerTakenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CoffeerService {

    @Autowired
    private ICoffeerDao coffeerDao;

    @Autowired
    private CoffeerTakenService coffeerTakenService;

    public Map<String,Object> register(Coffeer coffeer) throws Exception {

        Map<String,Object> ret = new HashMap<String, Object>();

        if(getCoffeerByName(coffeer.getName())!=null){
            ret.put("errMsg","该用户名已经被注册！");
            return ret;
        }

        String salt =  UUID.randomUUID().toString()
                .replace("-","").substring(0,5);

        coffeer.setPassword(CoffeerUtil.MD5(coffeer.getPassword() + salt));
        coffeer.setSalt(salt);
        coffeer.setHeadUrl("test");
        Long coffeerId = (Long)coffeerDao.save(coffeer);
        coffeer.setId(coffeerId);
        String ct = addCt(coffeer);

        ret.put("ct",ct);

        return ret;
    }

    public Map<String,Object> login(Coffeer coffeer) throws Exception {
        Map<String,Object> ret = new HashMap<String, Object>();

        Coffeer coRes = getCoffeerByName(coffeer.getName());

        if(coRes == null){
            ret.put("errMsg","用户不存在！");
            return ret;
        }

        if(!coRes.getPassword().equals(CoffeerUtil.MD5(coffeer.getPassword()+coRes.getSalt()))){
            ret.put("errMsg","用户密码错误！");
            return ret;
        }
        //验证成功，下发登陆凭证
        String ct = addCt(coRes);
        ret.put("ct",ct);

        return ret;
    }

    /**
     * 获得匿名用户
     * @return
     * @throws Exception
     */
    public Coffeer getAnonymousUser() throws Exception {
        return coffeerDao.get(Coffeer.class,13L);
    }
    public void logout(Coffeer coffeer){
        coffeerTakenService.updateStatus(coffeer.getId());
    }
    public Coffeer getCoffeerById(Long coffeerId) throws Exception{
        return coffeerDao.get(Coffeer.class,coffeerId);
    }

    public Coffeer getCoffeerByName(String name) throws Exception {

        return coffeerDao.getSingleQueryData(Coffeer.class," o.name = ?",name);

    }
    private String addCt(Coffeer coffeer) throws Exception {

        CoffeerTaken ct = new CoffeerTaken();
        ct.setStatus(1);
        Date exproationDate = new Date();
        exproationDate.setTime(exproationDate.getTime()+3600*24*10*1000);
        ct.setExpriationDate(exproationDate);
        ct.setCreateDate(new Date());
        String taken  = UUID.randomUUID().toString().replace("-","");
        ct.setTaken(taken);
        ct.setCoffeer(coffeer);
        coffeerTakenService.addCt(ct);
        return taken;

    }


}
