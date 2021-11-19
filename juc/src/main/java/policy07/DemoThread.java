package policy07;


import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-vip-course
 * @description:
 * @author: Mr.Wan
 * @create: 2021-04-14 21:48
 **/
public class DemoThread implements TuLingExecutorService {

    //默认队列大小
    private static final int defaultQueueSize = 5;

    //默认池大小
    private static final int defaultPoolSize = 5;

    //默认的时间
    private static final long defaultAliveTime = 60L;
    //线程池最大大小

    private static final int maxPoolSize = 50;

    //一个可见的线程池大小
    private volatile int poolsize;

    //任务容量
    private long completedTaskCount;

    //可见的拒绝策略
    private volatile PolicyHandler handler;
    //是否中断
    private volatile boolean isShutDown = false;

    //当前激活线程数,用于控制查询当前状态和数量
    private AtomicInteger ctl = new AtomicInteger();

    //队列
    private final BlockingQueue<Runnable> workQueue;

    //lock可重入锁
    private final ReentrantLock mainLock = new ReentrantLock();

    //work线程执行对象的集合
    private final HashSet<Worker> workers = new HashSet<Worker>();

    //是否允许超时
    private volatile boolean allowThreadTimeout;

    //设置阻塞时间
    private volatile long keepAliveTime;

    public DemoThread() {
        this(defaultPoolSize,defaultQueueSize,defaultAliveTime,new DefaultPolicyHandler());
    }

    public DemoThread(int poolsize) {
        this(poolsize,defaultQueueSize,defaultAliveTime,new DefaultPolicyHandler());
    }

    public DemoThread(int poolsize,int queueSize,long keepAliveTime,PolicyHandler policyHandler) {
        if(poolsize <=0 || poolsize >maxPoolSize){
            throw new IllegalArgumentException("线程池大小不能小于0");
        }
        this.poolsize = poolsize;
        this.handler = policyHandler;
        this.keepAliveTime = keepAliveTime;
        if(keepAliveTime>0){
            allowThreadTimeout = true;
        }
        this.workQueue = new ArrayBlockingQueue<Runnable>(queueSize);
    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task) {
        if(task == null ){
            throw new NullPointerException("任务不能为空");
        }
        if(isShutDown){
            throw new IllegalArgumentException("线程池销毁");
        }
        //任务数
        int i = ctl.get();
        if(i<poolsize){
            if(addWorker(task,true)){

            }else if(workQueue.offer(task)){

            }else{
                handler.rejected(task,this);
            }
        }
    }

    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            isShutDown = true;
            for (Worker worker : workers) {
                Thread thread = worker.thread;
                try {
                    if(thread.isInterrupted() && worker.tryLock()){
                        thread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    worker.unlock();
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    public int getCorePoolSize() {
        return ctl.get();
    }

    public Runnable getTask() {
        try {
            return allowThreadTimeout? workQueue.poll(keepAliveTime, TimeUnit.SECONDS) : workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean addWorker(Runnable r, boolean startNew){
        if(startNew){
            ctl.incrementAndGet();
        }
        boolean workerAdded = false;
        boolean workerStarted = false;
        Worker w = new Worker(r);
        Thread thread = w.thread;
        if (thread!= null){
            ReentrantLock mainLock = this.mainLock;
            mainLock.unlock();
            try {
                if(!isShutDown){
                    if(thread.isAlive()){
                        throw new IllegalArgumentException();
                    }
                    workers.add(w);
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if(workerAdded){
                thread.start();
                workerStarted = true;
            }
        }
        return workerStarted;
    }

    static AtomicInteger atomic = new AtomicInteger();

    class Worker extends ReentrantLock implements Runnable{

        volatile long completedTask;
        final Thread thread;
        Runnable firstTask;

        public Worker(Runnable r){
            this.firstTask = r;
            this.thread = new Thread(this,"thread-name-"+atomic.incrementAndGet());
        }

        public void run() {
            runWorker(this);
        }
    }

    private void runWorker(Worker worker){
        Thread wt = Thread.currentThread();
        Runnable task = worker.firstTask;
        worker.firstTask = null;
        boolean completedAbruptly = true;
        try {
            while (task != null || (task=getTask())!=null){
                worker.lock();
                if (isShutDown && !wt.isInterrupted()){
                    wt.interrupt();
                }
                try {
                    task.run();
                } finally {
                    task = null;
                    worker.completedTask++; //当前线程完成的任务数
                    worker.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(worker,completedAbruptly);
        }
    }

    private void processWorkerExit(Worker worker, boolean completedAbruptly) {
        if(completedAbruptly)
            ctl.decrementAndGet();

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += worker.completedTask;
            workers.remove(worker);
        } finally {
            mainLock.unlock();
        }
        if(completedAbruptly && !workQueue.isEmpty()){
            addWorker(null,false);
        }
    }



}
