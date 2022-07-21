import lombok.extern.slf4j.Slf4j;

//同步顺序，固定运行顺序，synchronize实现,先打印2再打印1
@Slf4j(topic = "c.SynchronizeRun")
public class SynchronizeRun {
    static Object lock = new Object();
    static boolean t2Runned = false;
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                while (!t2Runned){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (lock){
                 log.debug("2");
                 t2Runned = true;
                 lock.notify();
            }
        },"t2").start();
    }
}
