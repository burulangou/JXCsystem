package cn.toesbieya.jxc.Pojo.vo;

import lombok.Data;

@Data
public class PasswordUpdateParam {
    private Integer id;
    private String newPwd;
    private String oldPwd;
}
