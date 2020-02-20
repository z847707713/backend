package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.entity.Permission;
import cn.lovehao.backend.mapper.PermissionMapper;
import cn.lovehao.backend.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
