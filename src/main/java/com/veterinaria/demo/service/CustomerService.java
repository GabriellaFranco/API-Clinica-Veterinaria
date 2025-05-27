package com.veterinaria.demo.service;

import com.veterinaria.demo.dto.customer.CreateCustomerDTO;
import com.veterinaria.demo.dto.customer.GetCustomerDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.mapper.CustomerMapper;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<GetCustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toGetCustomerDTO).toList();
    }

    public GetCustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toGetCustomerDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    public GetCustomerDTO createCustomer(CreateCustomerDTO customerDTO) {
        var customerMapped = customerMapper.toCustomer(customerDTO);
        customerMapped.setCreationDate(LocalDate.now());
        customerRepository.save(customerMapped);
        return customerMapper.toGetCustomerDTO(customerMapped);
    }
}
