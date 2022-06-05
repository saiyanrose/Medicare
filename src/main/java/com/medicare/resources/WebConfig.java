package com.medicare.resources;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/**")
		.addResourceLocations("classpath:/static/").resourceChain(false);
		
		String dirName = "product-photos";
		Path productPhotosDir = Paths.get(dirName);
		System.out.println(productPhotosDir);

		String productPhotosPath = productPhotosDir.toFile().getAbsolutePath();
		System.out.println(productPhotosPath);

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + productPhotosPath + "/");
	}

	
}
