/**   
* @Title: ExchangerTest.java 
* @Package juc.base 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2016年2月16日 上午10:07:11 
* @version V1.0   
*/
package juc.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/** 
* @ClassName: ExchangerTest 
* @Description: TODO
* 用于线程之间交换数据
* @Company:彩讯科技
* @author 青 
* @version 1.0 2016年2月16日 上午10:07:11 
*/
public class ExchangerTest {

    public static void main(String[] args) {
        final Exchanger<List<Integer>> exchange = new Exchanger<List<Integer>>();
        new Thread(){
            public void run() {
                List<Integer> l1 = new ArrayList<Integer>();
                l1.add(1);
                l1.add(2);
                try {
                    l1 = exchange.exchange(l1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(l1);
            }
        }.start();
        
        new Thread(){
            public void run() {
                List<Integer> l2 = new ArrayList<Integer>();
                l2.add(3);
                l2.add(4);
                try {
                    l2 = exchange.exchange(l2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(l2);
            }
        }.start();
    }
}
