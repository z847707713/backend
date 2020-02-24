package cn.lovehao.backend.mapper;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

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

    IPage<Permission> selectPage(Page<Permission> page,@Param("p") Permission permission);

}
