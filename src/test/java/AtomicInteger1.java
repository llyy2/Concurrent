import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInteger1 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        System.out.println(i.getAndIncrement());//i++
        System.out.println(i.incrementAndGet());//++i

//        使用i.getAndAdd()解决TestCAS的问题
        System.out.println(i.getAndAdd(5));//i++5
        System.out.println(i.addAndGet(5));//++5
    }
}
