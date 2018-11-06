package com.akvasoft.NIH_Enterprise_Directory;

import com.akvasoft.NIH_Enterprise_Directory.config.BrowseNIH;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NihEnterpriseDirectoryApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NihEnterpriseDirectoryApplication.class);
	}

	public static void main(String[] args) {

		try {
			BrowseNIH.initialise();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(NihEnterpriseDirectoryApplication.class, args);
	}
}
