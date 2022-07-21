import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//可锁超时
@Slf4j(topic = "c.ReentrantLock")
public class TestReentrantLock3 {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

       Thread t1 = new Thread(()->{
           log.debug("尝试获得锁");
           try {
//               tryLock不带参数，立刻结束等待直接返回
               if(!reentrantLock.tryLock(2, TimeUnit.SECONDS)){
                   log.debug("获取不到锁");
                   return;
               }
           } catch (InterruptedException e) {
               log.debug("获取不到锁");
               e.printStackTrace();
               return;
           }
           try{
               log.debug("获得锁了");
           }finally {
               reentrantLock.unlock();

           }

       },"t1");

       reentrantLock.lock();
       log.debug("获得锁");
       t1.start();
       Thread.sleep(1000);
       log.debug("释放锁");
       reentrantLock.unlock();
    }
}
