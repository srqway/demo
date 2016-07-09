package idv.hsiehpinghan.jerseydemo.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import idv.hsiehpinghan.jerseydemo.service.EconomyService;
import idv.hsiehpinghan.jerseydemo.vo.ExchangeRateVo;
import idv.hsiehpinghan.jerseydemo.vo.GdpVo;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity.EuroNationalCurrencyExchangeRatesId;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;

@Path("economy")
public class EconomyResource {
	@Autowired
	private EconomyService service;

	/**
	 * http://localhost:8080/jersey-demo/economy/gdp
	 * 
	 * @return
	 */
	@GET
	@Path("gdp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGdp() {
		List<GdpVo> entities = service.getAllGdps();
		if (entities.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(entities).build();
		}
	}

	/**
	 * http://localhost:8080/jersey-demo/economy/gdp/Belgium
	 * 
	 * @param area
	 * @param quarter
	 * @return
	 */
	@GET
	@Path("gdp/{area : [a-zA-Z]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGdpByArea(@PathParam("area") String area) {
		List<GdpVo> entities = service.getGdpsByArea(area);
		if (entities.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(entities).build();
		}
	}

	/**
	 * http://localhost:8080/jersey-demo/economy/gdp/Belgium/2013Q2
	 * 
	 * @param area
	 * @param quarter
	 * @return
	 */
	@GET
	@Path("gdp/{area : [a-zA-Z]+}/{quarter : \\d{4}Q\\d{1}}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGdpByAreaAndQuarter(@PathParam("area") String area, @PathParam("quarter") String quarter) {
		GrossDomesticProductCurrentPricesId id = new GrossDomesticProductCurrentPricesId(quarter, area);
		GdpVo vo = service.getGdpById(id);
		if (vo == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Not found !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(vo).build();
		}
	}

	/**
	 * http://localhost:8080/jersey-demo/economy/exchange_rate
	 * 
	 * @return
	 */
	@GET
	@Path("exchange_rate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllExchangeRate() {
		List<ExchangeRateVo> entities = service.getAllExchangeRates();
		if (entities.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(entities).build();
		}
	}

	/**
	 * http://localhost:8080/jersey-demo/economy/exchange_rate/Bulgarian%20lev
	 * 
	 * @param currency
	 * @param yearMonth
	 * @return
	 */
	@GET
	@Path("exchange_rate/{currency: [a-zA-Z0-9%]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExchangeRateByCurrency(@PathParam("currency") String currency) {
		List<ExchangeRateVo> entities = service.getExchangeRatesByCurrency(currency);
		if (entities.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(entities).build();
		}
	}

	/**
	 * http://localhost:8080/jersey-demo/economy/exchange_rate/Bulgarian%20lev/
	 * 2016M05
	 * 
	 * @param currency
	 * @param yearMonth
	 * @return
	 */
	@GET
	@Path("exchange_rate/{currency: [a-zA-Z0-9%]+}/{yearMonth : \\d{4}M\\d{2}}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExchangeRateByCurrencyAndYearMonth(@PathParam("currency") String currency,
			@PathParam("yearMonth") String yearMonth) {
		EuroNationalCurrencyExchangeRatesId id = new EuroNationalCurrencyExchangeRatesId(yearMonth, currency);
		ExchangeRateVo vo = service.getExchangeRateById(id);
		if (vo == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Not found !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(vo).build();
		}
	}
}
