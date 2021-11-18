package com;

/**
 * @program:
 * @description:初始化阶段
 * @author: Mr.Wan
 * @create: 2021-11-18 0018
 **/
public class InitializationTest {
    public static int id = 1;
    public static int number;
    static {
        number = 2;
        System.out.println("父类静态");
    }
}
