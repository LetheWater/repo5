package com.itheima.ssm.service;

import com.itheima.ssm.domain.SysLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILogAopService {

    void save(SysLog sysLog) throws Exception;

    List<SysLog> findAll() throws Exception;
}
