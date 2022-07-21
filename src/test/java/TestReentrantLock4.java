import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//可打断锁
@Slf4j(topic = "c.ReentrantLock")
public class TestReentrantLock4 {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Condition condition1 = reentrantLock.newCondition();

        condition1.await();
        condition1.signal();
    }
}
