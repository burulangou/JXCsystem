package cn.toesbieya.jxc.service.sys;

import cn.toesbieya.jxc.Pojo.entity.SysResource;
import cn.toesbieya.jxc.enumeration.ResourceEnum;
import cn.toesbieya.jxc.mapper.SysResourceMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SysResourceService {
    @Resource
    private SysResourceMapper sysmapper;

    /**
     * 获取用户访问url权限
     * 以下两段函数皆为权限处理
     * 这里的Predicate<SysResource> predicate是作为过滤方法来进行true or false判断
     * */
    public List<SysResource> GetAuthority() {
        return getEnableResourceTree(null, i -> i.getType().equals(ResourceEnum.API.getCode()));
    }
    private List<SysResource> getEnableResourceTree(String ids, Predicate<SysResource> predicate) {
        return sysmapper
                .GetAuthority(ids)
                .stream()
                .filter( r -> {
                    //当且仅当sysresource里为enable和predicate测试为真
                    if (!r.isEnable() || predicate != null && !predicate.test(r)) {
                        return false;
                    }
                    String path = r.getPath();
                    int index = path.lastIndexOf("//");
                    if (index != -1 && !path.startsWith("http")) {
                        //去掉'//'前拼接的父节点的path
                        r.setPath(path.substring(index + 1));
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }


}
