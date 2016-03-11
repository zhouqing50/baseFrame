/**   
* @Title: Test1.java 
* @Package juc.base.synchronizedDemo 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2016年3月7日 下午2:23:20 
* @version V1.0   
*/
package juc.base.synchronizedDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
* @ClassName: Test1 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2016年3月7日 下午2:23:20 
*/
public class Test1 {

    /** 
    *
    * @Title: main 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param args    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void main(String[] args) {
       
        ExecutorService executorA =  Executors.newFixedThreadPool(5);
        final Atest atest = new Atest("Akais ");
        
        for (int i = 0; i <1; i++) {
            executorA.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                   // Atest atest = new Atest("Akais ");
                    atest.testA();
                   // atest.testB();
                    return 1;
                }
            });
        }
        
       
        for (int i = 0; i <1; i++) {
            executorA.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                   // Atest btest = new Atest("Bkais ");
                   // btest.testB();
                    atest.testB();
                  //  btest.testA();
                    return 1;
                }
            });
        }

    }

    
     class ThreadA extends Thread{
            public void run() {
                
            }
            
    }
    
}
