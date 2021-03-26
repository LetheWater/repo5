package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Member;
import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IPermissionDao {

    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findPermissionByRoleId(String id) throws Exception;

    @Select("select * from permission")
    List<Permission> findAll();

    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission);

    @Select("select * from permission where id = #{permissionId}")
    Permission findById(String permissionId);

    @Delete("delete from role_permission where permissionId=#{permissionId}")
    void deleteFromRole_Permission(String permissionId);

    @Delete("delete from permission where id=#{permissionId}")
    void deleteById(String permissionId);
}
