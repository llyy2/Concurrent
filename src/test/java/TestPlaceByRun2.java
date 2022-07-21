import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//交替输出 ReentrantLock实现
@Slf4j(topic = "c.TestPlaceByRun")
public class TestPlaceByRun2 {
    public static void main(String[] args) throws InterruptedException {
       WaitNotify2 waitNotify2 = new WaitNotify2(5);
        Condition conditionA = waitNotify2.newCondition();
        Condition conditionB = waitNotify2.newCondition();
        Condition conditionC = waitNotify2.newCondition();
        new Thread(()->{
            waitNotify2.print("A",conditionA,conditionB);
        }).start();
        new Thread(()->{
            waitNotify2.print("B",conditionB,conditionC);
        }).start();
        new Thread(()->{
            waitNotify2.print("C",conditionC,conditionA);
        }).start();

        Thread.sleep(1000);
        waitNotify2.lock();
        try{
            conditionA.signal();
        }finally {
            waitNotify2.unlock();
        }

    }
}

//打印内容    打印标记    下一打印
//a           1           2
//b           2           3
//c           3           1
class WaitNotify2 extends ReentrantLock{

    private int loopNum;

    public WaitNotify2(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str,Condition current,Condition next){
            for (int i = 0;i < loopNum; i++){
                lock();
                try {
                        current.await();
                        System.out.print(str + " ");
                        next.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                    unlock();
                }

            }
    }




}