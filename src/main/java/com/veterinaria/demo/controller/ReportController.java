package com.veterinaria.demo.controller;

import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.repository.UserRepository;
import com.veterinaria.demo.service.ReportService;
import com.veterinaria.demo.service.UserService;
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

    @GetMapping("/all-users")
    public ResponseEntity<byte[]> generateAllUsersReport() throws JRException {
        List<User> users = userRepository.findAll();
        byte[] pdf = reportService.generateAllUsersReport(users);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }
}
