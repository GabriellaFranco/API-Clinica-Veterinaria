package com.veterinaria.demo.mapper;

import com.veterinaria.demo.dto.customer.CreateCustomerDTO;
import com.veterinaria.demo.dto.customer.GetCustomerDTO;
import com.veterinaria.demo.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {

    public Customer toCustomer(CreateCustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.name())
                .phone(customerDTO.phone())
                .creationDate(LocalDate.now())
                .cpf(customerDTO.cpf())
                .build();
    }

    public GetCustomerDTO toGetCustomerDTO(Customer customer) {
        return GetCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .cpf(customer.getCpf())
                .creationDate(customer.getCreationDate())
                .build();
    }
}
