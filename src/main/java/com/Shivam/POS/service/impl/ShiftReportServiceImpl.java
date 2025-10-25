package com.Shivam.POS.service.impl;

import com.Shivam.POS.Mapper.ShiftReportMapper;
import com.Shivam.POS.Payload.dto.ShiftReportDto;
import com.Shivam.POS.domain.PaymentType;
import com.Shivam.POS.modal.*;
import com.Shivam.POS.repository.OrderRepository;
import com.Shivam.POS.repository.RefundRepository;
import com.Shivam.POS.repository.ShiftReportRepository;
import com.Shivam.POS.repository.UserRepository;
import com.Shivam.POS.service.ShiftReportService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;
    private final UserService userService;


    @Override
    public ShiftReportDto startShift() throws Exception {
        User currentUser= userService.getCurrentUser();
        LocalDateTime shiftStart=LocalDateTime.now();
        LocalDateTime startOfDay=shiftStart.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime endOfDay=shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport>existing= shiftReportRepository.findByCashierAndShiftStartBetween(currentUser,startOfDay,endOfDay);

        if(existing.isPresent()){
            throw new Exception("Already started shift.");
        }
        Branch branch=currentUser.getBranch();
        ShiftReport shiftReport= ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(shiftStart)
                .branch(branch)
                .build();
        ShiftReport saved= shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDto(saved);
    }

    @Override
    public ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {
        User currentUser= userService.getCurrentUser();
        ShiftReport shiftReport= shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(()->new Exception("Shift not found"));
        shiftReport.setShiftEnd(shiftEnd);

        List<Refund> refunds=refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(),shiftReport.getShiftStart(),shiftReport.getShiftEnd());

        double totalRefunds= refunds.stream().mapToDouble(refund->refund.getAmount() != null ? refund.getAmount():0.0).sum();

        List<Order>orders=orderRepository.findByCashierAndCreatedAtBetween(currentUser,shiftReport.getShiftStart(),shiftReport.getShiftEnd());
        double totalSales= orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders=orders.size();

        double netSales=totalSales-totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport saved=shiftReportRepository.save(shiftReport);



        return ShiftReportMapper.toDto(saved);
    }
    private List<Order> getRecentOrders(List<Order> orders){
        return orders.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
    private List<Product> getTopSellingProducts(List<Order>orders){
        Map<Product,Integer>m= new HashMap<>();
        for(Order order:orders){
            for(OrderItem item:order.getItems()){
                Product product=item.getProduct();
                m.put(product,m.getOrDefault(product,0)+item.getQuantity());
            }
        }
        return m.entrySet().stream()
                .sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }
    private  List<PaymentSummary>getPaymentSummaries(List<Order>orders,double totalSales){

        Map<PaymentType,List<Order>>grouped=orders.stream()
                .collect(Collectors.groupingBy(order->order.getPaymentType() !=null?order.getPaymentType():PaymentType.CASH));

        List<PaymentSummary>summaries=new ArrayList<>();
        for(Map.Entry<PaymentType,List<Order>>entry:grouped.entrySet()){
            double amount=entry.getValue().stream().mapToDouble(Order::getTotalAmount).sum();
            int transactions=entry.getValue().size();
            double percentage=(amount/totalSales)*100;

            PaymentSummary paymentSummary=new PaymentSummary();
            paymentSummary.setType(entry.getKey());
            paymentSummary.setPercentage(percentage);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setTotalAmount(amount);
            summaries.add(paymentSummary);
        }
        return summaries;

    }


    @Override
    public ShiftReportDto getShiftReportById(Long id) throws Exception {

        return shiftReportRepository.findById(id).map(ShiftReportMapper::toDto)
                .orElseThrow(()->new Exception("report nor found"));
    }

    @Override
    public List<ShiftReportDto> getAllShiftReports() {
        List<ShiftReport> reports= shiftReportRepository.findAll();

        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByBranchId(Long branchId) {
        List<ShiftReport> reports= shiftReportRepository.findByBranchId(branchId);
        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByCashierId(Long cashierId) {
        List<ShiftReport> reports= shiftReportRepository.findByCashierId(cashierId);
        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception {
        User user= userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(user).orElseThrow(()->new Exception("no active shift for cashier"));
        LocalDateTime now= LocalDateTime.now();

        List<Order> orders=orderRepository.findByCashierAndCreatedAtBetween(
               user,shiftReport.getShiftStart(),now
        );

        List<Refund> refunds=refundRepository.findByCashierIdAndCreatedAtBetween(user.getId(),shiftReport.getShiftStart(),now);

        double totalRefunds= refunds.stream().mapToDouble(refund->refund.getAmount() != null ? refund.getAmount():0.0).sum();

        double totalSales= orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders=orders.size();

        double netSales=totalSales-totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport saved=shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(saved);
    }

    @Override
    public ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {

        User cashier= userRepository.findById(cashierId).orElseThrow(()->new Exception("Cashier not found"));
        LocalDateTime start= date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end= date.withHour(23).withMinute(59).withSecond(59);
        ShiftReport report=shiftReportRepository.findByCashierAndShiftStartBetween(cashier,start,end).orElseThrow(()->new Exception("Cashier not found"));
        return ShiftReportMapper.toDto(report);
    }
}
