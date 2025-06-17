package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.customer.CustomerRequestDTO;
import com.veterinaria.demo.model.dto.customer.CustomerResponseDTO;
import com.veterinaria.demo.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerRequestDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.name())
                .phone(customerDTO.phone())
                .cpf(customerDTO.cpf())
                .build();
    }

    public CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .cpf(customer.getCpf())
                .creationDate(customer.getCreationDate())
                .build();
    }
}
