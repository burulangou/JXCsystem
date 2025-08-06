package cn.toesbieya.jxc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig implements AsyncConfigurer {
    /**
     * 增加对异常的处理,
     * (当项目中的异步方法（使用 @Async 注解的方法）执行失败时)
     * */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return(ex,method,params) -> {
            log.error("异步线程执行失败。方法：[{}],异常信息[{}] : ", method, ex.getMessage(), ex);
        };
    }

    /**
     *定时任务线程池
     * */
    @Bean
    public Executor scheduledExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(5);
        //是否回收空闲线程
        executor.setAllowCoreThreadTimeOut(true);
        //设置最大线程数
        executor.setMaxPoolSize(5);
        //配置等待队列的大小
        executor.setQueueCapacity(10);
        //配置线程池前缀
        executor.setThreadNamePrefix("scheduled-");
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //初始化
        log.info("定时任务线程池准备完毕");
        executor.initialize();
        return executor;
    }

    /**
     * 数据库插入线程池
     * */
    @Bean
    public Executor DBExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(5);
        //是否回收空闲线程
        executor.setAllowCoreThreadTimeOut(true);
        //设置最大线程数
        executor.setMaxPoolSize(5);
        //配置等待队列的大小
        executor.setQueueCapacity(10);
        //配置线程池前缀
        executor.setThreadNamePrefix("DB-Action-");
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //初始化
        log.info("数据库插入线程池准备完毕");
        executor.initialize();
        return executor;
    }
}
