package cloud.codechun.tutu.thread;

import java.util.concurrent.*;

public class FolderAnalyzeThreadPool {

    /**
     * 子文件夹 AI 分析线程池
     * 固定线程数 + 有界队列 + 回退策略
     */
    public static final ExecutorService EXECUTOR =
            new ThreadPoolExecutor(
                    4,                      // 核心线程数
                    4,                      // 最大线程数
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(50), // 有界队列
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
}
