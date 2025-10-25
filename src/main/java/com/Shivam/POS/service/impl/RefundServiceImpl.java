package com.Shivam.POS.service.impl;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.RefundMapper;
import com.Shivam.POS.Payload.dto.BranchDto;
import com.Shivam.POS.Payload.dto.RefundDto;
import com.Shivam.POS.modal.Branch;
import com.Shivam.POS.modal.Order;
import com.Shivam.POS.modal.Refund;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.OrderRepository;
import com.Shivam.POS.repository.RefundRepository;
import com.Shivam.POS.service.RefundService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;
    @Override
    public RefundDto createRefund(RefundDto refund) throws Exception {
        User cashier= userService.getCurrentUser();
        Order order= orderRepository.findById(refund.getOrderId()).orElseThrow(
                ()->new Exception("Order not found")
        );
        Branch branch=order.getBranch();
        Refund createdRefund= Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .createdAt(refund.getCreatedAt())
                .build();
        Refund savedRefund= refundRepository.save(createdRefund);
        return RefundMapper.toDto(savedRefund);

    }

    @Override
    public List<RefundDto> getAllRefunds() throws Exception{

        return refundRepository.findAll().stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByCashier(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByShiftReport(Long shirtReportId) throws Exception {

        return refundRepository.findByShiftReportId(shirtReportId).stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) throws Exception{
        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId,startDate,endDate).stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByBranch(Long branchId) throws Exception{
        return refundRepository.findByBranchId(branchId).stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RefundDto getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId).map(RefundMapper::toDto).orElseThrow(
                ()->new Exception("Refund not found")
        );
    }

    @Override
    public void deleteRefund(Long refundId)throws Exception {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);
    }
}
