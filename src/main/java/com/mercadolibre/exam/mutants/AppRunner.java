package com.mercadolibre.exam.mutants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication 	
public class AppRunner  extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppRunner.class);
    }

    public static void main(String[] args) throws Exception {
    	SpringApplication.run(AppRunner.class, args);
    }
}
