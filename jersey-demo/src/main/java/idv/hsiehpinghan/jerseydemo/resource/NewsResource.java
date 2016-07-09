package idv.hsiehpinghan.jerseydemo.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;

import idv.hsiehpinghan.jerseydemo.service.NewsService;
import idv.hsiehpinghan.jerseydemo.vo.NewsVo;

@Path("news")
public class NewsResource {
	@Autowired
	private NewsService service;

	/**
	 * http://localhost:8080/jersey-demo/news?term=brexit&start=0
	 * 
	 * @param term
	 * @return
	 * @throws SolrServerException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFeeds(@QueryParam("term") String term, @QueryParam("start") Integer start)
			throws SolrServerException {
		List<NewsVo> vos = service.query(term, start);
		if (vos.size() <= 0) {
			return Response.status(Response.Status.NOT_FOUND).entity("No data !!!").build();
		} else {
			return Response.status(Response.Status.OK).entity(vos).build();
		}
	}

}
