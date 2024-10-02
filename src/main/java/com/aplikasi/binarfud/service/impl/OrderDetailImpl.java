package com.aplikasi.binarfud.service.impl;

import com.aplikasi.binarfud.entity.OrderDetail;
import com.aplikasi.binarfud.repo.OrderDetailRepo;
import com.aplikasi.binarfud.service.OrderDetailService;
import com.aplikasi.binarfud.util.Config;
import com.aplikasi.binarfud.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map addOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("add Ooder setail");
            return response.success(orderDetailRepo.save(orderDetail));
        } catch (Exception e) {
            log.error("add order detail error: " + e.getMessage());
            return response.Error("add order detail = " + e.getMessage());
        }
    }

    @Override
    public Map updateOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("update order detail");
            if (orderDetail.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkDataDBorderDetail = orderDetailRepo.findById(orderDetail.getId());
            if (!checkDataDBorderDetail.isPresent()) {
                return response.Error(Config.ORDER_DETAIL_NOT_FOUND);
            }

            checkDataDBorderDetail.get().setQuantity(orderDetail.getQuantity());
            checkDataDBorderDetail.get().setTotal_price(orderDetail.getTotal_price());
            checkDataDBorderDetail.get().setOrder(orderDetail.getOrder());
            checkDataDBorderDetail.get().setProduct(orderDetail.getProduct());
            checkDataDBorderDetail.get().setUpdated_date(new Date());

            return response.success(orderDetailRepo.save(checkDataDBorderDetail.get()));
        } catch (Exception e){
            log.error("update order detail error : " + e.getMessage());
            return response.Error("update order detail =" + e.getMessage());
        }
    }

    @Override
    public Map deleteOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("delete order detail");
            if (orderDetail.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkDataDBorderDetail = orderDetailRepo.findById(orderDetail.getId());
            if (!checkDataDBorderDetail.isPresent()) {
                return response.Error(Config.ORDER_DETAIL_NOT_FOUND);
            }
            checkDataDBorderDetail.get().setDeleted_date(new Date());
            orderDetailRepo.save(checkDataDBorderDetail.get());
            return response.success(Config.SUCCESS);
        }catch (Exception e){
            log.error("delete order detail error : " + e.getMessage());
            return response.Error("delete order detail = " +e.getMessage());
        }
    }

    @Override
    public Map getByID(Long orderDetail) {
        Optional<OrderDetail> getBaseOptional = orderDetailRepo.findById(orderDetail);
        if(getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }
}
