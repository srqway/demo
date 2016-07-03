package idv.hsiehpinghan.jpademo.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import idv.hsiehpinghan.jpademo.entity.GrossDomesticProductCurrentPricesEntity;
import idv.hsiehpinghan.jpademo.entity.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.entity.QGrossDomesticProductCurrentPricesEntity;

@Service
@Repository
@Transactional
public class GrossDomesticProductCurrentPricesServiceMock {
	private QGrossDomesticProductCurrentPricesEntity qEntity = QGrossDomesticProductCurrentPricesEntity.grossDomesticProductCurrentPricesEntity;
	@Autowired
	private SessionFactory sessionFactory;
	//

	public void save(GrossDomesticProductCurrentPricesEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(entity);
	}

	public GrossDomesticProductCurrentPricesEntity findOne(GrossDomesticProductCurrentPricesId id) {
		Session session = sessionFactory.getCurrentSession();
		return getHibernateQueryFactory(session).selectFrom(qEntity).where(qEntity.id.eq(id)).fetchOne();

	}

	private HibernateQueryFactory getHibernateQueryFactory(Session session) {
		return new HibernateQueryFactory(session);
	}

}
