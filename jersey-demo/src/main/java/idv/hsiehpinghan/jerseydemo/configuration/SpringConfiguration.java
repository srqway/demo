package idv.hsiehpinghan.jerseydemo.configuration;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/jersey-demo.property")
@Import(idv.hsiehpinghan.jpademo.configuration.SpringConfiguration.class)
@ComponentScan(basePackages = { "idv.hsiehpinghan.jerseydemo" })
public class SpringConfiguration {
	@Autowired
	private Environment environment;

	@Bean
	public HttpSolrServer httpSolrServer() {
		String solrUrl = environment.getRequiredProperty("solrUrl");
		HttpSolrServer httpSolrServer = new HttpSolrServer(solrUrl);
		return httpSolrServer;
	}
}
