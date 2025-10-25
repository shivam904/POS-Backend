package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Order;
import com.Shivam.POS.modal.ShiftReport;
import com.Shivam.POS.modal.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefundDto {
    private Long id;

    private OrderDto order;
    private Long orderId;

    private String reason;
    private  Double amount;

//    private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDto cashier;
    private String cashierName;

    private BranchDto branch;
    private Long branchId;

    private PaymentType paymentType;
    private LocalDateTime createdAt;

}
