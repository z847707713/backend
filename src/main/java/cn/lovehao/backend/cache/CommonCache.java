package cn.lovehao.backend.cache;

import cn.lovehao.backend.dto.PermissionDto;
import cn.lovehao.backend.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommonCache {

    private List<PermissionDto> urls;

    @Autowired
    IPermissionService permissionService;

    @PostConstruct
    public void init(){
        urls = permissionService.getAllUrls();
    }

    public List<PermissionDto> getUrls(){
        return urls;
    }

    public List<PermissionDto>  reloadUrls(){
        urls = permissionService.getAllUrls();
        return urls;
    }


}
