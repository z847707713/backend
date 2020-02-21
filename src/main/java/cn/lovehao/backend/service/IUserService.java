package cn.lovehao.backend.service;

import cn.lovehao.backend.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
public interface IUserService extends IService<User> {

     IPage<User> diyPage(Page page);

     void forbid(String id);

     void add(User user);

     void update(User user);

}
