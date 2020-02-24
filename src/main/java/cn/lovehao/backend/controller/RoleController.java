package cn.lovehao.backend.controller;


import cn.lovehao.backend.dto.ResponseEntity;
import cn.lovehao.backend.entity.Role;
import cn.lovehao.backend.exception.ServiceException;
import cn.lovehao.backend.service.IRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class RoleController {

    @Autowired
    IRoleService roleService;

    @RequestMapping("/roles")
    public String view(){
        return "/admin/roles";
    }

    @RequestMapping("/roles/list")
    @ResponseBody
    public ResponseEntity<IPage<Role>> page(Page<Role> page, Role role){
        IPage<Role> roles = roleService.diyPage(page,role);
        return ResponseEntity.success(roles);
    }

    @ResponseBody
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public ResponseEntity<Role> get(String id){
        Role role = roleService.getById(id);
        if(role == null){
            throw new ServiceException("角色不存在");
        }
        return ResponseEntity.success(role);
    }


    @ResponseBody
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public ResponseEntity<String> add(Role role){
        roleService.save(role);
        return ResponseEntity.success();
    }

    @ResponseBody
    @RequestMapping(value = "/role",method = RequestMethod.PUT)
    public ResponseEntity<String> update(Role role){
        roleService.updateById(role);
        return ResponseEntity.success();
    }


    @ResponseBody
    @RequestMapping(value = "/role",method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(String id){
        roleService.delete(id);
        return ResponseEntity.success();
    }




}
