package countdownlaunch03;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: java-vip-course
 * @description:
 * @author: Mr.Wan
 * @create: 2021-04-07 20:35
 **/
public class Test {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args)  {
        final Test test = new Test();

        new Thread(){
            public void run() {
                test.get(Thread.currentThread(),new StringBuffer());
            };
        }.start();

        new Thread(){
            public void run() {
                test.get(Thread.currentThread(),new StringBuffer());
            };
        }.start();

    }

    public void get(Thread thread,StringBuffer stringBuffer) {
        String name = thread.getName();
        stringBuffer.append(name);
        rwl.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            while(System.currentTimeMillis() - start <= 1) {
                if(stringBuffer.toString().indexOf(thread.getName())>-1){
                    stringBuffer.append("-");
                }
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(stringBuffer);
            System.out.println(thread.getName()+"读操作完毕");
        } finally {
            rwl.readLock().unlock();
        }
    }

}
