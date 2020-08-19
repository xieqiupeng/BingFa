3. 需求变成了 1000 个线程

都要用到 SimpleDateFormat

但是线程不能无休地创建下去，因为线程越多，
所占用的资源也会越多。
假设我们需要 1000 个任务，
那就不能再用 for 循环的方法了，
而是应该使用线程池来实现线程的复用，
否则会消耗过多的内存等资源。

在这种情况下，
我们给出下面这个代码实现的方案：

```
public class ThreadLocalDemo03 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalDemo03().date(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(date);
    }
}
```

可以看出，我们用了一个
16 线程的线程池，
并且给这个线程池
提交了 1000 次任务。
每个任务中
它做的事情
和之前是一样的，
还是去执行 date 方法，
并且在这个方法中
创建一个 simpleDateFormat 对象。
程序的一种运行结果是
（多线程下，运行结果不唯一）：

```
00:00
00:07
00:04
00:02
...
16:29
16:28
16:27
16:26
16:39
```

程序运行结果正确，
把从 00:00 到 16:39
这 1000 个时间
给打印了出来，
并且没有重复的时间。
我们把这段代码
用图形化给表示出来，
如图所示：

图的左侧是一个线程池，
右侧是 1000 个任务。
我们刚才所做的就是每个任务
都创建了一个 
simpleDateFormat 对象，
也就是说，
1000 个任务对应
1000 个 simpleDateFormat 对象。

但是这样做
是没有必要的，
因为这么多对象的创建
是有开销的，
并且在使用完之后
销毁同样是有开销的，
而且这么多对象
同时存在在内存中
也是一种内存的浪费。

现在我们就来优化一下。
既然不想要这么多的
simpleDateFormat 对象，
最简单的就是只用一个就可以了。