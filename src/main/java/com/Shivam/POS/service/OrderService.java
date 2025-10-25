package com.Shivam.POS.service;

import com.Shivam.POS.Payload.dto.OrderDto;
import com.Shivam.POS.domain.OrderStatus;
import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.modal.Order;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto) throws Exception;
    OrderDto getOrderById(Long id) throws Exception;
    List<OrderDto> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status);
    List<OrderDto> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws Exception;
    List<OrderDto>getTodayOrdersByBranch(Long branchId);
    List<OrderDto> getOrdersByCustomerId(Long customerId);
    List<OrderDto>getTop5RecentOrdersByBranchId(Long branchId);


}
