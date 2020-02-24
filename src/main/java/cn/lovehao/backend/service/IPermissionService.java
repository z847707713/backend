package cn.lovehao.backend.service;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.entity.Permission;
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

}
