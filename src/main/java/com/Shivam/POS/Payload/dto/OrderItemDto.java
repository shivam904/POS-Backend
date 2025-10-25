package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.modal.Order;
import com.Shivam.POS.modal.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderItemDto {

    private Long id;
    private Integer quantity;

    private Double price;

    private ProductDto product;
    private Long productId;

    private Long orderId;
}
