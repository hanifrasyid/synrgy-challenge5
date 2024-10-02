package com.aplikasi.binarfud.util.storeprocedure;

import com.aplikasi.binarfud.repo.MerchantRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingSP {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public MerchantRepo merchantRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public  MerchantQuerySP merchantQuerySP;

    @Before
    public void init() {
        try {
            jdbcTemplate.execute(merchantQuerySP.getData);
            jdbcTemplate.execute(merchantQuerySP.getDataMerchantLikeName);
            jdbcTemplate.execute(merchantQuerySP.insertMerchant);
            jdbcTemplate.execute(merchantQuerySP.updateMerchant);
            jdbcTemplate.execute(merchantQuerySP.deletedMerchant);
        } finally {
        }
    }

    @Test
    public void saveSP(){
        Long resid = null;
        merchantRepo.saveMerchantSP(resid, "spring boot1");
    }

    @Test
    public void updateSP(){
        merchantRepo.updateMerchantSP(6L, "spring boot1");
    }

    @Test
    public void deletedSP(){
        merchantRepo.deletedMerchantSP(8L);
    }

    @Test
    public void listSP(){
        List<Object> obj =  merchantRepo.getListSP();
        System.out.println(obj);
    }

    @Test
    public void getIdSP(){
        Object obj =  merchantRepo.getMerchantById(6L);
        System.out.println(obj);
    }
}
