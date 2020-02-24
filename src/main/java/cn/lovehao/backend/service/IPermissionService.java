package cn.lovehao.backend.service;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.entity.Permission;
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
public interface IPermissionService extends IService<Permission> {

    List<PermissionDto> getAllUrls();

    IPage<Permission> diyPage(Page<Permission> page, Permission permission);

    void delete(String id);

}
