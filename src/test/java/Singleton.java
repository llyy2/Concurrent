public final class Singleton {
    public Singleton() {
    }
    private static volatile Singleton INSTANCE = null;
    public static Singleton getINSTANCE(){
//        实例没创建才可以进入synchronize代码块 第一个if在synchronize代码块外面，防止synchronize代码过多影响性能
        if (INSTANCE == null){
            synchronized (Singleton.class){
                //避免其他线程已经创建线程再判断一次
                if (INSTANCE == null){
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

}
