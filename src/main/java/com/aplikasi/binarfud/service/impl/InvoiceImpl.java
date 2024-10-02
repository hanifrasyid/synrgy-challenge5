package com.aplikasi.binarfud.service.impl;

import com.aplikasi.binarfud.entity.Merchant;
import com.aplikasi.binarfud.entity.User;
import com.aplikasi.binarfud.repo.MerchantRepo;
import com.aplikasi.binarfud.repo.UserRepo;
import com.aplikasi.binarfud.service.InvoiceService;
import com.aplikasi.binarfud.util.Config;
import com.aplikasi.binarfud.util.TemplateResponse;
import com.aplikasi.binarfud.util.jasper.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceImpl implements InvoiceService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MerchantRepo merchantRepo;

    @Autowired
    private TemplateResponse response;

    @Autowired
    private ReportService reportService;
    @Override
    public Map generateInvoice(User user) {
        try {
            log.info("generate invoice");
            if (user.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            if (user.getUsername() == null) {
                return response.Error(Config.USERNAME_REQUIRED);
            }
            Optional<User> checkDataDBUser = userRepo.findById(user.getId());
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            Long id = user.getId();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IdUser", id);
            String pathUrl = ".\\src\\main\\resources\\user2.jrxml";
            String fileName = "invoice pembelian " + user.getUsername();
            return response.success(reportService.generate_pdf(parameters,pathUrl,fileName));
        } catch (Exception e) {
            log.error("generate invoice : " + e.getMessage());
            return response.Error("generate invoice =" + e.getMessage());
        }
    }
    @Override
    public Map generateReport(Merchant merchant) {
        try {
            log.info("generate report");
            if (merchant.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            if (merchant.getMerchant_name() == null) {
                return response.Error(Config.MERCHANT_NAME_REQUIRED);
            }
            Optional<Merchant> checkDataDBMerchant = merchantRepo.findById(merchant.getId());
            if (!checkDataDBMerchant.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }
            Long id = merchant.getId();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IdMerchant", id);
            String pathUrl = ".\\src\\main\\resources\\merchant.jrxml";
            String fileName = "reporting merchant " + merchant.getMerchant_name();
            return response.success(reportService.generate_pdf(parameters,pathUrl,fileName));
        } catch (Exception e) {
            log.error("generate report : " + e.getMessage());
            return response.Error("generate report =" + e.getMessage());
        }
    }
}