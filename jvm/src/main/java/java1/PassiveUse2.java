package java1;

import org.junit.Test;

import java.util.Random;

/**
 * @description:
 * @author: Mr.Wan
 * @create: 11:20:50
 **/
public class PassiveUse2 {
    @Test
    public void test1(){
        System.out.println(Person.num);
        System.out.println(Person.num1);
    }

    @Test
    public void test2(){
        System.out.println(SerialA.ID);
//        System.out.println(SerialA.ID1);
    }

    @Test
    public void test3(){
        try {
            Class clazz = ClassLoader.getSystemClassLoader().loadClass("com.java1.Order");
            System.out.println(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Person {
    static {
        System.out.println("类初始化");
    }

    public static final int num = 1;//在链接过程的准备环节就被赋值为1了。
    public static final int num1 = new Random().nextInt(10);//此时的赋值操作需要在<clinit>()中执行

}

interface SerialA{
    public static final Thread t = new Thread() {
        {
            System.out.println("SerialA的初始化");
        }
    };

    static final int ID = 1;
    int ID1 = new Random().nextInt(10);//此时的赋值操作需要在<clinit>()中执行
}
