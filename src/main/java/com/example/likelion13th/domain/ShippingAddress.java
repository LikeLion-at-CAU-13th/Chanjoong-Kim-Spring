package com.example.likelion13th.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ShippingAddress extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;      // 수령인
    private String phoneNumber;    // 전화번호
    private String streetAddress;  // 도로명주소
    private String detailAddress;  // 상세주소
    private String postalCode;     // 우편번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    public ShippingAddress(String recipient, String phoneNumber, String streetAddress, String detailAddress, String postalCode, Orders orders) {
        this.recipient = recipient;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.postalCode = postalCode;
        this.orders = orders;
    }
}