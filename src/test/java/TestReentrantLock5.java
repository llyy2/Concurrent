import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.TestReentrantLock5")
public class TestReentrantLock5{
    final static Object room= new Object();
     static Boolean hasY= false;
     static Boolean hasW = false;
     static ReentrantLock reentrantLock = new ReentrantLock();
     static Condition hasYY = reentrantLock.newCondition();
     static Condition hasWW = reentrantLock.newCondition();
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            reentrantLock.lock();
            try {
                log.debug("有没有烟{}",hasY);
                while (!hasY){
                    log.debug("没有，歇会！！");
                    try {
                        hasYY.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //虚假唤醒
//                log.debug("有没有烟{}",hasY);
//                if(hasY){
//                    log.debug("烟来了，继续开工");
//                }else {
//                    log.debug("继续等");
//                }
                log.debug("烟来了，继续开工");
            }finally {
                reentrantLock.unlock();
            }
        },"小张").start();

        new Thread(()->{
           reentrantLock.lock();
           try {
               log.debug("有没有外卖{}",hasW);
               while (!hasW){
                   log.debug("没有 歇会！！");
                   try {
                       hasWW.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               log.debug("外卖来了，继续开工");

           }finally {
               reentrantLock.unlock();
           }
        },"小潘").start();

        Thread.sleep(1000);
        new Thread(()->{
            reentrantLock.lock();
            try {
                log.debug("外卖来了");
                hasW = true;
                hasWW.signal();
            }finally {
                reentrantLock.unlock();
            }
        },"送外卖的").start();

        Thread.sleep(1000);
        new Thread(()->{
            reentrantLock.lock();
            try {
                    log.debug("烟来了");
                    hasY = true;
                    hasYY.signal();
                }finally {
                    reentrantLock.unlock();
            }
        },"送烟的").start();

    }
}