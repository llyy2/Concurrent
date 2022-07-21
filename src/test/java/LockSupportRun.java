import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.LockSupportRun")
//同步顺序，固定运行顺序，park与unpark实现,先打印2再打印1
public class LockSupportRun {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        },"t1");
        t1.start();

        new Thread(()->{
            log.debug("2");
            LockSupport.unpark(t1);
        },"t2").start();
    }
}
