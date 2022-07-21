import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

//可打断锁
@Slf4j(topic = "c.ReentrantLock")
public class TestReentrantLock2 {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            log.debug("获得锁可打断锁");
            try {
                //如果没有竟争那么此方法就会获取Lock对象锁
                //如果有竟争就进入阻塞队列，可以被其它线程用interruput方法打断
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("被打断了，没获得锁");
                return;
            }
            try {
                log.debug("获取了锁");
            }finally {
                reentrantLock.unlock();
            }
        },"t1");

//        主线程先上锁，开启线程t1
        reentrantLock.lock();
        t1.start();
        Thread.sleep(1000);
        log.debug("打断");
        t1.interrupt();

    }
}
