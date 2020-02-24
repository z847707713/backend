package cn.lovehao.backend.dto;

import cn.lovehao.backend.entity.Permission;
import lombok.Data;

import java.util.List;

@Data
public class PermissionDto extends Permission {

    //是否有子节点
    private boolean hasChild;

    private List<PermissionDto> childs;


}
