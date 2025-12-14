package cloud.codechun.tutu.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration   // ★ 必须
@Component       // ★ 让 Spring 管理这个类
public class FolderAnalyzeThreadPool {

    public static ExecutorService EXECUTOR;

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executor() {
        return new ThreadPoolExecutor(
                4,
                4,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(50),
                new ThreadPoolExecutor.CallerRunsPolicy() // ★ 强烈建议
        );
    }

    @Autowired
    public void setExecutor(ExecutorService executor) {
        FolderAnalyzeThreadPool.EXECUTOR = executor;
    }
}

