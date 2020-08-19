
2. 10 个线程都要用到 SimpleDateFormat

假设我们的需求有了升级，
不仅仅需要 2 个线程，而是需要 10 个，
也就是说，有 10 个线程
同时对应 10 个 SimpleDateFormat 对象。
我们就来看下面这种写法：

```
public class ThreadLocalDemo02 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                String date = new ThreadLocalDemo02().date(finalI);
                System.out.println(date);
            }).start();
            Thread.sleep(100);
        }
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }
}
```
上面的代码利用了一个 for 循环
来完成这个需求。
for 循环一共循环 10 次，
每一次都会新建一个线程，
并且每一个线程都会在 date 方法中
创建一个 SimpleDateFormat 对象，
示意图如下：

可以看出一共有 10 个线程，
对应 10 个 SimpleDateFormat 对象。

代码的运行结果：
```
00:00
00:01
00:02
00:03
00:04
00:05
00:06
00:07
00:08
00:09
```