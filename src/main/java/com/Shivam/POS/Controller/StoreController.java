package com.Shivam.POS.Controller;

import com.Shivam.POS.Exceptions.UserException;
import com.Shivam.POS.Mapper.StoreMapper;
import com.Shivam.POS.Payload.dto.StoreDto;
import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.domain.StoreStatus;
import com.Shivam.POS.modal.Store;
import com.Shivam.POS.modal.User;
import com.Shivam.POS.service.StoreService;
import com.Shivam.POS.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto, @RequestHeader("Authorization") String jwt) throws UserException {
        User user =userService.getUserFromJwt(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto,user));

    }
    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(id));

    }
    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStore(@RequestHeader("Authorization") String jwt) throws UserException {
        return ResponseEntity.ok(storeService.getAllStore());

    }
    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(@RequestHeader("Authorization") String jwt) throws UserException {

        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee( @RequestHeader("Authorization") String jwt) throws UserException {
        return ResponseEntity.ok(storeService.getStoreByEmployee());

    }
    @PutMapping("/{id}")
    public ResponseEntity<StoreDto>updateStore(@PathVariable Long id, @RequestBody StoreDto storeDto) throws UserException {

      return ResponseEntity.ok(storeService.updateStore(id,storeDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws UserException {
        storeService.deleteStore(id);
        ApiResponse apiResponse= new ApiResponse();
        apiResponse.setMessage("store deleted message successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDto>moderateStore(@PathVariable Long id, @RequestParam StoreStatus status) throws Exception {


        return ResponseEntity.ok(storeService.moderateStore(id,status));
    }


}
