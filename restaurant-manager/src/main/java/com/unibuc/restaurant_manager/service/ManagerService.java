package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.mapper.ManagerMapper;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public final class ManagerService extends UserService<Manager, ManagerDto> {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    protected JpaRepository<Manager, Integer> getRepository() {
        return managerRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Manager";
    }

    @Override
    protected ManagerMapper getMapper() {
        return managerMapper;
    }

}
