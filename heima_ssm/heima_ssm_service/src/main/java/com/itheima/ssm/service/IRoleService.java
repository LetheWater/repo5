package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRoleService {
    List<Role> findAll() throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId) throws Exception;

    void deleteRole(String roleId) throws Exception;

    List<Permission> findOtherPermission(String roleId)throws Exception;

    void addPermissionToRole(String roleId, String[] permissionIds)throws Exception;
}
