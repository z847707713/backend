package cn.lovehao.backend.service.impl;

import cn.lovehao.backend.entity.Role;
import cn.lovehao.backend.mapper.RoleMapper;
import cn.lovehao.backend.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
