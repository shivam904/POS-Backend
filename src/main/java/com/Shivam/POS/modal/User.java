package com.Shivam.POS.modal;

import com.Shivam.POS.domain.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false,unique = true)
    @Email(message = "email should be valid")
    private String email;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Store store;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserRole role;

    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
