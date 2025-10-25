package com.Shivam.POS.Controller;


import com.Shivam.POS.Payload.dto.OrderDto;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.domain.OrderStatus;
import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDto>> getOrdersByBranch(@PathVariable Long branchId, @RequestParam(required = false) Long cashierId, @RequestParam(required = false) Long customerId, @RequestParam(required = false)PaymentType paymentType, @RequestParam(required = false)OrderStatus orderStatus) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByBranch(branchId,customerId,cashierId,paymentType,orderStatus));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDto>> getOrderByCashier(@PathVariable Long cashierId) throws Exception {
        return ResponseEntity.ok(orderService.getOrderByCashier(cashierId));
    }

    @GetMapping("/today/branch/{id}")
    public ResponseEntity<List<OrderDto>> getTodayOrder(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDto>> getCustomersOrder(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(id));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDto>>getRecentOrder(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>deleteOrder(@PathVariable Long id) throws Exception {
        orderService.deleteOrder(id);
        ApiResponse apiResponse=new ApiResponse();
        return ResponseEntity.ok(apiResponse);
    }


}
