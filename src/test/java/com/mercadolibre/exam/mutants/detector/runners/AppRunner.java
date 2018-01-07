package com.mercadolibre.exam.mutants.detector.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.mercadolibre.exam.mutants")
public class AppRunner
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AppRunner.class, args);
    }
}
