package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.modal.Store;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private Long storeId;
}
