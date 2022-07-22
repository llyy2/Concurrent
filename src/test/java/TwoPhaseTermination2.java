import lombok.extern.slf4j.Slf4j;
@Slf4j(topic = "c.test")
class test{
    public static void main(String[] args) throws InterruptedException {
//        TwoPhaseTermination2 termination2 = new TwoPhaseTermination2();
//        termination2.start();
//        termination2.start();
//        Thread.sleep(3000);
//        termination2.stop();
        //使用balking模式使创建多个线程时只开启一个monitor
        TwoPhaseTermination3 twoPhaseTermination3 = new TwoPhaseTermination3();
        twoPhaseTermination3.start();
        twoPhaseTermination3.start();
    }
}


@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination2{
    //线程监控
    private Thread monitor;
//    stop为多线程共享变量，volatile让一个线程的修改对另一个可见，避免因为jit即时编译产生热点代码
    private volatile boolean stop = false;
    //启动线程
    public void  start(){
        monitor = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                System.out.println("线程状况"+current.isInterrupted());
                if (stop){
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {

                }
            }
        });
        monitor.start();
    }

    //停止监控
    public void stop(){
       stop = true;
       //interrupt()打断sleep
       monitor.interrupt();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination3{
    //线程监控
    private Thread monitor;
    //    stop为多线程共享变量，volatile让一个线程的修改对另一个可见，避免因为jit即时编译产生热点代码
    private volatile boolean stop = false;

    private boolean starting = false;
    //启动线程
    public void  start(){

            synchronized (this){
                if (starting){
                    return;
                }
                starting = true;

            }
            monitor = new Thread(()->{
                while (true){
                    Thread current = Thread.currentThread();
                    System.out.println("线程状况"+current.isInterrupted());
                    if (stop){
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                        log.debug("执行监控记录--");
                    } catch (InterruptedException e) {

                    }
                }
            });
            monitor.start();

    }

    //停止监控
    public void stop(){
        stop = true;
        //interrupt()打断sleep
        monitor.interrupt();
    }
}