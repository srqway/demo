package idv.hsiehpinghan.jpademo.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import idv.hsiehpinghan.jpademo.configuration.SpringConfiguration;
import idv.hsiehpinghan.jpademo.entity.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;
import idv.hsiehpinghan.jpademo.service.GrossDomesticProductCurrentPricesServiceMock;

@ContextConfiguration(classes = { SpringConfiguration.class })
public class GrossDomesticProductCurrentPricesEntityTest extends AbstractTestNGSpringContextTests {
	private final Date CREATED_DT = new Date();
	private final String CREATED_BY = "createdBy";
	private final Date UPDATED_DT = new Date();
	private final String UPDATED_BY = "updatedBy";
	private final String QUARTER = "quarter";
	private final String AREA = "area";
	private final BigDecimal DATA = BigDecimal.ONE;
	@Autowired
	private GrossDomesticProductCurrentPricesServiceMock service;

	@Test
	public void save() {
		service.save(generateGrossDomesticProductCurrentPricesEntity());
	}

	@Test(dependsOnMethods = "save")
	public void findOne() {
		GrossDomesticProductCurrentPricesId id = generateGrossDomesticProductCurrentPricesId();
		GrossDomesticProductCurrentPricesEntity entity = service.findOne(id);
		Assert.assertEquals(entity.getId().getQuarter(), QUARTER);
		Assert.assertEquals(entity.getId().getArea(), AREA);
		Assert.assertEquals(DATA.compareTo(entity.getData()), 0);
	}

	private GrossDomesticProductCurrentPricesEntity generateGrossDomesticProductCurrentPricesEntity() {
		GrossDomesticProductCurrentPricesId id = generateGrossDomesticProductCurrentPricesId();
		GrossDomesticProductCurrentPricesEntity entity = new GrossDomesticProductCurrentPricesEntity(CREATED_DT,
				CREATED_BY, UPDATED_DT, UPDATED_BY, id, DATA);
		return entity;
	}

	private GrossDomesticProductCurrentPricesId generateGrossDomesticProductCurrentPricesId() {
		GrossDomesticProductCurrentPricesId id = new GrossDomesticProductCurrentPricesId(QUARTER, AREA);
		return id;
	}
}
