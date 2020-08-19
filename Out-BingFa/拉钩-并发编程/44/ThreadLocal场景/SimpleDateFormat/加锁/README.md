6. 加锁

出错的原因就在于，
simpleDateFormat 这个对象
本身不是一个线程安全的对象，
不应该被多个线程同时访问。
所以我们就想到了一个解决方案，
用 synchronized 来加锁。
于是代码就修改成下面的样子：

```
public class ThreadLocalDemo05 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDemo05().date(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        String s = null;
        synchronized (ThreadLocalDemo05.class) {
            s = dateFormat.format(date);
        }
        return s;
    }
}
```

可以看出在 date 方法中
加入了 synchronized 关键字，
把 simpleDateFormat 的调用
给上了锁。

运行这段代码的结果（多线程下，运行结果不唯一）：

```
00:00
00:01
00:06
...
15:56
16:37
16:36
```

这样的结果是正常的，
没有出现重复的时间。
但是由于我们使用了
synchronized 关键字，
就会陷入一种排队的状态，
多个线程不能同时工作，
这样一来，
整体的效率
就被大大降低了。
有没有更好的解决方案呢？

我们希望达到的效果是，
既不浪费过多的内存，
同时又想保证线程安全。
经过思考得出，
可以让每个线程都拥有一个
自己的 simpleDateFormat 对象
来达到这个目的，
这样就能两全其美了。
