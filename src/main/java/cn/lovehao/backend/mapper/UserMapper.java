package cn.lovehao.backend.mapper;

import cn.lovehao.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectPage(Page<?> page,@Param("user") User user);

}
