package cn.toesbieya.jxc.Pojo.vo;


import cn.toesbieya.jxc.Pojo.entity.BizPurchaseOrder;
import cn.toesbieya.jxc.Pojo.entity.BizPurchaseOrderSub;
import cn.toesbieya.jxc.Pojo.entity.RecAttachment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PurchaseOrderVo extends BizPurchaseOrder {
    private List<BizPurchaseOrderSub> data;
    private List<RecAttachment> imageList;
    private List<RecAttachment> uploadImageList;
    private List<String> deleteImageList;

    public PurchaseOrderVo(BizPurchaseOrder parent) {
        BeanUtils.copyProperties(parent, this);
    }
}
