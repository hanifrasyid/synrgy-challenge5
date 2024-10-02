package com.aplikasi.binarfud.controller;

import com.aplikasi.binarfud.entity.Product;
import com.aplikasi.binarfud.repo.ProductRepo;
import com.aplikasi.binarfud.service.ProductService;
import com.aplikasi.binarfud.util.SimpleStringUtil;
import com.aplikasi.binarfud.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public ProductService productService;

    @Autowired
    public TemplateResponse response;

    @PostMapping(value = {"/add","/add/"})
    public ResponseEntity<Map> addProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<Map>(productService.addProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/update","/update/"})
    public ResponseEntity<Map> updateProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<Map>(productService.updateProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/delete","/delete/"})
    public ResponseEntity<Map> deleteProduct(@RequestBody Product request) {
        try {
            return new ResponseEntity<Map>(productService.deleteProduct(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/availableProduct","/availableProduct/"})
    public List<Product> getAvailableProduct() {
        return productService.getAvailableProduct();
    }

    @GetMapping(value={"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Map>(productService.getByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/listProduct", "/listProduct/"})
    public ResponseEntity<Map> list(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String product_name,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        try {
            Pageable show_data = simpleStringUtil.getShort(orderby, ordertype, page, size);

            Specification<Product> spec =
                    ((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (product_name != null && !product_name.isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("product_name")), "%" + product_name.toLowerCase() + "%"));
                        }
                        if (price != null && !price.isEmpty()) {
                            predicates.add(criteriaBuilder.equal(root.get("price"), price));
                        }
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                    });
            Page<Product> list = productRepo.findAll(spec, show_data);

            Map map = new HashMap();
            map.put("data",list);
            return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
