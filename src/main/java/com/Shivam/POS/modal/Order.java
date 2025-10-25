package com.Shivam.POS.modal;

import com.Shivam.POS.domain.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double totalAmount;
    private LocalDateTime createdAt;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Customer customer;
    private PaymentType paymentType;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem>items;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }

}
