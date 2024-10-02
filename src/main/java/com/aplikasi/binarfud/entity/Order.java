package com.aplikasi.binarfud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_time")
    private Date order_time;

    @Column(name = "destination_address")
    private String destination_address;

    @ManyToOne                      // satu user bisa beli banyak order
    @JoinColumn(name = "user_id")   // user_id sebagai foreign key dari entitas user
    private User user;

    @Column(name = "completed")
    private Boolean completed;
}