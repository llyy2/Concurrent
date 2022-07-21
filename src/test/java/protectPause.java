import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

//保护性暂停实现一对一，一个postman对应送一个
@Slf4j(topic = "c.protectPause")
public class protectPause {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0;i<3;i++){
            new Person().start();
        }
        Thread.sleep(1000);
        for (Integer id : MailBoxs.getIds()) {
            new Postman(id,"mail" +id).start();
        }

    }
}

@Slf4j(topic = "c.person")
class Person extends Thread{
    @Override
    public void run() {
        //收信
        GuardeObject guardeObject = MailBoxs.createGuardeObject();
        log.debug("开始收信id:{}",guardeObject.getId());
        Object mail = guardeObject.get(5000);
        log.debug("收到信id{}--内容",guardeObject.getId(),mail);
    }
}

@Slf4j(topic = "c.postman")
class Postman extends Thread{
    //邮件id
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardeObject guardedObject = MailBoxs.getGuardedObject(id);
        log.debug("送信id:{} 内容{}",guardedObject.getId(),mail);
        guardedObject.complete(mail);
    }
}

class MailBoxs{
//    boxs被多线程使用，使用hashtable线程安全
    private static Map<Integer,GuardeObject> boxs = new Hashtable<>();
//    ID自增
    private static int id = 1;
//    产生唯一id synchronized同步多线程id的使用
    private static synchronized int generateId(){
        return id++;
    }

    public static GuardeObject createGuardeObject(){
        GuardeObject go = new GuardeObject(generateId());
        boxs.put(go.getId(),go);
        return go;
    }

    public static Set<Integer> getIds(){
        return boxs.keySet();
    }

    public static  GuardeObject  getGuardedObject(int id){
        return boxs.remove(id);
    }
}

//保护性暂停实现
//添加超时效果
class GuardeObject{

    private int id;

    public GuardeObject(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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