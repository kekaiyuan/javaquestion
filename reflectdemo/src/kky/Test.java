package kky;

import java.lang.reflect.Method;

/**
 * @author 柯凯元
 * @create 2021/6/14 16:43
 */
public class Test {

    public Test(){}

    public void Test() {
        System.out.println("I'm Test1");
    }

    public void Test(String str) {
        System.out.println("I'm Test2");
    }

    public void Test(String str, boolean b) {
        System.out.println("I'm Test3");
    }

    public static void main(String[] args) throws Exception {
        //创建 Class 对象
        Class<Test> testClass = Test.class;
        //创建对象
        Object object = testClass.newInstance();

        //查找方法并调用，注意参数列表

        Method method = testClass.getDeclaredMethod("Test");
        method.invoke(object);

        method = testClass.getDeclaredMethod("Test", String.class);
        method.invoke(object, "helloworld");

        method = testClass.getDeclaredMethod("Test", String.class, boolean.class);
        method.invoke(object, "helloworld", true);


    }
}
