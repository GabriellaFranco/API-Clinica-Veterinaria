package com.veterinaria.demo.service.reportService;

import com.veterinaria.demo.model.entity.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserReportService {

    public byte[] generateAllUsersReport(List<User> users) throws JRException {
        InputStream templateStream = getClass().getResourceAsStream("/reports/user/AllUsersReport.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
