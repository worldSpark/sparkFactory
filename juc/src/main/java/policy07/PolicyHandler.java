package policy07;

/**
 * @author ：图灵-杨过
 * @date：2019/7/22
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description :
 */
public interface PolicyHandler {

    /**
     * 拒绝策略
     * @param task
     * @param executor
     */
    void rejected(Runnable task, TulingThreadPoolExecutor executor);

    void rejected(Runnable task, DemoThread demoThread);

}
