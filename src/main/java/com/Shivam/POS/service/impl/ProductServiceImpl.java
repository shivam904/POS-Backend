package com.Shivam.POS.service.impl;

import com.Shivam.POS.Mapper.ProductMapper;
import com.Shivam.POS.Payload.dto.CategoryDTO;
import com.Shivam.POS.Payload.dto.ProductDto;
import com.Shivam.POS.modal.Category;
import com.Shivam.POS.modal.Product;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.CategoryRepository;
import com.Shivam.POS.repository.ProductRepository;
import com.Shivam.POS.repository.StoreRepository;
import com.Shivam.POS.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto, User user) throws Exception {
        Store store= storeRepository.findById(
                productDto.getStoreId()
        ).orElseThrow(
                ()->new Exception("Store Not Found")
        );

        Category category= categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new Exception("Category not existed"));
        Product product=ProductMapper.toEntity(productDto,store,category);
        Product savedProduct= productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception {
        Product product= productRepository.findById(id).orElseThrow(
                ()->new Exception("Product not Found")
        );


        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImage(productDto.getImage());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if(productDto.getCategoryId() !=null){
            Category category= categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new Exception("Category not Found"));

            if(category != null){
                product.setCategory(category);
            }
        }

        Product savedProduct= productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product=productRepository.findById(id).orElseThrow(
                ()->new Exception("Product Not found")
        );
        productRepository.delete(product);

    }

    @Override
    public List<ProductDto> getProductByStoreId(Long storeId) {
        return productRepository.findByStoreId(storeId).stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) {

        return productRepository.searchByKeyword(storeId,keyword).stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
