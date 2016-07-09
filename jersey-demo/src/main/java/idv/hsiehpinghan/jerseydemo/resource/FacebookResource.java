package idv.hsiehpinghan.jerseydemo.resource;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import idv.hsiehpinghan.jerseydemo.service.FacebookService;

@Path("facebook")
public class FacebookResource {
	@Autowired
	private FacebookService service;

	/**
	 * http://localhost:8080/jersey-demo/facebook/6815841748/feed
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("{page_id : \\d+}/feed")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFeeds(@PathParam("page_id") String pageId) throws IOException {
		String str = service.getFeeds(pageId);
		if (str.length() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(str).build();
		}
	}

}
