package com.Shivam.POS.Controller;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.ProductDto;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.service.ProductService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<ProductDto>create(@RequestBody ProductDto productDto, @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.getUserFromJwt(jwt);
        return ResponseEntity.ok(
                productService.createProduct(
                        productDto,user
                )
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDto>>getProductByStoreId(@PathVariable Long storeId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user =userService.getUserFromJwt(jwt);
        return ResponseEntity.ok(
                productService.getProductByStoreId(
                        storeId
                )
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDto>>getProductByKeyword(@PathVariable Long storeId, @RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws UserException {
        return ResponseEntity.ok(
                productService.searchByKeyword(
                        storeId,keyword
                )
        );
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto, @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.getUserFromJwt(jwt);
        return ResponseEntity.ok(
                productService.updateProduct(
                        id,productDto,user
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUserFromJwt(jwt);
        productService.deleteProduct(id,user);
        ApiResponse apiResponse= new ApiResponse();
        apiResponse.setMessage("Deleted the product");
        return ResponseEntity.ok(
                apiResponse
        );

    }


}
