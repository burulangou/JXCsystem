package cn.toesbieya.jxc.Pojo.vo;


import cn.toesbieya.jxc.Pojo.entity.BizPurchaseInbound;
import cn.toesbieya.jxc.Pojo.entity.BizPurchaseInboundSub;
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
public class PurchaseInboundVo extends BizPurchaseInbound {
    private List<BizPurchaseInboundSub> data;
    private List<RecAttachment> imageList;
    private List<RecAttachment> uploadImageList;
    private List<String> deleteImageList;

    public PurchaseInboundVo(BizPurchaseInbound parent) {
        BeanUtils.copyProperties(parent, this);
    }
}
