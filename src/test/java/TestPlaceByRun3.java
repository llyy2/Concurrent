import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

//交替输出 ReentrantLock实现
@Slf4j(topic = "c.TestPlaceByRun")
public class TestPlaceByRun3 {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) throws InterruptedException {
       parkUnpark parkUnpark = new parkUnpark(5);
            t1 = new Thread(()->{
                parkUnpark.print("a",t2);
            });
            t2 = new Thread(()->{
                parkUnpark.print("b",t3);
            });
            t3 = new Thread(()->{
                parkUnpark.print("c",t1);
            });

            t1.start();
            t2.start();
            t3.start();

            LockSupport.unpark(t1);

    }
}

//打印内容    打印标记    下一打印
//a           1           2
//b           2           3
//c           3           1
class parkUnpark {

    private int loopNum;

    public parkUnpark(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str,Thread thread){
        for (int i = 0; i < loopNum; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(thread);
        }
    }
}