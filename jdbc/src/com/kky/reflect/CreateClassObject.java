package com.kky.reflect;

import com.kky.entity.Emp;

/**
 * @author 柯凯元
 * @date 2021/07/05 17:23
 */
public class CreateClassObject {
    public static void main(String[] args) throws ClassNotFoundException {
        //1.通过 class.forname() 来获取 Class 对象
        Class class1 = Class.forName("com.kky.entity.Emp");
        System.out.println(class1.getPackage());
        System.out.println(class1.getName());
        System.out.println(class1.getSimpleName());
        System.out.println(class1.getCanonicalName());

        System.out.println("----------------");

        //2.通过 类名.class 来获取 Class 对象
        Class<Emp> class2 = Emp.class;
        System.out.println(class2.getPackage());
        System.out.println(class2.getName());
        System.out.println(class2.getSimpleName());
        System.out.println(class2.getCanonicalName());

        System.out.println("----------------");

        //3.通过对象的 getClass() 来获取
        //不推荐，因为会创建对象
        Class<? extends Emp> class3 = new Emp().getClass();
        System.out.println(class3.getPackage());
        System.out.println(class3.getName());
        System.out.println(class3.getSimpleName());
        System.out.println(class3.getCanonicalName());

        System.out.println("----------------");

        //4. 如果是一个基本数据类型，可以通过 class 的方式
        Class<Integer> class4 = int.class;
        System.out.println(class4.getPackage());
        System.out.println(class4.getName());
        System.out.println(class4.getSimpleName());
        System.out.println(class4.getCanonicalName());
        //或者通过 Type 的方式
        Class<Integer> class5 = Integer.TYPE;
        System.out.println(class5.getPackage());
        System.out.println(class5.getName());
        System.out.println(class5.getSimpleName());
        System.out.println(class5.getCanonicalName());

    }
}
