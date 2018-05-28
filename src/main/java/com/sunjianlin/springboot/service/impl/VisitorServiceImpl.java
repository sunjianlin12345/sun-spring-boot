package com.sunjianlin.springboot.service.impl;

import com.sunjianlin.springboot.entity.VisitorEntity;
import com.sunjianlin.springboot.dao.VisitorMapper;
import com.sunjianlin.springboot.service.IVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* Created by sunjianlin
* 2018年05月28日 16:12:30
*/
@Service("visitorService")
public class VisitorServiceImpl implements IVisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public int add(VisitorEntity visitor) {
        return visitorMapper.insert(visitor);
    }

}