package com.example.likelion13th.dto.response;

import com.example.likelion13th.domain.Orders;
import com.example.likelion13th.enums.DeliverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrdersResponseDto {
    private Long id;
    private DeliverStatus deliverStatus;

    public static OrdersResponseDto fromEntity(Orders orders)
    {
        return OrdersResponseDto.builder()
                .id(orders.getId())
                .deliverStatus(orders.getDeliverStatus())
                .build();
    }
}
