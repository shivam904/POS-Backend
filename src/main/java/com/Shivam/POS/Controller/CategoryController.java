package com.Shivam.POS.Controller;

import com.Shivam.POS.Payload.dto.CategoryDTO;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO>createCategory(
            @RequestBody CategoryDTO categoryDTO ) throws Exception{
        return ResponseEntity.ok(
                categoryService.createCategory(categoryDTO)
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>>getCategoryByStoreId(
             @PathVariable Long storeId ) throws Exception{
        return ResponseEntity.ok(
                categoryService.getCategoriesByStore(storeId)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO>updateCategory(
            @PathVariable Long id, @RequestBody CategoryDTO categoryDTO ) throws Exception{
        return ResponseEntity.ok(
                categoryService.updateCategory(id,categoryDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>deleteCategory(
            @PathVariable Long id, @RequestBody CategoryDTO categoryDTO ) throws Exception{

        categoryService.deleteCategory(id);
        ApiResponse apiResponse= new ApiResponse();
        apiResponse.setMessage("Successfully deleted category");
        return ResponseEntity.ok(
                apiResponse
        );
    }

}
