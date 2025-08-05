package cn.toesbieya.jxc.Pojo.entity;

import lombok.Data;

@Data
public class SysDepartment {
    private Integer id;
    private Integer pid;
    private String name;
    private boolean enable = false;
}
