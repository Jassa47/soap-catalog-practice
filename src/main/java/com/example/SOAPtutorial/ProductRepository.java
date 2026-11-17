package com.example.SOAPtutorial;

import com.example.SOAPtutorial.gen.Category;
import com.example.SOAPtutorial.gen.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {

    // Our "database": SKU -> Product
    private final Map<String, Product> products = new LinkedHashMap<>();

    // Used to create new SKUs: SKU-1007, SKU-1008, ...
    private final AtomicInteger nextId = new AtomicInteger(1007);

    // Runs once when the app starts: fill in sample data
    public ProductRepository() {
        save(make("SKU-1001", "Okanagan Pinot Noir", Category.WINE, "24.99", 40));
        save(make("SKU-1002", "Niagara Riesling", Category.WINE, "18.49", 25));
        save(make("SKU-1003", "Pacific Pale Ale 6-pack", Category.BEER, "14.99", 120));
        save(make("SKU-1004", "Coastal Lager 12-pack", Category.BEER, "26.99", 60));
        save(make("SKU-1005", "Small Batch Gin", Category.SPIRITS, "39.99", 15));
        save(make("SKU-1006", "Sparkling Apple Cider (0.0%)", Category.NON_ALCOHOLIC, "6.99", 0));
    }

    // Find one product. Returns an empty Optional if the SKU doesn't exist.
    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(products.get(sku));
    }

    // List all products, or only one category if category is not null
    public List<Product> findAll(Category category) {
        return products.values().stream()
                .filter(p -> category == null || p.getCategory() == category)
                .toList();
    }

    // Create a new product with a generated SKU
    public Product add(String name, Category category, BigDecimal price, int stock) {
        String sku = "SKU-" + nextId.getAndIncrement();
        Product p = make(sku, name, category, price.toPlainString(), stock);
        save(p);
        return p;
    }

    private void save(Product p) {
        products.put(p.getSku(), p);
    }

    // Helper to build a Product (the class JAXB generated in Step 3)
    private static Product make(String sku, String name, Category category, String price, int stock) {
        Product p = new Product();
        p.setSku(sku);
        p.setName(name);
        p.setCategory(category);
        p.setPrice(new BigDecimal(price));
        p.setStock(stock);
        return p;
    }
}