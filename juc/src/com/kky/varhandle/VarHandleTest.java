package com.kky.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleTest {
    public static void main(String[] args) {
        AClass aClass = new AClass();
        VarHandle varHandle = null;

        try {
            varHandle = MethodHandles.lookup().findVarHandle(AClass.class,"x",int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println((int)varHandle.get(aClass));
        varHandle.set(aClass,9);
        System.out.println((int)varHandle.get(aClass));

        // CAS
        varHandle.compareAndSet(aClass,9,10);
        // + 10
        varHandle.getAndAdd(aClass,10);
    }
}

class AClass{
    int x = 8;
}
