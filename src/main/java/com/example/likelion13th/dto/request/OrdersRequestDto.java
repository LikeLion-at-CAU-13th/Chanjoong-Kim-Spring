// src/main/java/com/example/likelion13th/dto/request/OrdersRequestDto.java
package com.example.likelion13th.dto.request;

import com.example.likelion13th.domain.Member;
import com.example.likelion13th.domain.Orders;
import com.example.likelion13th.domain.ShippingAddress;
import com.example.likelion13th.enums.DeliverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrdersRequestDto {
    private Long memberId;
    private DeliverStatus deliverStatus;

    // 배송정보
    private String recipient;
    private String phoneNumber;
    private String streetAddress;
    private String detailAddress;
    private String postalCode;

    public Orders toEntity(Member buyer) {
        ShippingAddress shippingAddress = new ShippingAddress(
                recipient,
                phoneNumber,
                streetAddress,
                detailAddress,
                postalCode,
                null // Orders는 아직 생성 전이므로 null
        );
        Set<ShippingAddress> shippingAddresses = new HashSet<>();
        shippingAddresses.add(shippingAddress);

        return Orders.builder()
                .buyer(buyer)
                .deliverStatus(deliverStatus)
                .shippingAddresses(shippingAddresses)
                .build();
    }
}