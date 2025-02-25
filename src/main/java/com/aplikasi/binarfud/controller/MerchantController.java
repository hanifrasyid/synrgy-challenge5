package com.aplikasi.binarfud.controller;

import com.aplikasi.binarfud.entity.Merchant;
import com.aplikasi.binarfud.repo.MerchantRepo;
import com.aplikasi.binarfud.service.MerchantService;
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
@RequestMapping("/merchant")
public class MerchantController {

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public MerchantRepo merchantRepo;

    @Autowired
    public MerchantService merchantService;

    @Autowired
    public TemplateResponse response;

    @PostMapping(value ={"/add","/add/"})
    public ResponseEntity<Map> addMerchant(@RequestBody Merchant merchant) {
        try {
            return new ResponseEntity<Map>(merchantService.addMerchant(merchant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value={"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Merchant request) {
        try {
            return new ResponseEntity<Map>(merchantService.update(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value ={"/updateStatus", "/updateStatus/"})
    public ResponseEntity<Map> updateMerchantStatus(@RequestBody Merchant request) {
        try {
            return new ResponseEntity<Map>(merchantService.updateMerchantStatus(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value={"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody Merchant request) {
        try {
            return new ResponseEntity<Map>(merchantService.delete(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    @GetMapping(value={"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Map>(merchantService.getByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/openMerchant")
    public List<Merchant> getOpenMerchants() {
        return merchantService.getOpenMerchant();
    }

    @GetMapping(value = {"/listMerchant", "/listMerchant/"})
    public ResponseEntity<Map> list(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String merchant_name,
            @RequestParam(required = false) String merchant_location,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        try {
            Pageable show_data = simpleStringUtil.getShort(orderby, ordertype, page, size);

            Specification<Merchant> spec =
                    ((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (merchant_name != null && !merchant_name.isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("merchant_name")), "%" + merchant_name.toLowerCase() + "%"));
                        }
                        if (merchant_location != null && !merchant_location.isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("merchant_location")), "%" + merchant_location.toLowerCase() + "%"));
                        }
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                    });

            Page<Merchant> list = merchantRepo.findAll(spec, show_data);

            Map map = new HashMap();
            map.put("data",list);
            return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
