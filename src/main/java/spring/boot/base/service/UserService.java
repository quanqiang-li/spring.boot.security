package spring.boot.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import spring.boot.base.dao.UserDao;
import spring.boot.base.entity.SysUser;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Cacheable(cacheNames = "authority", key = "#username")
    public SysUser getUserByName(String username) {
        return userDao.selectByName(username);
    }

}