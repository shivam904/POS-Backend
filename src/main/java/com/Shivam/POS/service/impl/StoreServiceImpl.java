package com.Shivam.POS.service.impl;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.StoreMapper;
import com.Shivam.POS.Payload.dto.StoreDto;
import com.Shivam.POS.domain.StoreStatus;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.StoreContact;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.repository.StoreRepository;
import com.Shivam.POS.service.StoreService;
import com.Shivam.POS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository  storeRepository;
    private final UserService userService;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {
Store store= StoreMapper.toEntity(storeDto,user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) throws Exception {
        Store store= storeRepository.findById(id).orElseThrow(()->new Exception("Store not Found"));

        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDto> getAllStore() {

        return storeRepository.findAll().stream().map(StoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin= userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());


    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws UserException {
        User currUser= userService.getCurrentUser();
        Store existing= storeRepository.findByStoreAdminId(currUser.getId());
        if(existing==null){
            throw new UserException("store not found");
        }
        existing.setBrand(storeDto.getBrand());
        existing.setDescription(storeDto.getDescription());
        if(storeDto.getStoreType() != null){
            existing.setStoreType(storeDto.getStoreType());
        }
        if(storeDto.getContact() !=null){
            StoreContact contact=StoreContact.builder().address(storeDto.getContact().getAddress())
                    .email(storeDto.getContact().getEmail())
                    .phone(storeDto.getContact().getPhone())
                    .build();
            existing.setContact(contact);
        }
        Store updatedStore=storeRepository.save(existing);
        return StoreMapper.toDTO(updatedStore);


    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store=getStoreByAdmin();
        storeRepository.delete(store);

    }

    @Override
    public StoreDto getStoreByEmployee() throws UserException {
        User currUser= userService.getCurrentUser();

        if(currUser ==null){
            throw new UserException("You dont have permission");
        }
        return StoreMapper.toDTO(currUser.getStore());
    }
    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) throws Exception {
        Store store= storeRepository.findById(id).orElseThrow(
                ()->new Exception("store not found")
        );
        store.setStatus(status);
        Store updatedStore= storeRepository.save(store);
        return StoreMapper.toDTO(updatedStore);
    }
}
