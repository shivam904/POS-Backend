package com.Shivam.POS.Mapper;

import com.Shivam.POS.Payload.dto.OrderDto;
import com.Shivam.POS.Payload.dto.ProductDto;
import com.Shivam.POS.Payload.dto.RefundDto;
import com.Shivam.POS.Payload.dto.ShiftReportDto;
import com.Shivam.POS.modal.Order;
import com.Shivam.POS.modal.Product;
import com.Shivam.POS.modal.Refund;
import com.Shivam.POS.modal.ShiftReport;

import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDto toDto(ShiftReport shiftReport){
        return ShiftReportDto.builder()
                .id(shiftReport.getId())
                .shiftEnd(shiftReport.getShiftEnd())
                .shiftStart(shiftReport.getShiftStart())
                .totalSales(shiftReport.getTotalSales())
                .totalOrders(shiftReport.getTotalOrders())
                .netSale(shiftReport.getNetSale())
                .totalRefunds(shiftReport.getTotalRefunds())
                .totalRefunds(shiftReport.getTotalRefunds())
                .cashier(UserMapper.toDTO(shiftReport.getCashier()))
                .cashierId(shiftReport.getCashier().getId())
                .branchId(shiftReport.getBranch().getId())
                .recentOrders(mapOrders(shiftReport.getRecentOrders()))
                .topSellingProducts(mapProducts(shiftReport.getTopSellingProducts()))
                .refunds(mapRefund(shiftReport.getRefunds()))
                .paymentSummaries(shiftReport.getPaymentSummaries())
                .build();
    }
    private static List<OrderDto>mapOrders(List<Order>recentOrders){
        if(recentOrders==null || recentOrders.isEmpty()){
            return null;
        }
        return recentOrders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    private static List<ProductDto>mapProducts(List<Product>products){
        if(products==null || products.isEmpty()){
            return null;
        }
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    private static List<RefundDto>mapRefund(List<Refund>refunds){
        if(refunds==null || refunds.isEmpty()){
            return null;
        }
        return refunds.stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }


}
