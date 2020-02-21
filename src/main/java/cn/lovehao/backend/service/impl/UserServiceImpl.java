package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.entity.User;
import cn.lovehao.backend.mapper.UserMapper;
import cn.lovehao.backend.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public IPage<User> diyPage(Page page) {
        IPage iPage = page(page,
                new QueryWrapper<User>()
                        .select("id", "username", "nick_name", "initials", "head_img", "signature", "phone", "email"
                                , "is_forbid", "is_delete", "create_by", "create_time", "update_by", "update_time")
                        .eq("is_delete", false));
        return iPage;
    }

    @Override
    public void forbid(String id) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setIsForbid(!user.getIsForbid());
        updateById(user);
    }

    @Override
    public void add(User user) {
        int count = count(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (count > 0) {
            throw new RuntimeException("用户已经存在");
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
            throw new RuntimeException("用户已经存在");
        }
        updateById(user);
    }

}
