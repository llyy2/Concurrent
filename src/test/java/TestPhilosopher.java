import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

//哲学家问题 死锁
@Slf4j(topic = "c.Testphilosopher")
public class TestPhilosopher {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("阿里士多得1",c1,c2).start();
        new Philosopher("阿里士多得2",c2,c3).start();
        new Philosopher("阿里士多得3",c3,c4).start();
        new Philosopher("阿里士多得4",c4,c5).start();
        new Philosopher("阿里士多得5",c5,c1).start();




    }
}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread{
   Chopstick left;
   Chopstick right;

    public Philosopher(String name,Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
//            synchronized (left){
//                synchronized (right){
//                    try {
//                        eat();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            使用tryLock方法解决死锁问题
            //左手拿到筷子
            if (left.tryLock()){
                try{
                    if (right.tryLock()){
                        try {
                            eat();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }

        }

    }

    private void eat() throws InterruptedException {
        log.debug("eat......");
        Thread.sleep(500);
    }
}

class Chopstick extends ReentrantLock {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "chopstick{" +
                name +
                '}';
    }
}

//class Chopstick{
//    private String name;
//
//    public Chopstick(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "chopstick{" +
//                  name +
//                '}';
//    }
//}
