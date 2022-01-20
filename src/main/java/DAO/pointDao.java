/**
 * 1972002 - Kevin Juan
 */
package DAO;

import Model.FePointEntity;
import Utility.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class pointDao implements daoInterface<FePointEntity>{
    @Override
    public int addData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int delData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int updateData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public ObservableList<FePointEntity> showData() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(FePointEntity.class);

        query.from(FePointEntity.class);

        List<FePointEntity> clist = s.createQuery(query).getResultList();
        s.close();

        return FXCollections.observableArrayList(clist);
    }
}
