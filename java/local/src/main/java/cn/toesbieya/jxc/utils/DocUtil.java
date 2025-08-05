package cn.toesbieya.jxc.utils;

import cn.toesbieya.jxc.Pojo.entity.BizDoc;
import cn.toesbieya.jxc.Pojo.vo.search.DocSearch;
import cn.toesbieya.jxc.constant.DocConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
public class DocUtil {
    private static String GET_DOC_ID_SCRIPT;
    static {
        try {
            GET_DOC_ID_SCRIPT = new String(ByteStreams.toByteArray(new ClassPathResource("/script/get_doc_id.lua").getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取单号，形如 {type}0001
    public static String getDocId(String type) {
        if (!Arrays.asList(DocConstant.DOC_TYPE).contains(type)) {
            log.error("单据类型有误，【{}】不存在", type);
            return null;
        }

        RedisScript<Long> redisScript = new DefaultRedisScript<>(GET_DOC_ID_SCRIPT, Long.class);

        Long result = RedisUtil.getStringRedisTemplate().execute(
                redisScript,
                Arrays.asList(DocConstant.UPDATE_DOC_LOCK_KEY, DocConstant.DOC_TYPE_REDIS_KEY),
                String.valueOf(DateUtil.getTimestampNow()),
                type
        );

        if (result == null || result <= 1) return null;

        return String.format("%s%s%04d", type, DateUtil.dateFormat(DateTimeFormatter.BASIC_ISO_DATE), result - 1);
    }

    public static <T extends BizDoc> LambdaQueryWrapper<T> baseCondition(Class<T> c, DocSearch vo) {
        String id = vo.getId();
        String idFuzzy = vo.getIdFuzzy();
        Integer cid = vo.getCid();
        String cname = vo.getCname();
        Integer vid = vo.getVid();
        String vname = vo.getVname();
        String status = vo.getStatus();
        Long ctimeStart = vo.getCtimeStart();
        Long ctimeEnd = vo.getCtimeEnd();
        Long vtimeStart = vo.getVtimeStart();
        Long vtimeEnd = vo.getVtimeEnd();

        return Wrappers.lambdaQuery(c)
                //单据ID相关查询
                .eq(!StringUtils.isEmpty(id), BizDoc::getId, id)
                .like(!StringUtils.isEmpty(idFuzzy), BizDoc::getId, idFuzzy)
                //创建人信息查询
                .eq(cid != null, BizDoc::getCid, cid)
                .like(!StringUtils.isEmpty(cname), BizDoc::getCname, cname)
                //审核人信息查询
                .eq(vid != null, BizDoc::getVid, vid)
                .like(!StringUtils.isEmpty(vname), BizDoc::getVname, vname)
                //单据状态查询
                .inSql(!StringUtils.isEmpty(status), BizDoc::getStatus, status)
                //时间范围查询
                .ge(ctimeStart != null, BizDoc::getCtime, ctimeStart)
                .le(ctimeEnd != null, BizDoc::getCtime, ctimeEnd)
                .ge(vtimeStart != null, BizDoc::getCtime, vtimeStart)
                .le(vtimeEnd != null, BizDoc::getCtime, vtimeEnd)
                //排序
                .orderByDesc(BizDoc::getCtime);
    }
}
