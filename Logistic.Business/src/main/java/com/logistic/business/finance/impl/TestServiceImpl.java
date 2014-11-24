package com.logistic.business.finance.impl;

import com.logistic.business.base.impl.AbstractService;
import com.logistic.business.finance.ITestService;
import com.logistic.dao.base.IBaseDao;
import com.logistic.domain.Test;

/**
 * Created by kafein on 10/26/2014.
 */
public class TestServiceImpl extends AbstractService<Test> implements ITestService{

    public TestServiceImpl(IBaseDao<Test> baseDao) {
        super(baseDao);
    }
}
