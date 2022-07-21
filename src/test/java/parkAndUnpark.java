import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.parkAndUnpark")
public class parkAndUnpark {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("start---");
            try {
                Thread.sleep(3000);
                log.debug("park------");
                LockSupport.park();
                log.debug("ruseme");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("unpark-----------");
        LockSupport.unpark(t1);
    }
}
