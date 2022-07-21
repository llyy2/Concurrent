import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestLiveLock")
public class TestLiveLock {
    static volatile int count = 10;
    public static void main(String[] args) {

        new Thread(()->{
            while (count > 0){
                try {
                    log.debug("count --{}",count);
                    count--;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            while (count < 20){
                log.debug("count ++{}",count);
                try {
                    count++;
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }
}
