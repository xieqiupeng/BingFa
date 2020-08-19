
1. 2 个线程都要用到 SimpleDateFormat

下面我们用一个案例
来说明这种典型的第一个场景。
假设有个需求，
即 2 个线程
都要用到 SimpleDateFormat。
代码如下所示：

```
public class ThreadLocalDemo01 {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            String date = new ThreadLocalDemo01().date(1);
            System.out.println(date);
        }).start();
        Thread.sleep(100);
        new Thread(() -> {
            String date = new ThreadLocalDemo01().date(2);
            System.out.println(date);
        }).start();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }
}
```

在以上代码中可以看出，
两个线程
分别创建了一个
自己的 SimpleDateFormat 对象，
如图所示：

这样一来，有两个线程，
那么就有两个 SimpleDateFormat 对象，
它们之间互不干扰，
这段代码是可以正常运转的，
运行结果是

```
00:01
00:02
```