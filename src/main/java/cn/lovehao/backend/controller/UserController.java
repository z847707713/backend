package cn.lovehao.backend.controller;


import cn.lovehao.backend.dto.ResponseEntity;
import cn.lovehao.backend.entity.User;
import cn.lovehao.backend.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<IPage<User>> users(Page page,User user) {
        IPage<User> users = userService.diyPage(page,user);
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
     * 添加用户
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/user",method = RequestMethod.POST)
    public ResponseEntity<String> add(User user){
        userService.add(user);
        return ResponseEntity.success();
    }

    /**
     * 修改用户
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/user",method = RequestMethod.PUT)
    public ResponseEntity<String> update(User user){
        userService.update(user);
        return ResponseEntity.success();
    }

    @ResponseBody
    @RequestMapping(value = "/admin/user",method = RequestMethod.GET)
    public ResponseEntity<User> get(String id){
        User user = userService.getById(id);
        if(user == null){
            return ResponseEntity.error("id 为" + id + "的用户不存在");
        }
        return ResponseEntity.success(user);
    }

    @ResponseBody
    @RequestMapping(value = "/admin/user",method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(String id){
        User user = userService.getById(id);
        if(user == null){
            return ResponseEntity.error("id 为" + id + "的用户不存在");
        }
        user.setIsDelete(true);
        userService.updateById(user);
        return ResponseEntity.success();
    }

    /**
     * 批量禁止或开启
     * @param ids
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/user/batchForbid",method = RequestMethod.PUT)
    public ResponseEntity<String> batchUpdateIsForbid(@RequestParam("ids[]") List<String> ids,@RequestParam("type")Boolean type){
        userService.batchForbid(ids,type);
        return ResponseEntity.success();
    }


    /**
     * 批量禁止或开启
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/users",method = RequestMethod.DELETE)
    public ResponseEntity<String> batchDelete(@RequestParam("ids[]") List<String> ids){
        userService.batchDelete(ids);
        return ResponseEntity.success();
    }




}
