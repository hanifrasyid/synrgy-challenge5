package com.aplikasi.binarfud.service.impl;

import com.aplikasi.binarfud.entity.Merchant;
import com.aplikasi.binarfud.entity.Product;
import com.aplikasi.binarfud.repo.MerchantRepo;
import com.aplikasi.binarfud.repo.ProductRepo;
import com.aplikasi.binarfud.service.ProductService;
import com.aplikasi.binarfud.util.Config;
import com.aplikasi.binarfud.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MerchantRepo merchantRepo;

    @Autowired
    public TemplateResponse response;
    @Override
    public Map addProduct(Product product) {
        try {
            log.info("add product");
            if(product.getProduct_name().isEmpty()){
                return response.Error(Config.NAME_REQUIRED);
            }
            if(product.getMerchant() == null && product.getMerchant().getId() == null){
                return response.Error(Config.MERCHANT_REQUIRED);
            }
            Optional<Merchant> checkDataDB = merchantRepo.findById(product.getMerchant().getId());
            if(!checkDataDB.isPresent()){
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }
            product.setMerchant(checkDataDB.get());
            return response.success(productRepo.save(product));
        } catch (Exception e){
            log.error("add product error : " + e.getMessage());
            return response.Error("add product = " + e.getMessage());
        }
    }

    @Override
    public Map updateProduct(Product product) {
        try {
            log.info("update product");
            if(product.getId() == null ){
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Product> checkDataDBProduct = productRepo.findById(product.getId());
            if(!checkDataDBProduct.isPresent()){
                return response.Error(Config.PRODUCT_NOT_FOUND);
            }

            if(product.getMerchant() == null && product.getMerchant().getId() == null) {
                return response.Error(Config.MERCHANT_REQUIRED);
            }
            Optional<Merchant> checkDataDB = merchantRepo.findById(product.getMerchant().getId());
            if(!checkDataDB.isPresent()){
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }

            checkDataDBProduct.get().setProduct_name(product.getProduct_name());
            checkDataDBProduct.get().setPrice(product.getPrice());
            checkDataDBProduct.get().setMerchant(checkDataDB.get());
            checkDataDBProduct.get().setUpdated_date(new Date());

            return response.success(productRepo.save(checkDataDBProduct.get()));
        } catch (Exception e){
            log.error("update product error : " + e.getMessage());
            return response.Error("update product = " + e.getMessage());
        }
    }

    @Override
    public Map deleteProduct(Product product) {
        try {
            log.info("delete product");
            if(product.getId() == null ){
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Product> checkDataDBProduct = productRepo.findById(product.getId());
            if(!checkDataDBProduct.isPresent()){
                return response.Error(Config.PRODUCT_NOT_FOUND);
            }

            checkDataDBProduct.get().setDeleted_date(new Date());

            productRepo.save(checkDataDBProduct.get());
            return response.success(Config.SUCCESS);
        } catch (Exception e){
            log.error("delete product error : " + e.getMessage());
            return response.Error("delete product = " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAvailableProduct() {
        return productRepo.findAll();
    }

    @Override
    public Map getByID(Long product) {
        Optional<Product> getBaseOptional = productRepo.findById(product);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }
}