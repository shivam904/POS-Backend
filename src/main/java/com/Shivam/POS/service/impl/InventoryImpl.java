package com.Shivam.POS.service.impl;

import com.Shivam.POS.Mapper.InventoryMapper;
import com.Shivam.POS.Payload.dto.InventoryDto;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Inventory;
import com.Shivam.POS.modal.Product;
import com.Shivam.POS.repository.BranchRepository;
import com.Shivam.POS.repository.InventoryRepository;
import com.Shivam.POS.repository.ProductRepository;
import com.Shivam.POS.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {
        Branch branch= branchRepository.findById(inventoryDto.getBranchId()).orElseThrow(()->new Exception("branch not exist..."));
        Product product=productRepository.findById(inventoryDto.getProductId()).orElseThrow(()->new Exception("product doesn't exist"));
        Inventory inventory= InventoryMapper.toEntity(inventoryDto,branch,product);
        Inventory saved= inventoryRepository.save(inventory);

        return InventoryMapper.toDto(saved);
    }

    @Override
    public InventoryDto updateInventory(Long id,InventoryDto inventoryDto) throws Exception {
        Inventory inventory= inventoryRepository.findById(id).orElseThrow(()->new Exception("inventory doesn't exist"));
        inventory.setQuantity(inventoryDto.getQuantity());
        Inventory updatedInventory= inventoryRepository.save(inventory);

        return InventoryMapper.toDto(updatedInventory);

    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory= inventoryRepository.findById(id).orElseThrow(()->new Exception("inventory doesn't exist"));
        inventoryRepository.delete(inventory);


    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory= inventoryRepository.findById(id).orElseThrow(()->new Exception("inventory doesn't exist"));

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory= inventoryRepository.findByProductIdAndBranchId(productId,branchId);
        return InventoryMapper.toDto(inventory);

    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories= inventoryRepository.findByBranchId(branchId);
        return inventories.stream().map(InventoryMapper::toDto).collect(Collectors.toList());
    }
}
