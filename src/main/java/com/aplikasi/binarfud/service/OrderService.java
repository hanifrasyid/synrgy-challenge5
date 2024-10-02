package com.aplikasi.binarfud.service;

import com.aplikasi.binarfud.entity.Order;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map createOrder(Order order);
    List<Order> getOrder();
    Map getByID(Long order);
    Map getByUserId(Long UserId);
}
