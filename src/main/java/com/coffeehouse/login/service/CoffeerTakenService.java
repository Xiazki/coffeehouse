package com.coffeehouse.login.service;

import com.coffeehouse.common.entity.CoffeerTaken;
import com.coffeehouse.login.dao.ICoffeerTakenDao;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CoffeerTakenService {

    @Autowired
    private ICoffeerTakenDao coffeerTakenDao;

    public void addCt(CoffeerTaken ct) throws Exception {
        coffeerTakenDao.save(ct);
    }

    public CoffeerTaken getCtByTaken(String taken) throws Exception {
        String hqlwhere = " o.taken = ?";
        return coffeerTakenDao.getSingleQueryData(CoffeerTaken.class,hqlwhere,taken);
    }

    public void updateStatus(Long coffeerId){
        String sql = "update coffeer_taken t set t.status = '0' where t.coffeer_id=?";
        Query query = coffeerTakenDao.createQuery(sql,true,coffeerId);
        query.executeUpdate();
    }
}
