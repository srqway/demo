package idv.hsiehpinghan.crawlerdemo.service;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity.EuroNationalCurrencyExchangeRatesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.QEuroNationalCurrencyExchangeRatesEntity;

@Service
@Repository
@Transactional
public class EuroNationalCurrencyExchangeRatesService {
	private QEuroNationalCurrencyExchangeRatesEntity qEntity = QEuroNationalCurrencyExchangeRatesEntity.euroNationalCurrencyExchangeRatesEntity;
	@Autowired
	private SessionFactory sessionFactory;

	public void saveOrUpdate(EuroNationalCurrencyExchangeRatesEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		EuroNationalCurrencyExchangeRatesId id = entity.getId();
		EuroNationalCurrencyExchangeRatesEntity oldEntity = getHibernateQueryFactory(session).selectFrom(qEntity)
				.where(qEntity.id.eq(id)).fetchOne();
		if (oldEntity == null) {
			session.persist(entity);
		} else {
			oldEntity.setUpdatedDt(entity.getUpdatedDt());
			oldEntity.setUpdatedBy(entity.getUpdatedBy());
			oldEntity.setValue(entity.getValue());
			session.update(oldEntity);
		}
	}

	public void saveOrUpdate(Collection<EuroNationalCurrencyExchangeRatesEntity> entities) {
		Session session = sessionFactory.getCurrentSession();
		for (EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			EuroNationalCurrencyExchangeRatesId id = entity.getId();
			EuroNationalCurrencyExchangeRatesEntity oldEntity = getHibernateQueryFactory(session).selectFrom(qEntity)
					.where(qEntity.id.eq(id)).fetchOne();
			if (oldEntity == null) {
				session.persist(entity);
			} else {
				oldEntity.setUpdatedDt(entity.getUpdatedDt());
				oldEntity.setUpdatedBy(entity.getUpdatedBy());
				oldEntity.setValue(entity.getValue());
				session.persist(oldEntity);
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EuroNationalCurrencyExchangeRatesEntity findOne(EuroNationalCurrencyExchangeRatesId id) {
		Session session = sessionFactory.getCurrentSession();
		return getHibernateQueryFactory(session).selectFrom(qEntity).where(qEntity.id.eq(id)).fetchOne();

	}

	private HibernateQueryFactory getHibernateQueryFactory(Session session) {
		return new HibernateQueryFactory(session);
	}

}
