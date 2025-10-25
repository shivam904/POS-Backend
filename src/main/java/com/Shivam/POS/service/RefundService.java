package com.Shivam.POS.service;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.RefundDto;
import com.Shivam.POS.modal.Refund;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {
    RefundDto createRefund(RefundDto refund) throws Exception;
    List<RefundDto>getAllRefunds() throws Exception;
    List<RefundDto> getRefundByCashier(Long cashierId) throws Exception;
    List<RefundDto> getRefundByShiftReport(Long shirtReportId) throws Exception;
    List<RefundDto>getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    List<RefundDto>getRefundByBranch(Long branchId) throws Exception;
    RefundDto getRefundById(Long refundId) throws Exception;
    void deleteRefund(Long refundId) throws Exception;
}
