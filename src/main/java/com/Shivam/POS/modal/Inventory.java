package com.Shivam.POS.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private Product product;
    @Column(nullable = false)
    private int quantity;
    private LocalDateTime lastUpdate;

    @PrePersist
    @PreUpdate
    protected void onUpdate(){
        lastUpdate=LocalDateTime.now();
    }

}
