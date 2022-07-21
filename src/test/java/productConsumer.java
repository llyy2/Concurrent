import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j(topic = "c.productConsumer")
public class productConsumer {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(3);
        for (int i = 0;i < 3; i++){
//            使用id 代替i 是因为Lambda只支持fianl变量，thread
            int id = i;
            new Thread(()->{
                queue.put(new Message(id,"value"+id));
            },"生产者"+i).start();
        }

        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(2000);
                    Message message = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//
            }
        },"消费者").start();
    }
}

//消息队列类，Java线程间的通信
@Slf4j(topic = "c.MessageQueue")
class MessageQueue{
//    消息队列
    private LinkedList<Message> list = new LinkedList<>();
//    队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    //    获取消息
    public Message take(){
        synchronized (list){
            //检查对列是否为空
            while (list.isEmpty()){
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            从队列头部获取消息并返回
            Message message = list.removeFirst();
            log.debug("消费者消费信息{}",message);
            list.notifyAll();
            return message;
        }

    }

//    存入消息
    public void put(Message message){
//        检查对象是否满
        synchronized (list){
            while (list.size()==capcity){
                try {
                    log.debug("队列已满，生产者等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //将消息加入队列尾部
            list.addLast(message);
            log.debug("生产者生产消息{}",message);
            list.notifyAll();
        }

    }

}

class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}