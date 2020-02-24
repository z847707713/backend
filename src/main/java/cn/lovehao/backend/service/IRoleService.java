package cn.lovehao.backend.service;

import cn.lovehao.backend.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> diyPage(Page<Role> page,Role role);


    void delete(String id);
}
