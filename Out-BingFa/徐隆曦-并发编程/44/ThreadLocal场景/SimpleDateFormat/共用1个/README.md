
4. 所有的线程
都共用一个
simpleDateFormat 对象

我们用下面的代码来演示
只用一个 simpleDateFormat 对象的情况：

```
public class ThreadLocalDemo04 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDemo04().date(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        return dateFormat.format(date);
    }
}
```

在代码中可以看出，
其他的没有变化，
变化之处就在于，我们把这个
simpleDateFormat 对象
给提取了出来，
变成 static 静态变量，
需要用的时候直接去
获取这个静态对象就可以了。
看上去省略掉了
创建 1000 个 
simpleDateFormat 对象的开销，
看上去没有问题，
我们用图形的方式
把这件事情给表示出来：

从图中可以看出，
我们有不同的线程，
并且线程会执行它们的任务。
但是不同的任务所调用的
simpleDateFormat 对象
都是同一个，
所以它们
所指向的那个对象
都是同一个，
但是这样一来
就会有
线程不安全的问题。