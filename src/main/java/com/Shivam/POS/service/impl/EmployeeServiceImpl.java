package com.Shivam.POS.service.impl;

import com.Shivam.POS.Mapper.UserMapper;
import com.Shivam.POS.Payload.dto.UserDto;
import com.Shivam.POS.domain.UserRole;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.BranchRepository;
import com.Shivam.POS.repository.ProductRepository;
import com.Shivam.POS.repository.StoreRepository;
import com.Shivam.POS.repository.UserRepository;
import com.Shivam.POS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store= storeRepository.findById(storeId).orElseThrow(()->new Exception("branch not exist..."));
        Branch branch=null;
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw new Exception("branch id is required to create branch manager");
            }
            branch= branchRepository.findById(employee.getBranchId()).orElseThrow(()->new Exception("branch no found"));
        }
        User user= UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        User savedEmployee= userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);

    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {
        Branch branch= branchRepository.findById(employee.getBranchId()).orElseThrow(()->new Exception("branch not found"));
        if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER || employee.getRole() ==UserRole.ROLE_BRANCH_MANAGER){
                User user=UserMapper.toEntity(employee);
                user.setBranch(branch);
                user.setPassword(passwordEncoder.encode(employee.getPassword()));
                return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("Branch role not supported");
    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {
        User existing =userRepository.findById(employeeId).orElseThrow(()->new  Exception("employee doesn't exist"));
        Branch branch= branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(()->new Exception("branch not found"));
        existing.setEmail(employeeDetails.getEmail());
        existing.setFullName(employeeDetails.getFullName());
        existing.setPassword(employeeDetails.getPassword());
        existing.setRole(employeeDetails.getRole());
        existing.setBranch(branch);
        return userRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User existing =userRepository.findById(employeeId).orElseThrow(()->new  Exception("employee doesn't exist"));
        userRepository.delete(existing);

    }

    @Override
    public List<UserDto> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store= storeRepository.findById(storeId).orElseThrow(()->new Exception("branch not exist..."));

        return userRepository.findByStore(store).stream().filter(
                user -> role==null || user.getRole()==role
        ).map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch= branchRepository.findById(branchId).orElseThrow(()->new Exception("branch not found"));
        return userRepository.findByBranchId(branchId).stream().filter(
                user -> role==null || user.getRole()==role

        ).map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
