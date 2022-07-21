import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.RoundingMode;
import java.security.GuardedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;


//@Slf4j(topic = "c.tt")
//public class test2 {
//    public static void main(String[] args) {
//        Runnable runnable = new Runnable() {
//            public void run() {
//                log.debug("running2");
//            }
//        };
//
//        Thread thread = new Thread(runnable,"t2");
//        thread.start();
//    }
//}

//@Slf4j(topic = "c.tt")
//public class test2 {
//    public static void main(String[] args) throws Exception{
//        final FutureTask futureTask = new FutureTask<>(new Callable() {
//            public Object call() throws Exception {
//                log.debug("running---");
//                Thread.sleep(2000);
//                return 1000;
//            }
//        });
//
//        Thread thread = new Thread(futureTask,"t2");
//        thread.start();
//        log.debug("{}",futureTask.get());
//    }
//
//}

//线程状态
//@Slf4j(topic = "c.tt")
//public class test2 {
//    public static void main(String[] args) throws Exception{
//
//        Thread t1 = new Thread("t1"){
//
//            @Override
//            public void run() {
//                log.debug("running");
//            }
//        };
//
//        System.out.println(t1.getState());
//        t1.start();
//        System.out.println(t1.getState());
//    }
//
//
//    }

////interrupt
//@Slf4j(topic = "c.tt")
//public class test2 {
//    public static void main(String[] args) {
//
//        Thread t1 = new Thread("t1"){
//
//            @Override
//            public void run() {
//                log.debug("running");
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    System.out.println("wake up--");
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        t1.start();
//        log.debug("interrupt......");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t1.interrupt();
//
//        System.out.println(t1.getState());
//        t1.start();
//        System.out.println(t1.getState());
//    }
//
//
//}
//


//yield  Priority
//interrupt
//@Slf4j(topic = "c.tt")
//public class test2 {
//    public static void main(String[] args) {
//
//        Runnable task1 =() ->{
//
//            int count =0;
//            for(;;){
//                System.out.println("------"+count);
//            }
//
//        };
//
//        Runnable task2 =() ->{
//
//            int count =0;
//            for(;;){
////                Thread.yield();
//                System.out.println("               ------"+count);
//            }
//
//        };
//
//        Thread t1 = new Thread(task1);
//        Thread t2 = new Thread(task2);
////        t1.setPriority(Thread.MIN_PRIORITY);
////        t2.setPriority(Thread.MAX_PRIORITY);
//        t1.start();
//        t2.start();
//    }
//
//
//}

//join join 等待线程结束 有join r =10 没有join r=0
//@Slf4j(topic = "c.tt")
//public class test2 {
//    static int r=0;
//    public static void main(String[] args) {
//            test1();
//        }
//     private static  void test1()throws Exception{
//        log.debug("开始");
//        Thread t1 = new Thread(()->{
//           log.debug("开始");
//           sleep(1);
//           log.debug("结束");
//           r =10;
//        },"t1");
//        t1.start();
//        t1.join();
//        log.debug("结果为",r);
//        log.debug("结束");
//    }



