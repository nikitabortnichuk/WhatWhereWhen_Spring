package com.bortni.service;

import com.bortni.model.entity.Admin;
import com.bortni.model.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin findByLoginAndPassword(String login, String password){
        return adminRepository.findByLoginAndPassword(login, password);
    }

}
