package lewandowski.demo.Utilities;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;

public abstract class BaseDaoSupport<T> extends HibernateDaoSupport {

    private Class<T> type;

    public BaseDaoSupport(Class<T> type) {
        super();

        this.type = type;
    }

    public void save(T object) {
        try {
            getHibernateTemplate().saveOrUpdate(object);
        } catch(DataAccessException e) {
            getHibernateTemplate().merge(object);
        }
    }

    public void insert(T object) {
        getHibernateTemplate().save(object);
    }

    public void update(T object) {
        getHibernateTemplate().update(object);
    }

    public void merge(T object) {
        getHibernateTemplate().merge(object);
    }

    public void persist(T object) {
        getHibernateTemplate().persist(object);
    }

    public void delete(int id) {
        delete(find(id));
    }

    public void delete(T object) {
        getHibernateTemplate().delete(object);
    }

  /*  public void mandatoryDelete(long id) {
        delete(mandatoryFind(id));
    }
*/

    public T find(int id) {
        return getHibernateTemplate().get(type, id);
    }
/*
    public T mandatoryFind(long id) {
        T o = find(id);
        if(o == null) throw new DomainObjectNotFoundException(type, id);
        return o;
    }*/

    public List<T> findAll() {
        return (List<T>) getHibernateTemplate().find(
                new StringBuilder().append("from ")
                        .append(type.getName()).toString());
    }
}