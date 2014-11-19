import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyTest {

    public static void main(String[] args) {
//        int a = (5 << 16) | 6;
//        int b = (9 << 16) | 3;
//        int c = a + b;
//        System.out.println(c >> 16);
//        System.out.println(c & 65535);
//        try {
//            throw new Throwable();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                throw new Error();
//            }
//        };

        final ThreadLocal<String> threadLocal = new ThreadLocal();
        new Thread(){
            @Override
            public void run() {
                threadLocal.set("123");
            }
        }.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(){
            @Override
            public void run() {
                System.out.println(threadLocal.get());
            }
        }.start();
    }
}
