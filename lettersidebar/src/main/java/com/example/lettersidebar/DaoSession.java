package com.example.lettersidebar;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.lettersidebar.ConstactBean;

import com.example.lettersidebar.ConstactBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig constactBeanDaoConfig;

    private final ConstactBeanDao constactBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        constactBeanDaoConfig = daoConfigMap.get(ConstactBeanDao.class).clone();
        constactBeanDaoConfig.initIdentityScope(type);

        constactBeanDao = new ConstactBeanDao(constactBeanDaoConfig, this);

        registerDao(ConstactBean.class, constactBeanDao);
    }
    
    public void clear() {
        constactBeanDaoConfig.clearIdentityScope();
    }

    public ConstactBeanDao getConstactBeanDao() {
        return constactBeanDao;
    }

}
