package com.example.likelion13th.Service;

import com.example.likelion13th.dto.request.OrdersDeleteRequestDto;
import com.example.likelion13th.enums.DeliverStatus;
import com.example.likelion13th.domain.Member;
import com.example.likelion13th.domain.Orders;
import com.example.likelion13th.domain.ShippingAddress;
import com.example.likelion13th.dto.request.OrdersRequestDto;
import com.example.likelion13th.dto.response.OrdersResponseDto;
import com.example.likelion13th.dto.request.OrdersUpdateRequestDto;
import com.example.likelion13th.Repository.OrdersRepository;
import com.example.likelion13th.Repository.MemberRepository;
import com.example.likelion13th.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    
    @Transactional
    public OrdersResponseDto createOrders(OrdersRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매자입니다."));

        if (!member.isBuyer()) {
            throw new IllegalArgumentException("주문은 구매자만 등록할 수 있습니다.");
        }

        Orders orders = dto.toEntity(member);
        Orders savedOrders = ordersRepository.save(orders);
        return new OrdersResponseDto(savedOrders.getId(), savedOrders.getDeliverStatus());
    }
    
    // 주문 목록 전부 조회
    public List<OrdersResponseDto> getAllOrders()
    {
        return ordersRepository.findAll().stream()
                .map(OrdersResponseDto::fromEntity)
                .toList();
    }

    // 특정 주문 목록 조회
    public OrdersResponseDto getOrdersById(Long id) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
        return OrdersResponseDto.fromEntity(orders);
    }

    // 주문 수정
    @Transactional
    public OrdersResponseDto updateOrders(Long ordersId, OrdersUpdateRequestDto dto)
    {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매자입니다."));

        Orders orders = ordersRepository.findById(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if(!orders.getBuyer().getId().equals(member.getId()))
        {
            throw new IllegalArgumentException("구매자 본인이 주문을 수정할 수 있습니다.");
        }

        if(!orders.getDeliverStatus().equals(DeliverStatus.PREPARATION)) {
            throw new IllegalStateException("주문은 PREPARATION 상태일 때만 수정할 수 있습니다.");
        }

        orders.update(dto.getDeliverStatus());

        return OrdersResponseDto.fromEntity(orders);
    }

    @Transactional
    public void deleteOrders(Long ordersId, OrdersDeleteRequestDto dto)
    {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매자입니다."));

        Orders orders = ordersRepository.findById(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if(!orders.getBuyer().getId().equals(member.getId()))
        {
            throw new IllegalArgumentException("구매자 본인이 주문을 삭제할 수 있습니다.");
        }

        if(!orders.getDeliverStatus().equals(DeliverStatus.COMPLETED)) {
            throw new IllegalStateException("주문은 COMPLETED 상태일 때만 삭제할 수 있습니다.");
        }

        ordersRepository.delete(orders);
    }
}
