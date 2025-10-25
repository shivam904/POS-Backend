package com.Shivam.POS.repository;


import com.Shivam.POS.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category>findByStoreId(Long storeId);

}
