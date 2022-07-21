import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.ReentrantLock")
public class TestReentrantLock {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) {

//        ReentrantLock的可重入特性
        reentrantLock.lock();
        try {
            log.debug("enter main");
            m1();
        }finally {
            reentrantLock.unlock();
        }


    }
    public static void m1(){
        reentrantLock.lock();
        try {
            log.debug("enter m1");
            m2();
        }finally {
            reentrantLock.unlock();
        }
    }

    public static void m2(){
        reentrantLock.lock();
        try {
            log.debug("enter m2");
        }finally {
            reentrantLock.unlock();
        }
    }
}
