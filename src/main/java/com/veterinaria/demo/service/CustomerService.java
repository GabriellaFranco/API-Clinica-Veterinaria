package com.veterinaria.demo.service;

import com.veterinaria.demo.model.dto.customer.CustomerRequestDTO;
import com.veterinaria.demo.model.dto.customer.CustomerResponseDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.mapper.CustomerMapper;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toCustomerResponseDTO).toList();
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toCustomerResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    public List<CustomerResponseDTO> getCustomersByFilter(String name, String phone, String cpf, LocalDate creationDate) {
        return customerRepository.findByFilter(name, phone, cpf, creationDate)
                .stream().map(customerMapper::toCustomerResponseDTO).toList();
    }
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
        var customerMapped = customerMapper.toCustomer(customerDTO);

        customerMapped.setCreationDate(LocalDate.now());
        customerRepository.save(customerMapped);

        return customerMapper.toCustomerResponseDTO(customerMapped);
    }
}
