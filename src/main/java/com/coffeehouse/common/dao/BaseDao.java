package com.coffeehouse.common.dao;

import com.coffeehouse.common.dao.idao.IBaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import java.util.LinkedHashMap;
import java.util.List;

public class BaseDao<T> implements IBaseDao<T> {


    @Autowired
    protected SessionFactory sessionFactory;

    public T get(Class<T> var1, Long id) throws Exception {
        return this.getCurrentSesson().get(var1, id);
    }

    public void save(T entity) throws Exception {
        this.getCurrentSesson().save(entity);
    }

    public void update(T entity) throws Exception {
        this.getCurrentSesson().update(entity);
    }

    public void saveOrUpdate(T entity) throws Exception {
        this.saveOrUpdate(entity);
    }

    public void delete(T entity) throws Exception {
        this.delete(entity);
    }

    public T merge(T entiry) throws Exception {
        return null;
    }

    public void delete(Long id) throws Exception {

    }

    public T getSingleQueryData(Class<T> var1,String hqlwhere,Object... pars) throws Exception{
        String hql = "select o from " + getClassName(var1) + " o " + (hqlwhere == null ? "" : "where " + hqlwhere);
        return (T) this.createQuery(hql,false,pars).uniqueResult();
    }

    public List<T> getQueryData(Class<T> var1) throws Exception {
        return this.getQueryData(var1, null);
    }

    public List<T> getQueryData(Class<T> var1, String hqlWhere, Object... pars) throws Exception {
        return this.getQueryData(var1,null,hqlWhere,pars);
    }

    public List<T> getQueryData(Class<T> var1, LinkedHashMap<String, String> orderBy) throws Exception {
        return this.getQueryData(var1, orderBy, null,null);
    }


    public List<T> getQueryData(Class<T> var1, LinkedHashMap<String, String> orderBy, String hqlWhere, Object... pars) {
        String hql = "select o from " + getClassName(var1) + " o " + (hqlWhere == null ? "" : "where " + hqlWhere) + getOrderBy(orderBy);
        Query query = this.createQuery(hql,false,pars);
        return query.list();
    }

    public List<T> getScrollData(Class<T> var1) throws Exception {
        return null;
    }

    public List<T> getScrollData(Class<T> var1, int firstPage, int maxResult) {
        return null;
    }

    public List<T> getScrollData(Class<T> var1, int firstPage, int maxResult, String sqlWhere, Object... pars) throws Exception {
        return null;
    }

    public List<Object[]> excuteQuery(String sql) {
        return null;
    }

    public List<Object[]> excuteQuery(String sql, int firstPage, int maxResult) {
        return null;
    }

    public List<Object[]> excuteQuery(String sql, int firstPage, int maxResult, Object... pars) {
        return null;
    }

    public int excuteUpdate(String sql) {
        return 0;
    }

    public int excutUpdate(String sql, Object... pars) {
        return 0;
    }

    protected Session getCurrentSesson() {
        return this.sessionFactory.getCurrentSession();
    }

    public String getClassName(Class<T> var1) {
        String entityName = var1.getSimpleName();
        Entity entity = var1.getAnnotation(Entity.class);
        if (entity.name() != null && !"".equals(entity.name())) {
            entityName = entity.name();
        }
        return entityName;
    }

    public String getOrderBy(LinkedHashMap<String, String> orderBy) {
        StringBuilder strBuilder = new StringBuilder("");
        if (orderBy != null && orderBy.size() > 0) {
            strBuilder.append(" order by ");
            for (String key : orderBy.keySet()) {
                strBuilder.append(orderBy.get(key)).append(" ").append((String) orderBy.get(key)).append(",");
            }
            strBuilder.deleteCharAt(strBuilder.length() - 1);
        } else {
            strBuilder.append(" order by o.createTime ");
        }
        return strBuilder.toString();
    }

    //(+1s)
    public Query createQuery(String ql,Boolean isNaive,Object... pars) {
        Query query = null;
        if(isNaive){
            query = this.getCurrentSesson().createSQLQuery(ql);
        }else{
            query = this.getCurrentSesson().createQuery(ql);
        }
        if(pars!=null&& pars.length>0){
            for(int i= 0;i<pars.length;i++){
                query.setParameter(i,pars[i]);
            }
        }
        return query;
    }

    @Deprecated
    public SQLQuery createSQLQuery(String sql, Object... pars) {
        SQLQuery sqlQuery = this.getCurrentSesson().createSQLQuery(sql);
        if(pars!=null&& pars.length>0){
            for(int i= 0;i<pars.length;i++){
                sqlQuery.setParameter(i,pars[i]);
            }
        }
        return sqlQuery;
    }
}