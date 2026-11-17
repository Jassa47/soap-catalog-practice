package com.example.SOAPtutorial;

import com.example.SOAPtutorial.gen.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE = "http://example.com/catalog";

    private final ProductRepository repository;

    // Spring passes in the ProductRepository automatically (dependency injection)
    public ProductEndpoint(ProductRepository repository) {
        this.repository = repository;
    }

    // ===== getProduct =====
    @PayloadRoot(namespace = NAMESPACE, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        Product product = repository.findBySku(request.getSku()).orElseThrow();

        GetProductResponse response = new GetProductResponse();
        response.setProduct(product);
        return response;
    }

    // ===== listProducts =====
    @PayloadRoot(namespace = NAMESPACE, localPart = "listProductsRequest")
    @ResponsePayload
    public ListProductsResponse listProducts(@RequestPayload ListProductsRequest request) {
        List<Product> found = repository.findAll(request.getCategory());

        ListProductsResponse response = new ListProductsResponse();
        response.setCount(found.size());
        response.getProduct().addAll(found);
        return response;
    }

    // ===== addProduct =====
    @PayloadRoot(namespace = NAMESPACE, localPart = "addProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {
        Product created = repository.add(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock());

        AddProductResponse response = new AddProductResponse();
        response.setProduct(created);
        return response;
    }

    // ===== updatePrice =====
    @PayloadRoot(namespace = NAMESPACE, localPart = "updatePriceRequest")
    @ResponsePayload
    public UpdatePriceResponse updatePrice(@RequestPayload UpdatePriceRequest request) {
        Product product = repository.findBySku(request.getSku()).orElseThrow();

        UpdatePriceResponse response = new UpdatePriceResponse();
        response.setSku(product.getSku());
        response.setOldPrice(product.getPrice());   // remember the old price first
        product.setPrice(request.getNewPrice());    // then change it
        response.setNewPrice(product.getPrice());
        return response;
    }
}