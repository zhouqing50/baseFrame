import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * User: zhouq
 * Date: 2016/11/14
 */


public class Tsss {
    public static void main(String [] args) {
//
//        System.out.println("E.1000".matches("E\\.(.+)\\.[0-9]{1,12}"));
//
//
//        System.out.println(1==1 || 1==3);
//
//
//        ModuleKeyEnum moduleKeyEnum = ModuleKeyEnum.getByModuleKey("MK_BANNERS");
//        System.out.println(moduleKeyEnum);
//
//        System.out.println(ModuleKeyEnum.COMPONENTS.getModuleKey());

        List<String> aa = null;
        List<Integer> bb = Lists.newArrayList();
        bb = aa.stream().map(Integer::parseInt).collect(Collectors.toList());

//        List<Integer> aa = Lists.newArrayList(1,2,3,4,5);
//        List<Integer> bb = Lists.newArrayList(1);
//
//        System.out.println(aa.containsAll(bb));
//
//        String c1 = "http://zz.com/cca/dda";
//        String c2 = "http://zz.com";
//        System.out.println(c1.contains(c2));

    }
}
