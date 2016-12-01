package base.collection;

import java.util.HashMap;

/**
 * Description:
 * 直接把对象当做Key，存储到HashMap中，如果Key的hashcode重复，则插入失败
 * User: zhouq
 * Date: 2016/11/15
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        Name name1 = new Name("abc", "123");
        map.put(name1, name1);
        Name name2 = new Name("abc", "456");
        map.put(name2, name2);
        map.entrySet().forEach(System.out::println);
    }

    static class Name {
        private String first;
        private String last;

        public Name(String first, String last) {
            this.first = first;
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        // 根据 first 判断两个 Name 是否相等
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
            return 2;
           // return first.hashCode();
        }

        public String toString() {
            return "Name[first=" + first + ", last=" + last + "]";
        }
    }
}





