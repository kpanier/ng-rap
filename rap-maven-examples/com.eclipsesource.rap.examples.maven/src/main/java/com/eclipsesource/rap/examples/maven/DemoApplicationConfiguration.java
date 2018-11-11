package com.eclipsesource.rap.examples.maven;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.rap.rwt.service.ResourceLoader;

public class DemoApplicationConfiguration implements ApplicationConfiguration {

	public void configure(Application application) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "RAP Maven Demo");
		application.addEntryPoint("/demo", DemoApplication.class, properties );

		addResource(application, "main.js");
		addResource(application, "polyfills.js");
		addResource(application, "runtime.js");
		addResource(application, "styles.js");
		addResource(application, "vendor.js");

	}

	private void addResource(Application application, final String name) {
        application.addResource(
			name,
			new ResourceLoader() {
				@Override
				public InputStream getResourceAsStream(String resourceName ) throws IOException {
					return this.getClass().getClassLoader().getResourceAsStream( name );
				}
			}
        );
    }

}
