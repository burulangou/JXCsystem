package cn.toesbieya.jxc.Pojo.vo;

import cn.toesbieya.jxc.Pojo.entity.SysUser;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class UserVo extends SysUser {
    private String Token;
    private String roleName;
    private String deptName;
    private Boolean online = false;
    private Set<String> departmentIds;
    private Set<String> resourceIds;

    public UserVo(SysUser parent) {
        BeanUtils.copyProperties(parent, this);
    }
}
