package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {

    private Long id;
    private BranchDto branch;
    private ProductDto product;
    private Long branchId;
    private Long productId;
    private int quantity;
    private LocalDateTime lastUpdate;

}
