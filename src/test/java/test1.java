import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.test")
public class test1 {
    public static void main(String[] args) {
        Thread t1=new Thread(){
            public void run(){
                log.debug("running");
            }
        };
        t1.setName("t1");
        t1.run();
    }
}
