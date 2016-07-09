package idv.hsiehpinghan.jerseydemo.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import idv.hsiehpinghan.jerseydemo.configuration.SpringConfiguration;
import idv.hsiehpinghan.jerseydemo.vo.NewsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { SpringConfiguration.class })
public class NewsServiceTest {
	private final String TERM = "benefit";
	@Autowired
	private NewsService service;

	@Test
	public void a_query() throws SolrServerException {
		List<NewsVo> vos = service.query(TERM);
		
		System.err.println(vos);
		
		Assert.assertTrue(vos.size() > 0);
	}
}
