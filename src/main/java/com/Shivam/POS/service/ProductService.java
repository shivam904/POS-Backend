package com.Shivam.POS.service;

import com.Shivam.POS.Payload.dto.ProductDto;
import com.Shivam.POS.modal.User;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, User user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;

    List<ProductDto> getProductByStoreId(Long storeId);

    List<ProductDto> searchByKeyword(Long storeId, String keyword);

}
