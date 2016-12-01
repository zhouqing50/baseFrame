package base.collection;

import java.util.HashSet;

/**
 * Description:
 * 直接把对象当做Key，存储到HashMap中，如果Key的hashcode重复，则插入失败
 * User: zhouq
 * Date: 2016/11/15
 */
public class HashSetTest {
    public static void main(String[] args) {
        HashSet<Name> set = new HashSet<>();
        set.add(new Name("abc", "123"));
        set.add(new Name("abc", "456"));
        System.out.println(set);
    }

    static class Name {
        private String first;
        private String last;

        public Name(String first, String last) {
            this.first = first;
            this.last = last;
        }

        // 根据 first 判断两个 HashSetTest 是否相等
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o.getClass() == Name.class) {
                Name n = (Name) o;
                return n.first.equals(first);
            }
            return false;
        }

        // 根据 first 计算 Name 对象的 hashCode() 返回值
        public int hashCode() {
            return first.hashCode();
        }

        public String toString() {
            return "Name[first=" + first + ", last=" + last + "]";
        }
    }
}





