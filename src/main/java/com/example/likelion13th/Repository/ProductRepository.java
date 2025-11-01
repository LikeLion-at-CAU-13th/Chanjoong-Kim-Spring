package com.example.likelion13th.Repository;

import com.example.likelion13th.domain.Member;
import com.example.likelion13th.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findBySeller(Member seller);
}