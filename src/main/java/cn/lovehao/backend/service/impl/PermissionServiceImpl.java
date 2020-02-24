package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.entity.Permission;
import cn.lovehao.backend.entity.RolePermission;
import cn.lovehao.backend.exception.ServiceException;
import cn.lovehao.backend.mapper.PermissionMapper;
import cn.lovehao.backend.mapper.RolePermissionMapper;
import cn.lovehao.backend.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    /**
     *  获取所有的url ，并处理成 PermissionDto 的数据结构
     * @return
     */
    @Override
    public List<PermissionDto> getAllUrls() {
        List<Permission> urls = permissionMapper.selectAllUrls();
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for(Permission p : urls){
            if(p.getParent() == null){
                addNodeAndFindChild(urls, permissionDtos, p);
            }
        }
        return permissionDtos;
    }

    @Override
    public IPage<Permission> diyPage(Page<Permission> page, Permission permission) {
        return permissionMapper.selectPage(page,permission);
    }

    @Transactional
    @Override
    public void delete(String id) {
        //验证id 是否正确
        Permission p = getById(id);
        if(p == null){
            throw new ServiceException("要删除得权限不存在");
        }
        // 验证该权限是否在 使用中
        int count = rolePermissionMapper.selectCount(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getPermissionId,id));
        if(count > 0){
            throw new ServiceException("该权限正在使用中，不可删除");
        }
        //开始删除
        removeById(id);
    }

    /**
     *  代码相同的地方抽取出来，完全是强迫症
     * @param urls
     * @param permissionDtos
     * @param p
     */
    private void addNodeAndFindChild(List<Permission> urls, List<PermissionDto> permissionDtos, Permission p) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setUrl(p.getUrl());
        permissionDto.setId(p.getId());
        permissionDto.setName(p.getName());
        findChilds(urls, permissionDto);
        permissionDtos.add(permissionDto);
    }

    /**
     *  递归获取 子节点
     * @param urls
     * @param permissionDto
     */
    private void findChilds(List<Permission> urls, PermissionDto permissionDto) {
        List<PermissionDto> childPermissionDto = new ArrayList<>();
        for(Permission c : urls){
            if(permissionDto.getId().equals(c.getParent())){
                addNodeAndFindChild(urls, childPermissionDto, c);
            }
        }
        if(childPermissionDto.size() > 0){
            permissionDto.setHasChild(true);
        } else {
            permissionDto.setHasChild(false);
        }
        permissionDto.setChilds(childPermissionDto);
    }


}
