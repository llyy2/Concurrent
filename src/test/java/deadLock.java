import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.deadLock")
public class deadLock {
    public static void main(String[] args) {
        test1();
    }

    private static void test1(){
        Object A = new Object();
        Object B = new Object();

        Thread t1 = new Thread(()->{
            synchronized (A){
                log.debug("lockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    log.debug("lockB");
                    log.debug("下一步--");
                }
            }
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (B){
                log.debug("lockB");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    log.debug("lockA");
                    log.debug("下一步--");
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
