package com.dm.DGCat.AppTest;

import com.dm.DGCat.AppTest.Demo.ContextHolder;
import com.dm.DGCat.AppTest.Demo.DemoEntity;
import com.dm.DGCat.Application;
import com.dm.DGCat.dao.DiaryFileDAO;
import com.dm.DGCat.config.SpringContextUtil;
import com.dm.demo.myautoconfige.TestServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AppTest
{
	@Value("${custom.data.data0}")
	String printSrtr;

	@Autowired
	DiaryFileDAO DiaryFileDAO;
	//@Test
	public void Test1() {
		Flux.range(1, 100)
				/**
				 * 设置订阅池 线程使用和位置有关  设置之后才会使用设置的线程
				 * 当前线程，通过 Schedulers.immediate()方法来创建。
				 * 单一的可复用的线程，通过 Schedulers.single()方法来创建。
				 * 使用弹性的线程池，通过 Schedulers.elastic()方法来创建。线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁。该调度器适用于 I/O 操作相关的流的处理。
				 * 使用对并行操作优化的线程池，通过 Schedulers.parallel()方法来创建。其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
				 * 使用支持任务调度的调度器，通过 Schedulers.timer()方法来创建。
				 * 从已有的 ExecutorService 对象中创建调度器，通过 Schedulers.fromExecutorService()方法来创建。
				 */
				//.publishOn(Schedulers.elastic())
				//设置推送池
				/**
				 * 在最开始出即使用在此处设置的线程
				 */
				.subscribeOn(Schedulers.elastic())
				/**
				 * 过滤器  只留下符合条件的元素
				 */
				.filter(i -> i % 2 == 0)
				.log("Range") //添加了 log 操作符并指定了日志分类的名称
				/**
				 * 将传至此处的元素做修改   并保存为修改之后的信息
				 */
				.map(c->c*c)
				/**
				 * 在 map 操作符之后添加了一个名为 test 的检查点。当出现错误时，检查点名称会出现在异常堆栈信息中
				 */
				.checkpoint("test")
				/**
				 * onErrorReturn() 错误时 返回默认值
				 * switchOnError(Mono.just(0)) 方法来使用另外的流来产生元素
				 * onErrorResumeWith() 方法来根据不同的异常类型来选择要使用的产生元素的流。
				 */
				//.onErrorReturn(0)
				/**
				 * buffer 将当前流的元素收集到集合中，并将集合作为流中的新元素  下例 每个集合最大5个元素
				 */
				//.buffer(5)
				/**
				 * bufferTimeout 同上  多一个执行时间间隔条件  以毫秒为单位
				 */
				//.bufferTimeout(5, Duration.ofMillis(1000))
				/**
				 * bufferWhile 则只有当 Predicate 返回 true 时才会收集。一旦值为 false，会立即开始下一次收集。
				 */
				//.bufferWhile(i -> i % 5 != 0)
				/**
				 * bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中；
				 */
				//.bufferUntil(i -> i % 2 == 0)
				/**
				 * zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；
				 * 也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。
				 */
				//.zipWith(Flux.range(101, 50))
				.zipWith(Flux.range(201, 50), (s1, s2) -> String.format("%s-%s", s1, s2))
				/**
				 * take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。
				 * take(long n)，take(Duration timespan)和 takeMillis(long timespan)：按照指定的数量或时间间隔来提取。
				 * takeLast(long n)：提取流中的最后 N 个元素。
				 * takeUntil(Predicate<? super T> predicate)：提取元素直到 Predicate 返回 true。
				 * takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取 fasle时立即中止
				 * takeUntilOther(Publisher<?> other)：提取元素直到另外一个流开始产生元素。
				 */
				//.take(10)
				//.take(Duration.ofMillis(500))
				//.takeMillis(long timespan)
				//.takeLast(5)
				//.takeUntil(c->c % 3==0)
				//.takeWhile(c->c % 2==0)
				/**
				 * reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
				 * 累积操作是通过一个 BiFunction 来表示的。在操作时可以指定一个初始值。
				 * 如果没有初始值，则序列的第一个元素作为初始值。
				 */
				//.reduce((x, y) -> x + y)
				//.reduceWith(() -> 100, (x, y) -> x + y)
				//.doOnComplete(System.out::println)//成功执行订阅操作后执行
				.log("Range") //添加了 log 操作符并指定了日志分类的名称
				.subscribe(System.out::println);//订阅时  才会真正的执行  以上工作全部为准备工作
		/**
		 * 使用generate 使用Sink对象 初始化数据流
		 */
//		final Random random = new Random();
//		Flux.generate(ArrayList::new,(list, sink) -> {
//					int value = random.nextInt(100);
//					list.add(value);
//					sink.next(value);
//					if (list.size() == 10) {
//						//complete 结束生成
//						sink.complete();
//					}
//					return list; })
//				.subscribe(System.out::println);

		/**
		 * create 使用FluxSink生成对象  一次性将全部对象生成
		 */
//		Flux.create(sink -> {
//			for (int i = 0; i < 10; i++) {
//				sink.next(i);
//			}
//			sink.complete();
//		}).subscribe(System.out::println);

	}

	//@Test
	public void Test2(){
		try{
			DemoEntity<String> demoTest = (DemoEntity<String>) ContextHolder.getBean("demoEnityUse");
			int i=0;
			for (String ss: demoTest.getDemoList()) {
				System.out.println(MessageFormat.format("[ Value "+String.valueOf(i)+"] : {0}",ss));
				i++;
			}
			System.out.println("[ ApplicationConfigeration ] : GetConfigeration");
			System.out.println(printSrtr);
		}catch(Exception e){
			if(e instanceof  org.springframework.beans.factory.NoSuchBeanDefinitionException)
				System.out.println("[ NullBeanError ] : NullBean");
		}
	}

	//@Test
	public void Test3(){
		ApplicationContext ctx = SpringContextUtil.getApplicationContext();
		String msg = ctx.getMessage("hello", new String[]{"你的名字"}, Locale.US);
		System.out.println("Message is ===> " + msg);
	}
	@Resource
	TestServer testServer;
	@Test
	public void Test4(){
		String msg=testServer.getTest();
		System.out.println("Message is ===> " + msg);
	}

}
