package idv.hsiehpinghan.jerseydemo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import idv.hsiehpinghan.jerseydemo.vo.ExchangeRateVo;
import idv.hsiehpinghan.jerseydemo.vo.GdpVo;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity.EuroNationalCurrencyExchangeRatesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.QEuroNationalCurrencyExchangeRatesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.QGrossDomesticProductCurrentPricesEntity;

@Service
@Repository
@Transactional
public class EconomyService {
	private QGrossDomesticProductCurrentPricesEntity qGrossDomesticProductCurrentPricesEntity = QGrossDomesticProductCurrentPricesEntity.grossDomesticProductCurrentPricesEntity;
	private QEuroNationalCurrencyExchangeRatesEntity qEuroNationalCurrencyExchangeRatesEntity = QEuroNationalCurrencyExchangeRatesEntity.euroNationalCurrencyExchangeRatesEntity;
	@Autowired
	private SessionFactory sessionFactory;

	public List<GdpVo> getAllGdps() {
		Session session = sessionFactory.getCurrentSession();
		List<GrossDomesticProductCurrentPricesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qGrossDomesticProductCurrentPricesEntity).fetch();
		List<GdpVo> vos = new ArrayList<>(entities.size());
		for (GrossDomesticProductCurrentPricesEntity entity : entities) {
			vos.add(convertToGdpVo(entity));
		}
		return vos;
	}

	public List<GdpVo> getGdpsByArea(String area) {
		Session session = sessionFactory.getCurrentSession();
		List<GrossDomesticProductCurrentPricesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qGrossDomesticProductCurrentPricesEntity)
				.where(qGrossDomesticProductCurrentPricesEntity.id.area.eq(area)).fetch();
		List<GdpVo> vos = new ArrayList<>(entities.size());
		for (GrossDomesticProductCurrentPricesEntity entity : entities) {
			vos.add(convertToGdpVo(entity));
		}
		return vos;
	}

	public GdpVo getGdpById(GrossDomesticProductCurrentPricesId id) {
		Session session = sessionFactory.getCurrentSession();
		GrossDomesticProductCurrentPricesEntity entity = getHibernateQueryFactory(session)
				.selectFrom(qGrossDomesticProductCurrentPricesEntity)
				.where(qGrossDomesticProductCurrentPricesEntity.id.eq(id)).fetchOne();
		return convertToGdpVo(entity);
	}

	public List<ExchangeRateVo> getAllExchangeRates() {
		Session session = sessionFactory.getCurrentSession();
		List<EuroNationalCurrencyExchangeRatesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qEuroNationalCurrencyExchangeRatesEntity).fetch();
		List<ExchangeRateVo> vos = new ArrayList<>(entities.size());
		for (EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			vos.add(convertToExchangeRateVo(entity));
		}
		return vos;
	}

	public List<ExchangeRateVo> getExchangeRatesByCurrency(String currency) {
		Session session = sessionFactory.getCurrentSession();
		List<EuroNationalCurrencyExchangeRatesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qEuroNationalCurrencyExchangeRatesEntity)
				.where(qEuroNationalCurrencyExchangeRatesEntity.id.currency.eq(currency)).fetch();
		List<ExchangeRateVo> vos = new ArrayList<>(entities.size());
		for (EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			vos.add(convertToExchangeRateVo(entity));
		}
		return vos;
	}

	public ExchangeRateVo getExchangeRateById(EuroNationalCurrencyExchangeRatesId id) {
		Session session = sessionFactory.getCurrentSession();
		EuroNationalCurrencyExchangeRatesEntity entity = getHibernateQueryFactory(session)
				.selectFrom(qEuroNationalCurrencyExchangeRatesEntity)
				.where(qEuroNationalCurrencyExchangeRatesEntity.id.eq(id)).fetchOne();
		return convertToExchangeRateVo(entity);
	}

	private HibernateQueryFactory getHibernateQueryFactory(Session session) {
		return new HibernateQueryFactory(session);
	}

	private GdpVo convertToGdpVo(GrossDomesticProductCurrentPricesEntity entity) {
		String quarter = entity.getId().getQuarter();
		String area = entity.getId().getArea();
		BigDecimal value = entity.getValue();
		return new GdpVo(quarter, area, value);
	}

	private ExchangeRateVo convertToExchangeRateVo(EuroNationalCurrencyExchangeRatesEntity entity) {
		String yearMonth = entity.getId().getYearMonth();
		String currency = entity.getId().getCurrency();
		BigDecimal value = entity.getValue();
		return new ExchangeRateVo(yearMonth, currency, value);
	}
}
