package com.coffeehouse.common.dao.idao;

import java.util.List;

/**
 * Created by xiang on 2017/2/13.
 */
public interface ISQLBaseDao {

    public List<Object[]> excuteQuery(String sql);

    public List<Object[]>excuteQuery(String sql,int firstPage,int maxResult);

    public List<Object[]> excuteQuery(String sql,int firstPage,int maxResult,Object... pars);

    public int excuteUpdate(String sql);

    public int excutUpdate(String sql,Object... pars);


}