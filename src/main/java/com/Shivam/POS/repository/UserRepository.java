package com.Shivam.POS.repository;

import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    List<User> findByStore(Store store);
    List<User>findByBranchId(Long branchId);
}
