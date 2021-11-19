package queue05;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java-vip-course
 * @description:
 * @author: Mr.Wan
 * @create: 2021-04-08 21:34
 **/
public class Demo {

    //生成当前的阻塞队列
    private BlockingQueue<Ball> blockingQueue = new ArrayBlockingQueue<Ball>(1);

    //当前队列大小
    public int queueSize(){
        return blockingQueue.size();
    }

    //生产者,放入队列
    public void produce(Ball ball) throws InterruptedException {
        blockingQueue.put(ball);
    }
    //消费者,队列拿出来
    public Ball consume() throws InterruptedException {
        return blockingQueue.take();
    }

    public static void main(String[] args) {
        final ArrayBlockingQueueTest box = new ArrayBlockingQueueTest();
        //创建当前对象
        Demo demo = new Demo();
        //创建可缓存线程池,长度超过需求,灵活回收线程.若无则创建线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Runnable() {
            public void run() {
                int i=0;
                while (true){
                    Ball ball = new Ball();
                    ball.setNumber("兵乓球"+i);
                    ball.setColor("yellow");
                    try {
                        box.produce(ball);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
