package com.Shivam.POS.repository;

import com.Shivam.POS.modal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreAdminId(Long adminId);

}
