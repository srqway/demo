package idv.hsiehpinghan.crawlerdemo.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.QGrossDomesticProductCurrentPricesEntity;

@Service
@Repository
@Transactional
public class GrossDomesticProductCurrentPricesService {
	private QGrossDomesticProductCurrentPricesEntity qEntity = QGrossDomesticProductCurrentPricesEntity.grossDomesticProductCurrentPricesEntity;
	@Autowired
	private SessionFactory sessionFactory;

	public void saveOrUpdate(GrossDomesticProductCurrentPricesEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		GrossDomesticProductCurrentPricesId id = entity.getId();
		GrossDomesticProductCurrentPricesEntity oldEntity = getHibernateQueryFactory(session).selectFrom(qEntity)
				.where(qEntity.id.eq(id)).fetchOne();
		if (oldEntity == null) {
			session.saveOrUpdate(entity);
		} else {
			oldEntity.setUpdatedDt(entity.getUpdatedDt());
			oldEntity.setUpdatedBy(entity.getUpdatedBy());
			oldEntity.setValue(entity.getValue());
			session.update(oldEntity);
		}
	}

	public GrossDomesticProductCurrentPricesEntity findOne(GrossDomesticProductCurrentPricesId id) {
		Session session = sessionFactory.getCurrentSession();
		return getHibernateQueryFactory(session).selectFrom(qEntity).where(qEntity.id.eq(id)).fetchOne();

	}

	private HibernateQueryFactory getHibernateQueryFactory(Session session) {
		return new HibernateQueryFactory(session);
	}

}
