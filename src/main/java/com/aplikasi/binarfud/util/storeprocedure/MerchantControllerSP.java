package com.aplikasi.binarfud.util.storeprocedure;

import com.aplikasi.binarfud.repo.MerchantRepo;
import com.aplikasi.binarfud.service.MerchantService;
import com.aplikasi.binarfud.util.SimpleStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/merchant/sp")
public class MerchantControllerSP {

    @Autowired
    public MerchantRepo merchantRepo;

    @Autowired
    public MerchantService merchantService;

    @Autowired
    public DataSource dataSource;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Map> getById() {
        Map map = new HashMap();
        map.put("list",merchantRepo.getListSP());
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
