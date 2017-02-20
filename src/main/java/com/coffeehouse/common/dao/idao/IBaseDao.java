package com.coffeehouse.common.dao.idao;

import org.hibernate.Query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public interface IBaseDao<T> extends ISQLBaseDao{

    //base method
    //强制checkedExpection
    public T get(Class<T> var1,Long id)throws Exception;

    public Serializable save(T entity) throws Exception;

    public void update(T entity) throws Exception;

    public void saveOrUpdate(T entity) throws Exception;

    public void delete(T entity) throws Exception;

    public T merge(T entiry) throws Exception;

    //delete by id
    public void delete(Long id) throws Exception;

    //public List<T> getScrollData() throws Exception;

    public T getSingleQueryData(Class<T> var1,String hqlwhere,Object... pars) throws Exception;

    public List<T> getQueryData(Class<T> var1) throws Exception;

    public List<T> getQueryData(Class<T> var1, String hqlWhere, Object... pars) throws Exception;

    public List<T> getQueryData(Class<T> var1,LinkedHashMap<String,String> orderBy) throws Exception;

    public List<T> getQueryData(Class<T> var1,LinkedHashMap<String,String> orderBy,String hqlWhere,final Object... pars) throws Exception;

    public List<T> getScrollData(Class<T> var1) throws Exception;

    public List<T> getScrollData(Class<T> var1,int firstPage,int maxResult) throws Exception;

    public List<T> getScrollData(Class<T> var1,int firstPage,int maxResult,String sqlWhere,final Object... pars) throws Exception;

    public Query createQuery(String ql, Boolean isNaive, Object... pars);
}