package com.example.likelion13th.controller;

import com.example.likelion13th.domain.Member;
import com.example.likelion13th.domain.Orders;
import com.example.likelion13th.domain.ShippingAddress;
import com.example.likelion13th.dto.request.OrdersDeleteRequestDto;
import com.example.likelion13th.dto.request.OrdersUpdateRequestDto;
import com.example.likelion13th.dto.response.OrdersResponseDto;
import com.example.likelion13th.dto.request.OrdersRequestDto;
import com.example.likelion13th.Service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders") // 공통 경로
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrdersResponseDto> createOrders(@RequestBody OrdersRequestDto dto) {
        return ResponseEntity.ok(ordersService.createOrders(dto));
    }

    // 전체 주문 조회
    @GetMapping
    public ResponseEntity<List<OrdersResponseDto>> getAllOrders()
    {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersResponseDto> getOrdersById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(ordersService.getOrdersById(id));
    }

    // 주문 상태 수정
    @PutMapping("/{id}")
    public ResponseEntity<OrdersResponseDto> updateOrders(@PathVariable("id") Long id, @RequestBody OrdersUpdateRequestDto dto)
    {
        return ResponseEntity.ok(ordersService.updateOrders(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable("id") Long id, @RequestBody OrdersDeleteRequestDto dto)
    {
        ordersService.deleteOrders(id, dto);
        return ResponseEntity.ok("주문 내역이 성공적으로 삭제되었습니다.");
    }
}
