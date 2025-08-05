package cn.toesbieya.jxc.Pojo.entity;

import lombok.Data;

@Data
public class SysCategory {
    private Integer id;
    private Integer pid;
    private String name;
    private boolean leaf = false;
    private Long ctime;
}
