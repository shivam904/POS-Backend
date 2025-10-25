package com.Shivam.POS.service;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.BranchDto;
import com.Shivam.POS.modal.User;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDto>getAllBranchesById(Long storeId);
    BranchDto getBranchById(Long id) throws Exception;
}
