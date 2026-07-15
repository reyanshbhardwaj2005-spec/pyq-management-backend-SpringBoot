package com.iiitsonepat.questionbank.service.impl;

import com.iiitsonepat.questionbank.repository.AdminRepository;
import com.iiitsonepat.questionbank.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}
