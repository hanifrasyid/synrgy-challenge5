package com.aplikasi.binarfud.repo;

import com.aplikasi.binarfud.entity.Order;
import com.aplikasi.binarfud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    // Mencari pesanan berdasarkan objek pengguna (user)
    Optional<Order> findByUser(User user);
}
