package cn.toesbieya.jxc.module;

import cn.toesbieya.jxc.Pojo.entity.SysResource;
import cn.toesbieya.jxc.Pojo.vo.UserVo;
import cn.toesbieya.jxc.service.sys.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class PermissionModule implements CommandLineRunner {
    private final static ConcurrentHashMap<String, SysResource> urlMap = new ConcurrentHashMap<>(256);
    private final static ConcurrentHashMap<String, SysResource> adminUrlMap = new ConcurrentHashMap<>(16);

    @Resource
    private SysResourceService service;

    public static boolean authority(UserVo userVo,String url){
        boolean isAdmin = userVo.isAdmin();
        SysResource sysresource = adminUrlMap.get(url);
        //访问admin权限的资源时，需要用户是admin并且该权限已启用
        if (sysresource != null) {
            return sysresource.isEnable() && isAdmin;
        }

        if(isAdmin){
            return true;
        }

        //访问非admin权限道德资源时，只需要确认在urlMap里而没有出现在amdinurlMap里
        sysresource = urlMap.get(url);
        //权限表中无记录的资源放行url资源可以访问
        if (sysresource == null) return true;

        //未启用权限时拦截url资源
        if (!sysresource.isEnable()) return false;

        //根据用户权限判断
        Set<Integer> ids = userVo.getResourceIds();
        return ids != null && ids.contains(sysresource.getId());
    }


    @Override
    public void run(String... args) throws Exception {
        Instant start  = Instant.now();

        List<SysResource> resources = service.GetAuthority();

        for(SysResource sysResource : resources){
            String path = sysResource.getPath();
            if(sysResource.isAdmin()){
                adminUrlMap.put(path,sysResource);
            }
            else urlMap.put(path,sysResource);
        }
        Instant end = Instant.now();
        log.info("权限url资源加载完成，耗时：{}毫秒", ChronoUnit.MILLIS.between(start, end));
    }
}
