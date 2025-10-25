package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.CategoryDTO;
import com.Shivam.POS.modal.Category;

public class CategoryMapper {
    public static CategoryDTO toDto(Category category){
        return CategoryDTO.builder().id(category.getId()).name(category.getName()).storeId(category.getStore()!=null? category.getStore().getId() :null ).build();
    }
}
