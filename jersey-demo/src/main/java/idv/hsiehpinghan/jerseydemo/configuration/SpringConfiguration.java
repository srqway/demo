package idv.hsiehpinghan.jerseydemo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/jersey-demo.property")
@Import(idv.hsiehpinghan.jpademo.configuration.SpringConfiguration.class)
@ComponentScan(basePackages = { "idv.hsiehpinghan.jerseydemo" })
public class SpringConfiguration {

}
