package com.aplikasi.binarfud.controller;

import com.aplikasi.binarfud.entity.Merchant;
import com.aplikasi.binarfud.entity.Order;
import com.aplikasi.binarfud.entity.User;
import com.aplikasi.binarfud.repo.OrderRepo;
import com.aplikasi.binarfud.service.InvoiceService;
import com.aplikasi.binarfud.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public OrderRepo orderRepo;

    @Autowired
    public OrderService orderService;

    @Autowired
    public InvoiceService invoiceService;

    @Autowired
    public TemplateResponse response;

    @PostMapping(value ={"/createOrder","/createOrder/"})
    public ResponseEntity<Map> createOrder(@RequestBody Order order) {
        try {
            return new ResponseEntity<Map>(orderService.createOrder(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ={"/getOrder","/getOrder/"})
    public List<Order> getOrder() {
        return orderService.getOrder();
    }

    @GetMapping(value={"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Map>(orderService.getByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value={"/{UserId}", "/{UserId}/"})
    public ResponseEntity<Map> getByUserId(@PathVariable("UserId") Long UserId) {
        try {
            return new ResponseEntity<Map>(orderService.getByUserId(UserId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/listOrder", "/listOrder/"})
    public ResponseEntity<Map> list(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String destination_address,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        try {
            Pageable show_data = simpleStringUtil.getShort(orderby, ordertype, page, size);

            Specification<Order> spec =
                    ((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (destination_address != null && !destination_address.isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("destination_address")), "%" + destination_address.toLowerCase() + "%"));
                        }
                        if (completed != null) {
                            predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
                        }
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                    });

            Page<Order> list = orderRepo.findAll(spec, show_data);

            Map map = new HashMap();
            map.put("data", list);
            return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value ={"/generateInvoice","/generateInvoice/"})
    public ResponseEntity<Map> generateInvoice(@RequestBody User user) {
        try {
            return new ResponseEntity<Map>(invoiceService.generateInvoice(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value ={"/generateReport","/generateReport/"})
    public ResponseEntity<Map> generateReport(@RequestBody Merchant merchant) {
        try {
            return new ResponseEntity<Map>(invoiceService.generateReport(merchant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}