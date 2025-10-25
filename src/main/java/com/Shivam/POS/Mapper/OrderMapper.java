package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.OrderDto;
import com.Shivam.POS.modal.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .branchId(order.getBranch().getId())
                .cashier(UserMapper.toDTO(order.getCashier()))
                .customer(order.getCustomer())
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream().map(OrderItemMapper::toDto).collect(Collectors.toList()))
                .build();


    }
}
