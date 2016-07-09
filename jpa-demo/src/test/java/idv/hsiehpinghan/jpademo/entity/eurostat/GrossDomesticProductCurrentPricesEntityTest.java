package idv.hsiehpinghan.jpademo.entity.eurostat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import idv.hsiehpinghan.jpademo.configuration.SpringConfiguration;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.service.GrossDomesticProductCurrentPricesServiceMock;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { SpringConfiguration.class })
public class GrossDomesticProductCurrentPricesEntityTest {
	private final Date CREATED_DT = new Date();
	private final String CREATED_BY = "createdBy";
	private final Date UPDATED_DT = new Date();
	private final String UPDATED_BY = "updatedBy";
	private final String QUARTER = "quarter";
	private final String AREA = "area";
	private final BigDecimal VALUE = BigDecimal.ONE;
	@Autowired
	private GrossDomesticProductCurrentPricesServiceMock service;

	@Test
	public void a_save() {
		service.save(generateGrossDomesticProductCurrentPricesEntity());
	}

	@Test
	public void b_findOne() {
		GrossDomesticProductCurrentPricesId id = generateGrossDomesticProductCurrentPricesId();
		GrossDomesticProductCurrentPricesEntity entity = service.findOne(id);
		Assert.assertEquals(entity.getId().getQuarter(), QUARTER);
		Assert.assertEquals(entity.getId().getArea(), AREA);
		Assert.assertEquals(VALUE.compareTo(entity.getValue()), 0);
	}

	private GrossDomesticProductCurrentPricesEntity generateGrossDomesticProductCurrentPricesEntity() {
		GrossDomesticProductCurrentPricesId id = generateGrossDomesticProductCurrentPricesId();
		GrossDomesticProductCurrentPricesEntity entity = new GrossDomesticProductCurrentPricesEntity(CREATED_DT,
				CREATED_BY, UPDATED_DT, UPDATED_BY, id, VALUE);
		return entity;
	}

	private GrossDomesticProductCurrentPricesId generateGrossDomesticProductCurrentPricesId() {
		GrossDomesticProductCurrentPricesId id = new GrossDomesticProductCurrentPricesId(QUARTER, AREA);
		return id;
	}
}
