package com.Shivam.POS.Controller;

import com.Shivam.POS.Payload.response.ApiResponse;
import com.Shivam.POS.modal.Customer;
import com.Shivam.POS.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping
    public ResponseEntity<Customer>create(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer>update(@RequestBody Customer customer, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(customerService.updateCustomer(id,customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("deleted customer successfully");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping
    public ResponseEntity<List<Customer>>getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Customer>>search(@RequestParam String q){
        return ResponseEntity.ok(customerService.searchCustomer(q));
    }

}
