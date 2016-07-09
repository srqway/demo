package idv.hsiehpinghan.jerseydemo.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import idv.hsiehpinghan.jerseydemo.service.EconomyService;
import idv.hsiehpinghan.jerseydemo.vo.GdpVo;

@Path("economy")
public class EconomyResource {
	@Autowired
	private EconomyService service;

	/**
	 * http://localhost:8080/jersey-demo/economy
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readCrud() {
		List<GdpVo> entities = service.findAll();
		if (entities.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No economy data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(entities).build();
		}
	}

}
