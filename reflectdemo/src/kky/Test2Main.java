package kky;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 柯凯元
 * @date 2021/07/06 12:10
 */
public class Test2Main {
    public static void main(String[] args) throws Exception {

        Class<Test2> aClass = Test2.class;

        //调用 private 构造器
        Constructor<Test2> declaredConstructor = aClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Test2 test2 = declaredConstructor.newInstance();
        System.out.println(test2);

        //访问 private 属性
        Field id = aClass.getDeclaredField("id");
        id.setAccessible(true);
        id.set(test2,666);
        System.out.println(test2);

        //调用 private 方法
        Method add = aClass.getDeclaredMethod("add", Integer.TYPE, Integer.TYPE);
        add.setAccessible(true);
        System.out.println(add.invoke(test2,6,6));
    }
}
