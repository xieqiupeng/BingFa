###
[ThreadPoolExecutor](http://825635381.iteye.com/blog/2184680)
重点讲解： 
其中比较容易让人误解的是：
+ corePoolSize，
+ maximumPoolSize，
+ workQueue之间关系。 

1. 当线程池小于corePoolSize时，
新提交任务将创建一个新线程执行任务，
即使此时线程池中存在空闲线程。 

2. 当线程池达到corePoolSize时，
新提交任务将被放入workQueue中，
等待线程池中任务调度执行 

3. 当workQueue已满，
且maximumPoolSize>corePoolSize时，
新提交任务会创建新线程执行任务 

4. 当提交任务数超过maximumPoolSize时，
新提交任务由RejectedExecutionHandler处理 

5. 当线程池中超过corePoolSize线程，
空闲时间达到keepAliveTime时，关闭空闲线程 

6. 当设置allowCoreThreadTimeOut(true)时，
线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭 