package com.kky.reflect;

import com.kky.entity.Emp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 柯凯元
 * @date 2021/07/05 17:56
 */
public class ClassAPI {
    public static void main(String[] args) throws Exception{
        //获取 Class 对象
        Class<Student> studentClass = Student.class;

        /*
        获取 public 的成员变量，包括父类
         */
        Field[] fields = studentClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("------");

        /*
        获取当前类的所有属性
         */
        Field[] declaredFields = studentClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

        System.out.println("------");

        //反射在一定程度上破坏了封装性，需要合理使用
        Field address = studentClass.getDeclaredField("address");
        Student student = studentClass.newInstance();
        System.out.println(student.getAddress());
        //设置该属性是否能被访问
        address.setAccessible(true);
        address.set(student,"北京市");
        System.out.println(student.getAddress());

        System.out.println("------");

        /*
        获取该类的普通方法，包含父类。
        只能获取 public 方法
         */
        Method[] methods = studentClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        System.out.println("------");

        /*
        获取该类的所有方法
         */
        Method[] declaredMethods = studentClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

        System.out.println("------");

        //调用方法
        Method add = studentClass.getDeclaredMethod("add",Integer.TYPE,Integer.TYPE);
        add.setAccessible(true);
        Student student1 = studentClass.newInstance();
        add.invoke(student1,6,6);

        System.out.println("------");

        /*
        获取对象的 public 构造方法。
         */
        Constructor<?>[] constructors = studentClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("------");

        /*
        获取对象的所有构造方法，无论是私有的还是公共的
         */
        Constructor<?>[] declaredConstructors = studentClass.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }

        System.out.println("------");

        //如何调用私有构造方法？
        Constructor<Student> declaredConstructor =
                studentClass.getDeclaredConstructor(String.class, Integer.TYPE,
                        String.class);
        declaredConstructor.setAccessible(true);
        Student student2 = declaredConstructor.newInstance("kky", 18, "普通班");
        System.out.println(student2);

    }
}
