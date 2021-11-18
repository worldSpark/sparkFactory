package com;

/**
 * @description:
 * @author: Mr.Wan
 * @create: 11:02:31
 **/
public class SubInitialization extends InitializationTest{
    static {
        number = 4;
        System.out.println("子类静态");
    }

    public static void main(String[] args) {
        System.out.println(number);
    }
}
