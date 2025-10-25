package com.Shivam.POS.Payload.dto;

import com.Shivam.POS.domain.StoreStatus;
import com.Shivam.POS.modal.StoreContact;
import com.Shivam.POS.modal.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

        private Long id;

        private String brand;
        private UserDto storeAdmin;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private String description;
        private String storeType;

        private StoreStatus status;
        private StoreContact contact;




}
