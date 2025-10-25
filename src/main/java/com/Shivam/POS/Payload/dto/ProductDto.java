package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.modal.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;

    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String image;
    private Long storeId;
    private CategoryDTO category;
    private Long categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}
