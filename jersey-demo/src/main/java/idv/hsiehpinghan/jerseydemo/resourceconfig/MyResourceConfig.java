package idv.hsiehpinghan.jerseydemo.resourceconfig;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import idv.hsiehpinghan.jerseydemo.resource.EconomyResource;

public class MyResourceConfig extends ResourceConfig {
	public MyResourceConfig() {
		register(EconomyResource.class);
		register(RequestContextFilter.class);
		register(MultiPartFeature.class);
		register(JacksonFeature.class);
	}
}
