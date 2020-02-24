package cn.lovehao.backend.mapper;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectAllUrls();

}
