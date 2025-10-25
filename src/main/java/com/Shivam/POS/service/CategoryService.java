package com.Shivam.POS.service;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto) throws Exception;
    List<CategoryDTO>getCategoriesByStore(Long storeId);
    CategoryDTO updateCategory(Long id, CategoryDTO dto) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
