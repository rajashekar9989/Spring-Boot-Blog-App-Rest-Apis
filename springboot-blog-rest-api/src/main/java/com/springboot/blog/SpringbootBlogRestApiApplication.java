package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(

		info = @Info(

				title="Spring Boot Blog App Rest Apis",
				description = "Spring Boot Blog App Rest Apis Documentation",
				version = "v1.0",
				contact = @Contact(
						name="Rajashekar Reddy",
						email="rajas9162@gmail.com"

				),
				license = @License(
						name="Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog Api Documentation",
				url="git hub link"
		)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}











}


