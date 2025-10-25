package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Customer;
import com.Shivam.POS.modal.OrderItem;
import com.Shivam.POS.modal.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class OrderDto {

    private Long id;
    private Double totalAmount;
    private LocalDateTime createdAt;

    private BranchDto branch;

    private Long branchId;
    private Long customerId;
    private UserDto cashier;
    private PaymentType paymentType;

    private Customer customer;

    private List<OrderItemDto> items;
}
