package com.example.saleappv1.service;

import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public List<Product> getProducts() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/products.json");
            if (inputStream == null) {
                return new ArrayList<>();
            }
            return mapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Product> getProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> products = getProducts();
        
        return products.stream().filter(p -> {
            boolean matchCategory = (categoryId == null || p.getCategoryId() == categoryId);
            boolean matchKeyword = (keyword == null || keyword.isEmpty() || p.getName().toLowerCase().contains(keyword.toLowerCase()));
            boolean matchFromPrice = (fromPrice == null || p.getPrice() >= fromPrice);
            boolean matchToPrice = (toPrice == null || p.getPrice() <= toPrice);
            
            return matchCategory && matchKeyword && matchFromPrice && matchToPrice;
        }).collect(Collectors.toList());
    }
}