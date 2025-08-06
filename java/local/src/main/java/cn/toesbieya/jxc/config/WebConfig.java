package cn.toesbieya.jxc.config;


import cn.toesbieya.jxc.interceptor.SecurityInterceptor;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {
    // 移除默认的Jackson转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }


        //配置fastjson的http消息转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(FastJsonFactoryConfig.defaultConfig());

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastJsonHttpMessageConverter);

    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("进销存系统项目接口文档")
                .version("2.0")
                .description("进销存系统项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.toesbieya.jxc.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {
                "/test/**",
                "/account/login",
                "/account/logout",
                "/account/register",
                "/account/checkLoginName",
                "/account/checkNickName",
                "/error",
                "/doc.html",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/api-docs",
                "/v2/api-docs-ext",
                "/swagger-ui/**",
                "/doc.html/**",
                "/favicon.ico"
        };
        addInterceptor(registry, new SecurityInterceptor(), exclude);
    }

    private void addInterceptor(InterceptorRegistry registry, HandlerInterceptor interceptor, String[] exclude) {
        InterceptorRegistration registration = registry.addInterceptor(interceptor).addPathPatterns("/**");
        registration.excludePathPatterns(exclude);
    }
}
