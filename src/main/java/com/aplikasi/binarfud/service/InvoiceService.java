package com.aplikasi.binarfud.service;

import com.aplikasi.binarfud.entity.Merchant;
import com.aplikasi.binarfud.entity.User;
import java.util.Map;

public interface InvoiceService {
    Map generateInvoice(User user);
    Map generateReport(Merchant merchant);
}
