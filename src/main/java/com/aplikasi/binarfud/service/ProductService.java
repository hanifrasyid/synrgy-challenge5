package com.aplikasi.binarfud.service;

import com.aplikasi.binarfud.entity.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Map addProduct(Product product);
    Map updateProduct(Product product);
    Map deleteProduct(Product product);
    List<Product> getAvailableProduct();
    Map getByID(Long product);
}
