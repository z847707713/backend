package cn.lovehao.backend.controller;


import cn.lovehao.backend.dto.ResponseEntity;
import cn.lovehao.backend.entity.Permission;
import cn.lovehao.backend.exception.ServiceException;
import cn.lovehao.backend.service.IPermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
@Controller
@RequestMapping("/admin")
public class PermissionController {

    @Autowired
    IPermissionService permissionService;


    @RequestMapping("/permissions")
    public String view(){
        return "/admin/permissions";
    }

    @ResponseBody
    @RequestMapping("/permissions/list")
    public ResponseEntity<IPage<Permission>> page(Page<Permission> page, Permission permission){
        IPage<Permission> iPage =  permissionService.diyPage(page,permission);
        return ResponseEntity.success(iPage);
    }

    @ResponseBody
    @RequestMapping("/permission")
    public ResponseEntity<Permission> get(String id){
       Permission permission =  permissionService.getById(id);
       if(permission == null){
           throw new ServiceException("该权限不存在");
       }
       return ResponseEntity.success(permission);
    }

    @ResponseBody
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public ResponseEntity<String> add(Permission permission){
        permissionService.save(permission);
        return ResponseEntity.success();
    }

    @ResponseBody
    @RequestMapping(value = "/permission",method = RequestMethod.PUT)
    public ResponseEntity<String> update(Permission permission){
        permissionService.updateById(permission);
        return ResponseEntity.success();
    }

    @ResponseBody
    @RequestMapping(value = "/permission",method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(String id){
        permissionService.delete(id);
        return ResponseEntity.success();
    }



}
