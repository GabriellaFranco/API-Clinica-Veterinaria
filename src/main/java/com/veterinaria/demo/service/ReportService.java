package com.veterinaria.demo.service;

import com.veterinaria.demo.model.entity.Customer;
import com.veterinaria.demo.model.entity.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public <T> byte[] generateReport(String jrxmlPath, List<T> data, Map<String, Object> parameters) throws JRException {
        try (InputStream templateStream = getClass().getResourceAsStream(jrxmlPath)) {
            if (templateStream == null) {
                throw new IllegalArgumentException("Template not found on path: " + jrxmlPath);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
            if (parameters == null) {
                parameters = new HashMap<>();
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (IOException e) {
            throw new JRException("Template file error: ", e);
        }
    }

    public byte[] generateAllUsersReport(List<User> users) throws JRException {
        return generateReport("/reports/user/AllUsersReport.jrxml", users, null);
    }

    public byte[] generateVeterinariansReport(List<User> veterinarians) throws JRException {
        return generateReport("/reports/user/VeterinariansReport.jrxml", veterinarians, null);
    }

    public byte[] generateReceptionStaffReport(List<User> receptionStaff) throws JRException {
        return generateReport("/reports/user/ReceptionStaffReport.jrxml", receptionStaff, null);
    }

    public byte[] generateAllCustomersReport(List<Customer> customers) throws JRException {
        return generateReport("/reports/customer/AllCustomersReport.jrxml", customers, null);
    }
}
