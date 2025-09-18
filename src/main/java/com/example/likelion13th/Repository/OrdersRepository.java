package com.example.likelion13th.Repository;

import com.example.likelion13th.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
}
