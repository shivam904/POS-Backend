package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.OrderItemDto;
import com.Shivam.POS.modal.OrderItem;

public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem orderItem){
        if(orderItem==null)return null;
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .product(ProductMapper.toDto(orderItem.getProduct()))
        .build();
    }
}
