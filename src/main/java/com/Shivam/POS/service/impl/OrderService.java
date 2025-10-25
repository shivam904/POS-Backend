package com.Shivam.POS.service.impl;

import com.Shivam.POS.Mapper.OrderMapper;
import com.Shivam.POS.Payload.dto.OrderDto;
import com.Shivam.POS.domain.OrderStatus;
import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.modal.*;
import com.Shivam.POS.repository.OrderItemRepository;
import com.Shivam.POS.repository.OrderRepository;
import com.Shivam.POS.repository.ProductRepository;
import com.Shivam.POS.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements com.Shivam.POS.service.OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws Exception {
        User cashier=userService.getCurrentUser();
        Branch branch=cashier.getBranch();
        if(branch==null)throw new Exception("cashier branch not found");
        Order order=Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDto.getCustomer())
                .paymentType(orderDto.getPaymentType())
                .build();
        List<OrderItem>orderItems=orderDto.getItems().stream().map(orderItemDto ->{
            Product product=productRepository.findById(orderItemDto.getProductId()).orElseThrow(()->new EntityNotFoundException("product not found"));
            return OrderItem.builder()
                    .product(product)
                    .quantity(orderItemDto.getQuantity())
                    .price(product.getSellingPrice()*orderItemDto.getQuantity())
                    .order(order)
                    .build();

        } ).toList();
        double total=orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order savedOrder=orderRepository.save(order);


        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) throws Exception {
        return OrderMapper.toDto(orderRepository.findById(id).orElseThrow(()->new Exception("order not found")));
    }

    @Override
    public List<OrderDto> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) {
        return orderRepository.findByBranchId(branchId).stream().filter(
                order -> customerId==null || (order.getCustomer() !=null && order.getCustomer().getId().equals(customerId))
        ).filter(
                order -> cashierId==null || (order.getCashier() !=null && order.getCashier().getId().equals(cashierId))
        ).filter(order -> paymentType==null || order.getPaymentType()==paymentType).map(OrderMapper::toDto).collect(Collectors.toList());


    }

    @Override
    public List<OrderDto> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order=orderRepository.findById(id).orElseThrow(()->new Exception("order not found"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getTodayOrdersByBranch(Long branchId) {
        LocalDate today= LocalDate.now();
        LocalDateTime start= today.atStartOfDay();
        LocalDateTime end=today.plusDays(1).atStartOfDay();


        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId,start,end).stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getTop5RecentOrdersByBranchId(Long branchId) {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }
}