////两阶段终止模式
//@Slf4j(topic = "c.test2")
//public class test2{
//    public static void main(String[]args) throws InterruptedException {
//        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
//        twoPhaseTermination.start();
//        Thread.sleep(3500);
//        twoPhaseTermination.stop();
//        }
//}
//
@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination{
    //线程监控
    private Thread monitor;

    //启动线程
    public void  start(){
        monitor = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                System.out.println("线程状况"+current.isInterrupted());
                if (current.isInterrupted()){
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    //重新设置打断标记
                    e.printStackTrace();
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    //停止监控
   public void stop(){
        monitor.interrupt();
    }
}


//interrupted打断park interrupted打断，并清除打断标记，isInterrupted(），不会清除打断标记 一直为true
//两阶段终止模式
//@Slf4j(topic = "c.test2")
//public class test2{
//    public static void main(String[]args) throws InterruptedException {
//
//    }
//}
//@Slf4j(topic = "c.TwoPhaseTermination")
//class test3{
//    public static void main(String[] args) {
//        test3();
//    }
//
//    private static void test3(){
//        Thread t1 =new Thread(()->{
//            log.debug("park状态");
//            LockSupport.park();
//            log.debug("unpark.....");
//            log.debug("打断状态{}",Thread.currentThread().isInterrupted());
//
//            LockSupport.park();
//            log.debug("unpark.....");
//        },"t1");
//        t1.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t1.interrupt();
//
//    }
//
//
//}


////synchronized 对象锁 面向过程
//@Slf4j(topic = "c.test2")
//public class test2{
//    static int counter = 0;
//    static Object lock = new Object();
//    public static void main(String[]args) throws InterruptedException {
//        Thread t1 = new Thread(()->{
//            for (int i = 0;i<5000;i++){
//                synchronized (lock){
//                    counter++;
//                }
//            }
//        },"t1");
//
//        Thread t2 = new Thread(()->{
//            for (int i = 0;i<5000;i++){
//                synchronized (lock){
//                    counter--;
//                }
//            }
//        },"t2");
//
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        log.debug("counter的值{}",counter);
//    }
//}


//synchronized 对象锁 面向对象 只能锁对象
//@Slf4j(topic = "c.test2")
//public class test2{
//    public static void main(String[]args) throws InterruptedException {
//        Room room = new Room();
//        Thread t1 = new Thread(()->{
//            for (int i = 0;i<5000;i++){
//                room.incement();
//            }
//        },"t1");
//
//        Thread t2 = new Thread(()->{
//            for (int i = 0;i<5000;i++){
//                room.decment();
//            }
//        },"t2");
//
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        log.debug("counter的值{}",room.getCounter());
//    }
//}
//
//
//class Room{
//    private int counter=0;
//
//    public void incement(){
//        synchronized (this){
//            counter++;
//        }
//    }
//
//    //synchronized加在方法上，用来锁对象而不是锁方法，加在static上锁住类对象
//    public synchronized void incement1(){
//            counter++;
//    }
//
//    public void decment(){
//        synchronized (this){
//            counter--;
//        }
//    }
//
//    public int getCounter(){
//        synchronized (this){
//            return counter;
//        }
//    }
//}




//@Slf4j(topic = "c.test2")
//public class test2{
//    public static void main(String[]args) throws InterruptedException {
//        Number number = new Number();
//        new Thread(()->{
//            log.debug("begin1");
//            try {
//                number.a();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"t1").start();
//
//
//        new Thread(()->{
//            log.debug("begin2");
//            number.b();
//        },"t2").start();
//
//
//       new Thread(()->{
//            log.debug("begin3");
//            number.c();
//        },"t3").start();
//
//
//
//    }
//}
//
////c不加synchronized出现的情况
////3 1s 12
////23 1s 1
////32 1s 1
//@Slf4j(topic = "c.sy")
//class Number{
//
//    public synchronized void a() throws InterruptedException {
//        sleep(1);
//        log.debug("1");
//    }
//
//    public synchronized void b(){
//        log.debug("2");
//    }
//
//    public void c(){
//        log.debug("3");
//    }
//}


////买票synchronized
//@Slf4j(topic ="c.test" )
//class buy{
//    public static void main(String[] args) {
//        //票数
//        TiicketWindow tiicketWindow = new TiicketWindow(1000);
//
//        //所有线程集合
//        List<Thread> list = new ArrayList<>();
//        //票数统计
//        List<Integer> integerList = new Vector<>();
//
//        for (int i =0;i<3000;i++){
//            Thread thread = new Thread(()->{
//                //买票
//                int amount = tiicketWindow.sell(5);
////            统计买票数
//                integerList.add(amount);
//            });
//            list.add(thread);
//            thread.start();
//        }
//        for (Thread thread : list) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //统计卖出得票数和剩余票数
//        log.debug("余票{}",tiicketWindow.getCounter());
//        log.debug("卖出的票数{}",integerList.stream().mapToInt(i->i).sum());
//
//    }
//
//
//}
////售票窗口
//class TiicketWindow{
//    private int counter;
//
//    public TiicketWindow(int counter){
//        this.counter=counter;
//    }
//
//    public int getCounter(){
//        return counter;
//    }
//
//    //counter为共享变量，用synchronized
//    public synchronized int sell(int amount){
//        if (this.counter>=amount){
//            this.counter-=amount;
//            return amount;
//        }else {
//            return 0;
//        }
//    }
//}



////转账两个对象共享一个资源synchronized
//@Slf4j(topic ="c.test" )
//class transfer12{
//    public static void main(String[] args) throws InterruptedException {
//        Account a = new Account(1000);
//        Account b = new Account(1000);
//
//        Thread t1 = new Thread(()->{
//            for (int i= 0; i<1000;i++){
//                a.transfer(b,100);
//            }
//
//
//    });
//    Thread t2 = new Thread(()->{
//        for (int i= 0; i<1000;i++){
//            b.transfer(a,100);
//        }
//
//});
//
//    t1.start();
//    t2.start();
//    t1.join();
//    t2.join();
//
//    log.debug("total{}",a.getMoney()+b.getMoney());
//
//}
//
//}
//
////售票窗口
//class Account {
//    private int money;
//
//    public Account(int money) {
//        this.money = money;
//    }
//
//    public int getMoney() {
//        return money;
//    }
//
//    public void setMoney(int money) {
//        this.money = money;
//    }
//
//    //转账
//    public void transfer(Account target, int amount) {
//        synchronized (Account.class) {
//            if (this.money >= amount) {
//                this.setMoney(this.getMoney() - amount);
//                target.setMoney(target.getMoney() + amount);
//            }
//        }
//    }
//}


//wait-notify

//@Slf4j(topic = "c.wait")
//public class test2{
//   final static Object obj = new Object();
//    public static void main(String[] args) {
//
//
//            new Thread(()->{
//                synchronized (obj) {
//                    log.debug("执行t1");
//                    try {
//                        obj.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.debug("t1执行其他代码");
//                }
//                },"t1").start();
//
//        new Thread(()->{
//            synchronized (obj) {
//                log.debug("执行t2");
//                try {
//                    obj.wait();
////                    可以设置等待时间
////                    obj.wait(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.debug("t2执行其他代码");
//            }
//        },"t2").start();
//
//
//        //唤醒其他线程
//        log.debug("唤醒其他线程");
//        synchronized (obj){
//            //随机唤醒一个
////            obj.notify();
//            obj.notifyAll();
//        }
//
//
//
//    }
//}


//sleep不释放锁，wait释放锁

//@Slf4j(topic = "c.test")
//public class test2{
//    final static Object obj = new Object();
//    public static void main(String[] args) throws InterruptedException {
//
//
//        new Thread(()->{
//            synchronized (obj){
//                log.debug("t1获得锁");
//                try {
////                    Thread.sleep(20000);
//                    obj.wait(20000);
//                } catch (InterruptedException e) {
//
//                }
//            }
//        },"t1").start();
//
//        Thread.sleep(1000);
//        synchronized (obj){
//            log.debug("主线程获得锁");
//        }
//
//    }
//}



//@Slf4j(topic = "c.test")
//public class test2{
//    final static Object room= new Object();
//     static Boolean hasY= false;
//     static Boolean hasW = false;
//    public static void main(String[] args) throws InterruptedException {
//
//        new Thread(()->{
//            synchronized (room){
//                log.debug("有没有烟{}",hasY);
//                while (!hasY){
//                    log.debug("没有，歇会！！");
//                    try {
//                        room.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                log.debug("有没有烟{}",hasY);
//                if(hasY){
//                    log.debug("烟来了，继续开工");
//                }else {
//                    log.debug("继续等");
//                }
//            }
//        },"小张").start();
//
//        new Thread(()->{
//            synchronized (room){
//                log.debug("有没有外卖{}",hasW);
//                while (!hasW){
//                    log.debug("没有，歇会！！");
//                    try {
//                        room.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                log.debug("有没有外卖{}",hasW);
//                if(hasW){
//                    log.debug("外卖来了，继续开工");
//                }else {
//                    log.debug("继续等");
//                }
//            }
//        },"小潘").start();
//
//        Thread.sleep(1000);
//        new Thread(()->{
//            synchronized (room){
//                log.debug("外卖来了");
//                hasW=true;
//                room.notifyAll();
//            }
//        },"送外卖的").start();
//
//
//    }
//}
@Slf4j(topic = "c.guradedObject")
public class test2{
    public static void main(String[] args) throws IOException {
        Guarde guarde = new Guarde();


//        new Thread(()->{
//            //等待结果
//            log.debug("等待结果");
//            List<String> list = (List<String>) guarde.get();
//            //结果大小
//            log.debug("结果大小为{}",list.size());
//        },"t1").start();
//
//        new Thread(()->{
//            //执行下载
//            log.debug("执行下载");
//            try {
//                List<String> list = Downloader.download();
//                guarde.complete(list);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        },"t2").start();

        new Thread(()->{
            //等待结果
            log.debug("等待结果");
            Object reponse= guarde.get(2000);
            //结果大小
            log.debug("结果大小为{}",reponse);
        },"t1").start();

        new Thread(()->{
            //执行下载
            log.debug("执行下载");
            try {
                Thread.sleep(3000);
                guarde.complete(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t2").start();

    }
}


//保护性暂停实现
//添加超时效果
 class Guarde{

    //结果
    private Object response;

    //等待结果
    public Object get(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long process = 0;
            while (response==null){
                //这一轮循环应该等待的时间
                long watiing = timeout-process;
                //经历的时间超过最大等待时间就退出循环
                if (watiing<=0){
                    break;
                }
                try {
                    this.wait(watiing);//使用watiing原因是避免虚假唤醒，如开始时间是15:00:00，到wati这里是15:00:01，
                    // 经历了1s,process时间为1s,经过判断再次循环，原超时时间为2s,
                    // 但是到wait的时候已经经历了1s,因此只需再等待1s即可，用timeout-process
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //经历的时间
                process = System.currentTimeMillis()-begin;
            }
            return response;
        }

    }

    //产生结果
    public void complete(Object response){
        synchronized (this){
            this.response=response;
            this.notifyAll();
        }
    }

}