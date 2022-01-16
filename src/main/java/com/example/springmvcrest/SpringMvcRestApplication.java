package com.example.springmvcrest;

import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class SpringMvcRestApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcRestApplication.class, args);
    }

}

