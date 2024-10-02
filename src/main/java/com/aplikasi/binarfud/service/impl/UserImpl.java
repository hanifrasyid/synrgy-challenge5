package com.aplikasi.binarfud.service.impl;

import com.aplikasi.binarfud.entity.User;
import com.aplikasi.binarfud.repo.UserRepo;
import com.aplikasi.binarfud.service.UserService;
import com.aplikasi.binarfud.util.Config;
import com.aplikasi.binarfud.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map addUser(User user) {
        try {
            log.info("add user");
            return response.success(userRepo.save(user));
        } catch (Exception e){
            log.error("add user error : " + e.getMessage());
            return response.Error("add user = " + e.getMessage());
        }
    }

    @Override
    public Map updateUser(User user) {
        try {
            log.info("update user");
            if (user.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<User> checkDataDBUser = userRepo.findById(user.getId());
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            checkDataDBUser.get().setEmail(user.getEmail());
            checkDataDBUser.get().setUsername(user.getUsername());
            checkDataDBUser.get().setPassword(user.getPassword());
            checkDataDBUser.get().setUpdated_date(new Date());

            return response.success(userRepo.save(checkDataDBUser.get()));
        } catch (Exception e){
            log.error("update user error : " + e.getMessage());
            return response.Error("update user = " + e.getMessage());
        }
    }

    @Override
    public Map deleteUser(User user) {
        try {
            log.info("delete user");
            if (user.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<User> checkDataDBUser = userRepo.findById(user.getId());
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }

            checkDataDBUser.get().setDeleted_date(new Date());
            userRepo.save(checkDataDBUser.get());
            return response.success(Config.SUCCESS);
        } catch (Exception e){
            log.error("delete user error : " + e.getMessage());
            return response.Error("delete user = " + e.getMessage());
        }
    }

    @Override
    public Map getByID(Long user) {
        Optional<User> getBaseOptional = userRepo.findById(user);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }
}
