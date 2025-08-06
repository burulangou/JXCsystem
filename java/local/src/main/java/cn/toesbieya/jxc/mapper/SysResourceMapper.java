package cn.toesbieya.jxc.mapper;

import cn.toesbieya.jxc.Pojo.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {
        /**
         * 获取用户访问url权限
         * */
        List<SysResource> GetAuthority(@Param("ids") String ids);
}
