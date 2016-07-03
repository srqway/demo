package idv.hsiehpinghan.crawlerdemo.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		Class<?>[] clsArr = new Class<?>[] { idv.hsiehpinghan.crawlerdemo.configuration.SpringConfiguration.class,
				idv.hsiehpinghan.jpademo.configuration.SpringConfiguration.class };
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(clsArr);
	}
}
