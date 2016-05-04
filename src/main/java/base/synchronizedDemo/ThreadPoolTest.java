package base.synchronizedDemo;

import base.CommonThreadPoolUtils;

/**
 * Created by zhouq
 * on 2016/4/29.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        CommonThreadPoolUtils.getExecutor().execute(() -> {
            System.out.println(222);
        });
    }
}
