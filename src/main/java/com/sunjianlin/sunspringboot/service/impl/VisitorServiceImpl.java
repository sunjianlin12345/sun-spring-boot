package com.sunjianlin.sunspringboot.service.impl;
import com.sunjianlin.sunspringboot.dao.VisitorMapper;
import com.sunjianlin.sunspringboot.entity.VisitorEntity;
import com.sunjianlin.sunspringboot.service.IVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sunjianlin
 * 2018年05月11日 16:00:12
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
