import lombok.extern.slf4j.Slf4j;

//交替输出 wait-ify
@Slf4j(topic = "c.TestPlaceByRun")
public class TestPlaceByRun {
    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1,6);
        new Thread(()->{
            waitNotify.print("a",1,2);
        }).start();
        new Thread(()->{
            waitNotify.print("b",2,3);
        }).start();
        new Thread(()->{
            waitNotify.print("c",3,1);
        }).start();

    }
}

//打印内容    打印标记    下一打印
//a           1           2
//b           2           3
//c           3           1
class WaitNotify{
    private int flag;
    private int loopNum;

    public void print(String str,int waitFlag,int nextFlag){
        for (int i = 0; i < loopNum; i++){
            synchronized (this){
                while (flag != waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(str + " ");
                flag = nextFlag;
                this.notifyAll();
            }
        }

    }

    public WaitNotify(int flag, int loopNum) {
//        打印标记
        this.flag = flag;
//        打印次数
        this.loopNum = loopNum;
    }
}