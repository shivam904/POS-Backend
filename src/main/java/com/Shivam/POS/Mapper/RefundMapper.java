package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.RefundDto;
import com.Shivam.POS.modal.Refund;

public class RefundMapper {
    public static RefundDto toDto(Refund refund){
        return RefundDto.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .cashierName(refund.getCashier().getFullName())
                .branchId(refund.getBranch().getId())
                .shiftReportId(refund.getShiftReport()!=null?refund.getShiftReport().getId():null)
                .createdAt(refund.getCreatedAt())
                .build();

    }
}
