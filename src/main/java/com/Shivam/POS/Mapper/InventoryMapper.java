package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.InventoryDto;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Inventory;
import com.Shivam.POS.modal.Product;

public class InventoryMapper {
    public static InventoryDto toDto(Inventory inventory){
            return InventoryDto.builder()
                    .id(inventory.getId())
                    .branchId(inventory.getBranch().getId())
                    .productId(inventory.getProduct().getId())
                    .product(ProductMapper.toDto(inventory.getProduct()))
                    .quantity(inventory.getQuantity())
                    .build();

    }
    public static Inventory toEntity(InventoryDto inventoryDto, Branch branch, Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDto.getQuantity())
                .build();
    }
}
