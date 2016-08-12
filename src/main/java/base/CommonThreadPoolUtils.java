package base;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 公用的线程池
 * @author chenzengyong
 * @date on 2015/11/25.
 */
public class CommonThreadPoolUtils {

    private static final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    //默认线程数为主机CPU的可用核心数
    private static final Executor executor2 = Executors.newWorkStealingPool();

    public static Executor getExecutor(){
        return executor;
    }
}
