package sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:按 数字，特殊字符，英文字母或者中文首字母排序
 * 规则：数字，特殊字符，英文字母
 * User: zhouq
 * Date: 2016/5/18
 */
public class SortUtils {
    //字母Z使用了两个标签，这里有２７个值
    //i, u, v都不做声母, 跟随前面的字母
    private static char[] chartable =
            {
                    '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
                    '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
                    '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
            };
    private static char[] alphatableb =
            {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            };
    private static char[] alphatables =
            {
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
            };
    private static int[] table = new int[27];  //初始化

    static {
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(chartable[i]);
        }
    }

    public SortUtils() {

    }

    /**
     * 按 数字，特殊字符，英文字母或者中文首字母排序
     * @param str 排序参数
     * @return
     */
    public static String sortFirst(String str) {
        return string2AlphaFirst(str, "b");
    }

    public List<String> sort(List<String> list) {
        //为了排序都返回大写字母
        list.sort((String h1, String h2) -> string2AlphaFirst(h1, "b").compareTo(string2AlphaFirst(h2, "b")));
        return list;
    }

    //主函数,输入字符,得到他的声母,
    //英文字母返回对应的大小写字母
    //其他非简体汉字返回 '0'  按参数
    public static char char2Alpha(char ch, String type) {
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 'a' + 'A');//为了按字母排序先返回大写字母
        }
        // return ch;
        if (ch >= 'A' && ch <= 'Z') {
            return ch;
        }
        int gb = gbValue(ch);
        if (gb < table[0]) {
            return '0';
        }
        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))
                break;
        }
        if (i >= 26) {
            return '0';
        } else {
            if ("b".equals(type)) {//大写
                return alphatableb[i];
            } else {//小写
                return alphatables[i];
            }
        }
    }

    //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public String string2Alpha(String SourceStr, String type) {
        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += char2Alpha(SourceStr.charAt(i), type);
            }
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }

    //根据一个包含汉字的字符串返回第一个汉字拼音首字母的字符串
    public static String string2AlphaFirst(String SourceStr, String type) {
        String Result = "";
        try {
            Result += char2Alpha(SourceStr.charAt(0), type);
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }

    private static boolean match(int i, int gb) {
        if (gb < table[i]) {
            return false;
        }
        int j = i + 1;

        //字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i]))
            ++j;
        if (j == 26)
            return gb <= table[j];
        else
            return gb < table[j];
    }

    //取出汉字的编码
    private static int gbValue(char ch) {
        String str = "";
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2) {
                return 0;
            }
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        SortUtils obj1 = new SortUtils();
        System.out.println("======================");
        List<String> list = new ArrayList<>();
        list.add("adisen");
        list.add("宝爷");
        list.add("bulsi");
        list.add("夏天");
        list.add("Kobe");
        list.add("布丁");
        list.add("杜甫");
        list.add("ya");
        list.add("元方");
        list.add("111");
        list.add("233");
        list.add("周青");
        list.add("z");
        list.add("@");
        list.add("#");
        List<String> ss = obj1.sort(list);
        ss.forEach(System.out::println);
/*
        System.out.println("=====2222222222222222222222=======");
        List<ServiceNumberVO> serviceNumberVOList = new ArrayList<>();
        ServiceNumberVO s5 = new ServiceNumberVO();
        s5.setAppName("企业自建应用");
        s5.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s5);
        ServiceNumberVO s11 = new ServiceNumberVO();
        s11.setAppName("企业自建应用");
        s11.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s11);
        ServiceNumberVO s1 = new ServiceNumberVO();
        s1.setAppName("员工运动");
        s1.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s1);
        ServiceNumberVO s2 = new ServiceNumberVO();
        s2.setAppName("快递帮手");
        s2.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s2);
        ServiceNumberVO s3 = new ServiceNumberVO();
        s3.setAppName("1夏天");
        s3.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s3);
        ServiceNumberVO s4 = new ServiceNumberVO();
        s4.setAppName("233");
        s4.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s4);
        ServiceNumberVO s6 = new ServiceNumberVO();
        s6.setAppName("#");
        s6.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s6);
        ServiceNumberVO s7 = new ServiceNumberVO();
        s7.setAppName("bass");
        s7.setAppId("FSAID_1313f40");
        serviceNumberVOList.add(s7);
        serviceNumberVOList.sort((h1,h2) -> sortFirst(h1.getAppName()).compareTo( sortFirst(h2.getAppName())));
        serviceNumberVOList.forEach(System.out::println);*/
    }
}

