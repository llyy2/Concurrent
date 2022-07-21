import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.volatile")
public class TestVolatile {
    //无法退出循环 解决：
    //1、添加volatile 它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，线程操作volatile变量都是直接操作主存
    //2、使用synchronize
    static boolean run = true;
//    volatile static boolean run = true;

    static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
//            while (run){
//
//            }
            while (true){
                synchronized (object){
                    if (!run){
                        break;
                    }
                }

            }
        },"t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("停止run");
        synchronized (object){
            run = false;
        }
    }
}
