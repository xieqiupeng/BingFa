###
线程池的参数

| 参数名 | 含义 |
| --- | --- |
| corePoolSize | 核心线程数 |
| maxPoolSize | 最大线程数 |
| --- | --- |
| keepAliveTime + 时间单位 | 空闲线程存活时间 |
| --- | --- |
| ThreadFactory | 线程工厂，创建新线程 |
| --- | --- |
| workQueue | 任务队列，存放任务 |
| Handler | 处理被拒绝的任务 |