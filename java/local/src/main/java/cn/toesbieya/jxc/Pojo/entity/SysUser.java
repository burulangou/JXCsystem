package cn.toesbieya.jxc.Pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    private Integer id;
    private String loginName;
    private String nickName;
    private String pwd;
    private Integer role;
    private String avatar;              //头像
    private Long ctime;                 //创建时间
    private boolean admin = false;      //是否具有管理员权限
    private boolean enable = false;
    private Integer dept;               //部门
}
