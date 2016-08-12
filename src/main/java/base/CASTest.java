package base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @describe:
 * @author: zhouq
 * @date: 2016/5/5 14:15
 */
public class CASTest {

    private volatile int max = 0;

    public synchronized void setMax(int value) {
        if (value > max) {
            max = value;
        }
    }

    public int getMax() {
        return max;
    }


    private AtomicInteger max2 = new AtomicInteger();

    private void setMax2ByCAS(int value) {
        for ( ; ; ) {
            int current = max2.get();
            if (value > current) {
                if (max2.compareAndSet(current, value)) {
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    public int getMax2() {
        return max2.get();
    }

}
