package com.aplikasi.binarfud.service;

import com.aplikasi.binarfud.entity.Merchant;
import java.util.List;
import java.util.Map;

public interface MerchantService {
    Map addMerchant(Merchant merchant);
    Map update(Merchant merchant);
    Map updateMerchantStatus(Merchant merchant);
    Map delete(Merchant merchant);
    Map getByID(Long merchant);
    List<Merchant> getOpenMerchant();
}
