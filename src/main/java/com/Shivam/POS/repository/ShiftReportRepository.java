package com.Shivam.POS.repository;

import com.Shivam.POS.modal.ShiftReport;
import com.Shivam.POS.modal.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport,Long> {

    List<ShiftReport>findByCashierId(Long id);
    List<ShiftReport>findByBranchId(Long id);

    Optional<ShiftReport>findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(User cashier);
    Optional<ShiftReport>findByCashierAndShiftStartBetween(User cashier, LocalDateTime start, LocalDateTime end);
}
