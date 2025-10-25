package com.Shivam.POS.repository;

import com.Shivam.POS.modal.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Inventory findByProductIdAndBranchId(Long productId, Long branchId);
    List<Inventory> findByBranchId(Long branchId);
}
