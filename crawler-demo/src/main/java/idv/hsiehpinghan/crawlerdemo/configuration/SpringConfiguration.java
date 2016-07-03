package idv.hsiehpinghan.crawlerdemo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@Configuration("crawlerDemoSpringConfiguration")
@ComponentScan(basePackages = { "idv.hsiehpinghan.crawlerdemo" })
public class SpringConfiguration {
	// private Logger logger = Logger.getLogger(this.getClass().getName());
}
