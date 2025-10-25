package com.Shivam.POS.Controller;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.BranchDto;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.service.BranchService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchService branchService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) throws UserException {
        BranchDto createdBranch= branchService.createBranch(branchDto);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> findBranchById(@PathVariable Long id) throws Exception {
        BranchDto createdBranch= branchService.getBranchById(id);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDto>> getAllBranchesByStoreId(@PathVariable Long storeId) throws Exception {
        List<BranchDto> createdBranch= branchService.getAllBranchesById(storeId);
        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) throws Exception {
        BranchDto createdBranch= branchService.updateBranch(id,branchDto);
        return ResponseEntity.ok(createdBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("successfully deleted branch");
        return ResponseEntity.ok(apiResponse);
    }
}
