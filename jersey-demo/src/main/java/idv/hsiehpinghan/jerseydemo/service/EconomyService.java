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

import idv.hsiehpinghan.jerseydemo.vo.EconomyVo;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity;
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

	public EconomyVo getGdpEconomyVo() {
		Session session = sessionFactory.getCurrentSession();
		List<GrossDomesticProductCurrentPricesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qGrossDomesticProductCurrentPricesEntity).fetch();
		return convertToGdpEconomyVo(entities);
	}

	public EconomyVo getExchangeRateEconomyVo() {
		Session session = sessionFactory.getCurrentSession();
		List<EuroNationalCurrencyExchangeRatesEntity> entities = getHibernateQueryFactory(session)
				.selectFrom(qEuroNationalCurrencyExchangeRatesEntity).fetch();
		return convertToExchangeRateEconomyVo(entities);
	}

	private HibernateQueryFactory getHibernateQueryFactory(Session session) {
		return new HibernateQueryFactory(session);
	}

	private EconomyVo convertToGdpEconomyVo(List<GrossDomesticProductCurrentPricesEntity> entities) {
		List<String> legends = getGdpLegends(entities);
		List<String> xAxises = getGdpXAxises(entities);
		List<List<BigDecimal>> data = getGdpData(entities);
		return new EconomyVo(legends, xAxises, data);
	}

	private List<String> getGdpLegends(List<GrossDomesticProductCurrentPricesEntity> entities) {
		List<String> legends = new ArrayList<>();
		String oldArea = entities.get(0).getId().getArea();
		legends.add(oldArea);
		String newArea = null;
		for(GrossDomesticProductCurrentPricesEntity entity : entities) {
			newArea = entity.getId().getArea();
			if(newArea.equals(oldArea) == false) {
				legends.add(newArea);
				oldArea = newArea;
			}
		}
		return legends;
	}
	
	private List<String> getGdpXAxises(List<GrossDomesticProductCurrentPricesEntity> entities) {
		List<String> xAxises = new ArrayList<>();
		String oldArea = entities.get(0).getId().getArea();
		String newArea = null;
		for(GrossDomesticProductCurrentPricesEntity entity : entities) {
			newArea = entity.getId().getArea();
			if(newArea.equals(oldArea) == false) {
				break;
			}
			xAxises.add(entity.getId().getQuarter());
		}
		return xAxises;
	}
	
	private List<List<BigDecimal>> getGdpData(List<GrossDomesticProductCurrentPricesEntity> entities) {
		List<List<BigDecimal>> data = new ArrayList<>();
		String oldArea = entities.get(0).getId().getArea();
		String newArea = null;
		List<BigDecimal> values = new ArrayList<>();
		for(GrossDomesticProductCurrentPricesEntity entity : entities) {
			newArea = entity.getId().getArea();
			if(newArea.equals(oldArea) == false) {
				values = new ArrayList<>();
				data.add(values);
				oldArea = newArea;
			}
			values.add(entity.getValue());
		}
		return data;
	}
	
	private EconomyVo convertToExchangeRateEconomyVo(List<EuroNationalCurrencyExchangeRatesEntity> entities) {
		List<String> legends = getExchangeRateLegends(entities);
		List<String> xAxises = getExchangeRateXAxises(entities);
		List<List<BigDecimal>> data = getExchangeRateData(entities);
		return new EconomyVo(legends, xAxises, data);
	}
	
	private List<String> getExchangeRateLegends(List<EuroNationalCurrencyExchangeRatesEntity> entities) {
		List<String> legends = new ArrayList<>();
		String oldCurrency = entities.get(0).getId().getCurrency();
		legends.add(oldCurrency);
		String newCurrency = null;
		for(EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			newCurrency = entity.getId().getCurrency();
			if(newCurrency.equals(oldCurrency) == false) {
				legends.add(newCurrency);
				oldCurrency = newCurrency;
			}
		}
		return legends;
	}
	
	private List<String> getExchangeRateXAxises(List<EuroNationalCurrencyExchangeRatesEntity> entities) {
		List<String> xAxises = new ArrayList<>();
		String oldCurrency = entities.get(0).getId().getCurrency();
		String newCurrency = null;
		for(EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			newCurrency = entity.getId().getCurrency();
			if(newCurrency.equals(oldCurrency) == false) {
				break;
			}
			xAxises.add(entity.getId().getYearMonth());
		}
		return xAxises;
	}
	
	private List<List<BigDecimal>> getExchangeRateData(List<EuroNationalCurrencyExchangeRatesEntity> entities) {
		List<List<BigDecimal>> data = new ArrayList<>();
		String oldCurrency = entities.get(0).getId().getCurrency();
		String newCurrency = null;
		List<BigDecimal> values = new ArrayList<>();
		for(EuroNationalCurrencyExchangeRatesEntity entity : entities) {
			newCurrency = entity.getId().getCurrency();
			if(newCurrency.equals(oldCurrency) == false) {
				values = new ArrayList<>();
				data.add(values);
				oldCurrency = newCurrency;
			}
			values.add(entity.getValue());
		}
		return data;
	}
}
