package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.ILogAopDao;
import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ILogAopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogAopService implements ILogAopService {

    @Autowired
    private ILogAopDao logAopDao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        logAopDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() throws Exception {
        return logAopDao.findAll();
    }
}
