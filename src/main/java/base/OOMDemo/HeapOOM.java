package base.OOMDemo;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by zhouq
 * on 2016/5/4.
 * <p/>
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
//        List<OOMObject> list = new ArrayList<>();
//
//        while (true) {
//            list.add(new OOMObject());
//        }
        String ss = "192.168.1.1;192.1.1.1";
        String [] aa = ss.split(";");
        for (int i = 0; i < aa.length; i++) {
            System.out.println(aa[i]);
        }
        String [] s = { "a", "b", "c", "d", "e" };
        System.out.println(String.join(";",s));


        String[] a1 = {"保","何","于","刘","啊阿","D","周啊","A"};
        getSortOfChinese(a1);
        for (int i = 0; i < a1.length; i++) {
            System.out.println(a1[i]);
        }
    }

    public static String[] getSortOfChinese(String[] a) {
        // Collator 类是用来执行区分语言环境这里使用CHINA
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);

        // JDKz自带对数组进行排序。
        Arrays.sort(a, cmp);
        return a;
    }
}
