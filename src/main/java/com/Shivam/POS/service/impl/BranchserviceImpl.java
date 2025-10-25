package com.Shivam.POS.service.impl;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.BranchMapper;
import com.Shivam.POS.Payload.dto.BranchDto;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.BranchRepository;
import com.Shivam.POS.repository.StoreRepository;
import com.Shivam.POS.repository.UserRepository;
import com.Shivam.POS.service.BranchService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchserviceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {
        User currentUser=userService.getCurrentUser();
        Store store=storeRepository.findByStoreAdminId(currentUser.getId());
        Branch branch= BranchMapper.toEntity(branchDto,store);

        return BranchMapper.toDto(branchRepository.save(branch));
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch existing= branchRepository.findById(id).orElseThrow(()->new Exception("branch not exist..."));
        existing.setName(branchDto.getName());
        existing.setEmail(branchDto.getEmail());
        existing.setWorkingDays(branchDto.getWorkingDays());
        existing.setPhone(branchDto.getPhone());
        existing.setAddress(branchDto.getAddress());
        existing.setOpenTime(branchDto.getOpenTime());
        existing.setCloseTime(branchDto.getCloseTime());
        existing.setUpdatedAt(LocalDateTime.now());
        Branch updated= branchRepository.save(existing);

        return BranchMapper.toDto(updated);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existing= branchRepository.findById(id).orElseThrow(()->new Exception("branch not exist..."));
        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDto> getAllBranchesById(Long storeId) {
        return branchRepository.findByStoreId(storeId).stream().map(BranchMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch existing= branchRepository.findById(id).orElseThrow(()->new Exception("branch not exist..."));

        return BranchMapper.toDto(existing);
    }
}
