package cn.lovehao.backend.controller;


import cn.lovehao.backend.dto.ResponseEntity;
import cn.lovehao.backend.entity.User;
import cn.lovehao.backend.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zh
 * @since 2020-02-18
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/admin/users")
    public String index() {
        return "/admin/users";
    }

    /**
     * 分页:  size , current
     *
     * @param page
     * @return
     */
    @RequestMapping("/admin/users/list")
    @ResponseBody
    public ResponseEntity<IPage<User>> users(Page page) {
        IPage<User> users = userService.diyPage(page);
        return ResponseEntity.success(users);
    }

    /**
     * 禁用
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/users/forbid")
    public ResponseEntity<String> forbid(String id){
        userService.forbid(id);
        return ResponseEntity.success();
    }


    /**
     * 禁用
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/user",method = RequestMethod.POST)
    public ResponseEntity<String> add(User user){
        userService.add(user);
        return ResponseEntity.success();
    }



}
