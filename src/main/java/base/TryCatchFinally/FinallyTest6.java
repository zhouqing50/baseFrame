package base.TryCatchFinally;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: zhouq
 * Date: 2016/8/12
 */


public class FinallyTest6 {
    public static void main(String[] args) {
        System.out.println(getMap().get("KEY"));
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("KEY", "INIT");

        try {
            map.put("KEY", "TRY");
            return map;
        } catch (Exception e) {
            map.put("KEY", "CATCH");
        } finally {
            map.put("KEY", "FINALLY");
            map = null;
        }

        return map;
    }
}