package com.veterinaria.demo.controller;

import com.veterinaria.demo.enums.UserProfile;
import com.veterinaria.demo.model.entity.Customer;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.repository.CustomerRepository;
import com.veterinaria.demo.repository.UserRepository;
import com.veterinaria.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/all-users")
    public ResponseEntity<byte[]> generateAllUsersReport() throws JRException {
        List<User> users = userRepository.findAll();
        byte[] pdf = reportService.generateAllUsersReport(users);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }

    @GetMapping("/veterinarians")
    public ResponseEntity<byte[]> generateVeterinariansReport() throws JRException {
        List<User> veterinarians = userRepository.findByFilter("", "", UserProfile.VETERINARIAN);
        byte[] pdf = reportService.generateVeterinariansReport(veterinarians);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/staff")
    public ResponseEntity<byte[]> generateReceptionStaffReport() throws JRException {
        List<User> receptionStaff = userRepository.findByFilter("", "", UserProfile.RECEPTION_STAFF);

        byte[] pdf = reportService.generateReceptionStaffReport(receptionStaff);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("all-customers")
    public ResponseEntity<byte[]> generateAllCustomersReport() throws JRException {
        List<Customer> customers = customerRepository.findAll();

        byte[] pdf = reportService.generateAllCustomersReport(customers);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}