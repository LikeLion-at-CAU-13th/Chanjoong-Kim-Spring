package com.example.likelion13th.dto.request;

import com.example.likelion13th.enums.DeliverStatus;
import lombok.Getter;

@Getter
public class OrdersUpdateRequestDto {
    private DeliverStatus deliverStatus;
    private Long memberId;
}
