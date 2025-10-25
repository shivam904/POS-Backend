package com.Shivam.POS.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSale;
    private int totalOrders;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;

    @Transient
    private List<PaymentSummary>paymentSummaries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product>topSellingProducts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order>recentOrders;

    @OneToMany(mappedBy = "shiftReport",cascade = CascadeType.ALL)
    private List<Refund>refunds;





}
