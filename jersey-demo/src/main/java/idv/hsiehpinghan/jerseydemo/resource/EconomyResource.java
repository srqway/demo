package idv.hsiehpinghan.jerseydemo.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import idv.hsiehpinghan.jerseydemo.service.EconomyService;
import idv.hsiehpinghan.jerseydemo.vo.EconomyVo;

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
		EconomyVo vo = service.getGdpEconomyVo();
		if (vo.getxAxises().size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
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
		EconomyVo vo = service.getExchangeRateEconomyVo();
		if (vo.getxAxises().size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(vo).build();
		}
	}

}
