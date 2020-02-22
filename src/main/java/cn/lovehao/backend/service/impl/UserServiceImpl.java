package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.config.session.SessionManager;
import cn.lovehao.backend.config.session.UserSessionManager;
import cn.lovehao.backend.entity.User;
import cn.lovehao.backend.exception.ServiceException;
import cn.lovehao.backend.mapper.UserMapper;
import cn.lovehao.backend.service.IUserService;
import cn.lovehao.backend.utils.SpringSecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserSessionManager userSessionManager;


    @Override
    public IPage<User> diyPage(Page page,User user) {
        return userMapper.selectPage(page,user);
    }

    @Override
    public void forbid(String id) {
        User user = getById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        if(SpringSecurityUtil.getCurrentUserId().equals(id)){
            throw new ServiceException("不能禁用自己");
        }

        user.setIsForbid(!user.getIsForbid());
        updateById(user);

        if(user.getIsForbid()){
            //用户已经被禁用
            log.info("id为{}的用户 {} 被强制下线",user.getId(),user.getUsername());
            userSessionManager.destroy(id);
        }

    }

    @Override
    public void add(User user) {
        int count = count(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (count > 0) {
            throw new ServiceException("用户已经存在");
        }
        //保存用户
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsDelete(false);
        user.setIsForbid(false);
        save(user);
    }

    @Override
    public void update(User user) {
        User oldUser = getById(user.getId());
        int count = count(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (count > 0 && !oldUser.getUsername().equals(user.getUsername())) {
            throw new ServiceException("用户已经存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        updateById(user);
    }

    @Override
    public void batchForbid(List<String> ids, Boolean type) {
        List<User> users = new ArrayList<>();
        for(String id : ids){
            User user = new User();
            user.setId(id);
            user.setIsForbid(type);
            users.add(user);
        }
        updateBatchById(users);
    }

    @Override
    public void batchDelete(List<String> ids) {
        List<User> users = new ArrayList<>();
        for(String id : ids){
            User user = new User();
            user.setId(id);
            user.setIsDelete(true);
            users.add(user);
        }
        updateBatchById(users);
    }

}
