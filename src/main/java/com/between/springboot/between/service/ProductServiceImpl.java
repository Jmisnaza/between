package com.between.springboot.between.service;

import com.between.springboot.between.entity.Product;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
public class ProductServiceImpl implements ProductService {

    private final WebClient webClient;

    public ProductServiceImpl(@Value("${content-service}") String baseURL){

        this.webClient = WebClient.builder().baseUrl(baseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    @Override
    public Product getProduct(long id){
        try {
            return webClient.get()
                    .uri("product/" + id)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (WebClientResponseException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Object[] silimiarIdsList(long id) {
        try {
        return webClient.get()
                .uri("product/" + id + "/similarids")
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
        } catch (WebClientResponseException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
