package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.entity.Role;
import cn.lovehao.backend.entity.RolePermission;
import cn.lovehao.backend.entity.UserRole;
import cn.lovehao.backend.exception.ServiceException;
import cn.lovehao.backend.mapper.RoleMapper;
import cn.lovehao.backend.mapper.RolePermissionMapper;
import cn.lovehao.backend.mapper.UserRoleMapper;
import cn.lovehao.backend.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public IPage<Role> diyPage(Page<Role> page, Role role) {
        return roleMapper.selectAll(page,role);
    }

    @Transactional
    public void delete(String id){
        //查询是否有用户使用该角色
        int count = userRoleMapper.selectCount(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId,id));
        if(count > 0){
            throw new ServiceException("该角色正在使用中，不可删除");
        }
        // 该角色可以删除
        // 1. 删除关联表数据
        rolePermissionMapper.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, id));
        // 2. 删除角色表数据
        roleMapper.deleteById(id);
    }

}
