package cn.toesbieya.jxc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FastJsonFactoryConfig {
    public static FastJsonConfig defaultConfig(){
        //创建并配置FastJsonConfig对象
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,//输出值为null的成员
                SerializerFeature.WriteDateUseDateFormat,//日期格式化
                SerializerFeature.WriteNullListAsEmpty,//null列表输出为空数
                SerializerFeature.DisableCircularReferenceDetect//禁用循环引用检测
        );

        return config;
    }
}
