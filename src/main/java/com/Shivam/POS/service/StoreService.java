package com.Shivam.POS.service;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Payload.dto.StoreDto;
import com.Shivam.POS.domain.StoreStatus;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto, User user);
    StoreDto getStoreById(Long id) throws Exception;
    List<StoreDto> getAllStore();
    Store getStoreByAdmin() throws UserException;
    StoreDto updateStore(Long id, StoreDto storeDto) throws UserException;
    void deleteStore(Long id) throws UserException;
    StoreDto getStoreByEmployee() throws UserException;
    StoreDto moderateStore(Long id, StoreStatus status ) throws Exception;
}
