package com.aplikasi.binarfud.service.impl;

import com.aplikasi.binarfud.entity.Order;
import com.aplikasi.binarfud.entity.User;
import com.aplikasi.binarfud.repo.OrderRepo;
import com.aplikasi.binarfud.repo.UserRepo;
import com.aplikasi.binarfud.service.OrderService;
import com.aplikasi.binarfud.util.Config;
import com.aplikasi.binarfud.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OrderImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map createOrder(Order order) {
        try {
            log.info("create order");
            return response.success(orderRepo.save(order));
        } catch (Exception e){
            log.error("create order error : " + e.getMessage());
            return response.Error("create order = " + e.getMessage());
        }
    }

    @Override
    public List<Order> getOrder() {
        log.info("get all order");
        return orderRepo.findAll();
    }

    @Override
    public Map getByID(Long order) {
        Optional<Order> getBaseOptional = orderRepo.findById(order);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }

    @Override
    public Map getByUserId(Long UserId) {
        try {
            log.info("get order by user");
            if (UserId == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<User> checkDataDBUser = userRepo.findById(UserId);
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            checkDataDBUser.get().setId(UserId);
            Optional<Order> getBaseOptional = orderRepo.findByUser(checkDataDBUser.get());
            if(!getBaseOptional.isPresent()){
                return response.notFound(getBaseOptional);
            }
            return response.templateSuccess(getBaseOptional);
        }catch (Exception e){
            log.error("get order by user error : " + e.getMessage());
            return response.Error("get order by user = " + e.getMessage());
        }
    }
}
