package com.Shivam.POS.Controller;

import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.domain.UserRole;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto>createStoreEmployee(@RequestBody UserDto userDto, @PathVariable Long storeId) throws Exception {
        UserDto employee= employeeService.createStoreEmployee(userDto,storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto>createBranchEmployee(@RequestBody UserDto userDto, @PathVariable Long branchId) throws Exception {
        UserDto employee= employeeService.createBranchEmployee(userDto,branchId);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User>updateEmployee(@RequestBody UserDto userDto, @PathVariable Long id) throws Exception {
        User employee= employeeService.updateEmployee(id,userDto);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>deleteEmployee( @PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<UserDto>>findStoreEmployee(@RequestParam(required = false) UserRole userRole, @PathVariable Long id) throws Exception {
        List<UserDto>employee= employeeService.findStoreEmployees(id,userRole);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<UserDto>>findBranchEmployee(@RequestParam(required = false) UserRole userRole, @PathVariable Long id) throws Exception {
        List<UserDto>employee= employeeService.findBranchEmployees(id,userRole);
        return ResponseEntity.ok(employee);
    }



}
