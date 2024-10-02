package com.aplikasi.binarfud.repo;

import com.aplikasi.binarfud.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
}
