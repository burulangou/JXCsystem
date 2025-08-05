package cn.toesbieya.jxc.Pojo.entity;

import lombok.Data;

@Data
public class BizDoc {
    private String id;
    private Integer cid;
    private String cname;
    private Long ctime;
    private Integer vid;
    private String vname;
    private Long vtime;
    private Integer status;
    private String remark;
}
