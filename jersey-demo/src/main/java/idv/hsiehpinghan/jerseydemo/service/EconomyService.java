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

import idv.hsiehpinghan.jerseydemo.vo.GdpVo;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.QGrossDomesticProductCurrentPricesEntity;

@Service
@Repository
@Transactional
public class EconomyService {
	private QGrossDomesticProductCurrentPricesEntity qEntity = QGrossDomesticProductCurrentPricesEntity.grossDomesticProductCurrentPricesEntity;
	@Autowired
	private SessionFactory sessionFactory;

	public GdpVo findOne(GrossDomesticProductCurrentPricesId id) {
		Session session = sessionFactory.getCurrentSession();
		GrossDomesticProductCurrentPricesEntity entity = getHibernateQueryFactory(session).selectFrom(qEntity)
				.where(qEntity.id.eq(id)).fetchOne();
		return convertToGdpVo(entity);
	}

	public List<GdpVo> findAll() {
		Session session = sessionFactory.getCurrentSession();
		List<GrossDomesticProductCurrentPricesEntity> entities = getHibernateQueryFactory(session).selectFrom(qEntity)
				.fetch();
		List<GdpVo> vos = new ArrayList<>(entities.size());
		for (GrossDomesticProductCurrentPricesEntity entity : entities) {
			vos.add(convertToGdpVo(entity));
		}
		return vos;
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
}
