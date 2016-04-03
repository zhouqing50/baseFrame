/**   
* @Title: Atest.java 
* @Package juc.base.synchronizedDemo 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2016年3月7日 下午2:18:28 
* @version V1.0   
*/
package juc.base.synchronizedDemo;

/** 
* @ClassName: Atest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2016年3月7日 下午2:18:28 
*/
public class Atest {

    private String str;
    
    public Atest (String str) {
        this.str = str;
    }
    
    public  void testA() {
        synchronized (this) {
            System.out.println("start  ..1.."+str);
            //怎么检测一个线程是否持有对象监视器
           System.out.println(Thread.holdsLock(this)); 
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized  void testB() {
 
        System.out.println("start  ..2.."+str);
    }
}
