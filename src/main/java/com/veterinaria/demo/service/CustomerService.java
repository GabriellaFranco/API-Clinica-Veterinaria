package com.veterinaria.demo.service;

import com.veterinaria.demo.exception.OperationNotAllowedException;
import com.veterinaria.demo.model.dto.customer.CustomerRequestDTO;
import com.veterinaria.demo.model.dto.customer.CustomerResponseDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.customer.CustomerUpdateDTO;
import com.veterinaria.demo.model.entity.Customer;
import com.veterinaria.demo.model.mapper.CustomerMapper;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        validateCPF(customerDTO.cpf());
        var customerMapped = customerMapper.toCustomer(customerDTO);

        customerMapped.setCreationDate(LocalDate.now());
        customerRepository.save(customerMapped);

        return customerMapper.toCustomerResponseDTO(customerMapped);
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO customerDTO) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));

        applyUpdates(customer, customerDTO);
        var customerUpdated = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDTO(customerUpdated);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        var customerToDelete = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
        customerRepository.delete(customerToDelete);
    }

    private void applyUpdates(Customer customer, CustomerUpdateDTO customerDTO) {
        Optional.ofNullable(customerDTO.name())
                .filter(name -> !name.isBlank())
                .ifPresent(customer::setName);

        Optional.ofNullable(customerDTO.phone())
                .filter(phone -> !phone.isBlank())
                .ifPresent(customer::setPhone);
    }

    private void validateCPF(String cpf) {
        var customer = customerRepository.findByCpf(cpf);
        if (customer.isPresent()) {
            throw new OperationNotAllowedException("CPF already registered");
        }
    }
}
