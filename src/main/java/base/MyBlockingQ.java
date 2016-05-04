package base;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 * Created by zhouq
 * on 2016/4/29.
 */
public class MyBlockingQ {

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private Queue<Object> linkedList = new LinkedList<>();
    private int maxLength = 10;

    public Object take() {
        lock.lock();
        try {
            if (linkedList.size() == 0) {
                System.out.println("队列空，等待数据");
                notEmpty.await();
            }
            if (linkedList.size() == maxLength) {
                notFull.signalAll();
            }
            System.out.println("从队列取走一个元素，队列剩余"+linkedList.size()+"个元素");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return linkedList.poll();
    }

    public void poll(Object o) {
        lock.lock();
        try {
            if (linkedList.size() == 0){
                notEmpty.signalAll();
            }
            if (linkedList.size() == maxLength){
                System.out.println("队列满，等待有空余空间");
                notFull.await();
            }
            linkedList.add(o);
            System.out.println("向队列取中插入一个元素，队列剩余空间："+(maxLength-linkedList.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String []args){
        MyBlockingQ blockingQ = new MyBlockingQ();
       // blockingQ.take();
        blockingQ.poll(1);
        blockingQ.take();

    }
}
