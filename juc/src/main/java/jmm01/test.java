package jmm01;

/**
 * @program: java-vip-course
 * @description:
 * @author: Mr.Wan
 * @create: 2021-04-07 16:03
 **/
public class test {

    private volatile static test myinstance;

    public static test getInstance(){
        if(myinstance == null) {
            synchronized (test.class) {
                if (myinstance == null) {
                    myinstance = new test();
                }
            }
        }
        return myinstance;
    }
}
