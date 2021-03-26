package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPermissionService {
    List<Permission> findAll() throws Exception;

    void save(Permission permission)throws Exception;

    Permission findById(String permissionId)throws Exception;

    void deleteRole(String permissionId);
}
