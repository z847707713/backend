package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.entity.User;
import cn.lovehao.backend.mapper.UserMapper;
import cn.lovehao.backend.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public IPage<User> diyPage(Page page) {
        IPage iPage = page(page,
                new QueryWrapper<User>()
                        .select("id","username","nick_name","initials","head_img","signature","phone","email"
                                ,"is_forbid","is_delete","create_by","create_time","update_by","update_time")
                .eq("is_delete",false));
        return iPage;
    }

}
