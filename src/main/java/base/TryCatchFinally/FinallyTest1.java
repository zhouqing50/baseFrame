package base.TryCatchFinally;

/**
 * finally块的语句在try或catch中的return语句执行之后返回之前执行且finally里的修改语句可能影响也可能不影响try或catch中 return已经确定的返回值，
 * 若finally里也有return语句则覆盖try或catch中的return语句直接返回。
 *
 *http://www.cnblogs.com/lanxuezaipiao/p/3440471.html
 *
 *https://www.ibm.com/developerworks/cn/java/j-lo-finally/
 */
public class FinallyTest1 {
    public static void main(String[] args) {

        System.out.println(test11());
    }

    public static String test11() {
        try {
            System.out.println("try block");

            return test12();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test12() {
        System.out.println("return statement");

        return "after return";
    }

}