# GC 分析
## 模拟GC代码
```java
import java.util.Random;  
import java.util.concurrent.TimeUnit;  
import java.util.concurrent.atomic.LongAdder;  
  
/**  
 * @author oldFlag  
 * @since 2022/3/10  
 */
public class GCLogAnalysis {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        long endMillis = startMillis + timeoutMillis;

        LongAdder counter = new LongAdder();

        System.out.println("正在执行...");

        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];

        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = RANDOM.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }

        System.out.println("执行结束！共生成对象次数：" + counter.longValue());

    }

    public static Object generateGarbage(int max) {
        int randomSize = RANDOM.nextInt(max);
        int type = randomSize % 4;

        switch (type) {
            case 0:
                return new int[randomSize];
            case 1:
                return new byte[randomSize];
            case 2:
                return new double[randomSize];
            default:
                StringBuilder sb = new StringBuilder();
                String randomString = "randomString-Anything";
                while (sb.length() < randomSize) {
                    sb.append(randomString).append(max).append(randomSize);
                }
                return sb.toString();
        }
    }
}
```

## 并行GC（默认 GC 算法）
使用 jdk 8 执行上面的代码，该情况不指定任何的 GC 算法，以及内存大小。
观察日志发现，默认情况下，jdk 8 使用了Parallel GC且各个区域内存的大小是在不断变化的，并且 GC 使用时间也也有波动（0.01s 到 0.035s 之间）。这是因为 **默认情况下，JDK 8 会开启自适应参数，此时很多参数都会被 JVM 在运行中动态调整**，包括 堆内存每个区域的大小、对象晋升的阈值等。

程序的执行次数在 57000 左右。

### 限定堆内存大小
指定参数 `-Xmx1g -Xms1g` 后，GC 次数明显频繁了许多，同时单次 GC 暂停时间也比默认情况下短了非常多。

程序执行次数在 52000 左右。观察发现此时虽然单次 GC 的时间很短，但是总的 GC 时间较长，而且吞吐量相对默认情况有一定的降低，同时单次 GC 的时长波动也较大。

指定参数 `-Xmx4g -Xms4g`后，GC 次数明显下降，且没有发生 Full GC。

程序执行次数在 56000 左右。此时 年轻代 容量为 1000 mB 左右，老年代容量在 2000 mB 左右，吞吐量接近默认设置，而且年轻代的 GC 时间较长。把程序执行的时间拉长到 10s 后，结果依旧类似。

指定参数 `-Xmx256m -Xms256m` 后，运行程序出现了 `OutOfMemoryError:Java heap space`。

### 不限定堆内存大小

指定参数 `-Xmx1g`，后对比堆内存固定的情况可以发现 GC 次数变多，且 FullGC 次数也变多了，随着每次 FullGC 的出现，JVM 会加大堆内存的容量。

指定参数 `-Xmx4g`，指定最大堆内存为 4g，但是不指定初始堆内存大小。

程序执行次数在 58000 左右，甚至可以到 60000。根据观察可以发现这个时候的 年轻代总容量在 1000 mB，而 老年代 的容量在 778 mB。此时跑出了一个非常不错的成绩，平均 GC 时间较短，吞吐量较大，总的 GC 时间也较短，在把程序的运行时间加长到 10s 后，这种模式依旧是 并发GC 几种配置中成绩最好的。

根据日志可以发现在运行过程中，年轻代大小为 1.1g，老年代大小 1.2g，在指定堆大小时，老年代被强行分配为 2.5g 左右的大空间，导致老年代堆积了很多对象之后才进行垃圾回收， Full GC 时间相对更长。

在手动设置了参数 `-Xmx2432m -Xms2432m -Xmn1344m` 后也可以跑出类似的结果了，GC 用时 和 吞吐量 达到了一个平衡点。所以可以得出结论，内存大小并非越大越好，在满足程序正常运行且保有一定余量时，可以尽量减少内存的大小，使实际需要使用的内存靠近分配的内存大小。

手工调整实际运行时参数 ：
```
-XX:InitialHeapSize=2550136832 
-XX:MaxHeapSize=2550136832 
-XX:MaxNewSize=1409286144 
-XX:NewSize=1409286144
```

## Serial GC

指定参数 `-Xmx1g -Xms1g -XX:+UseSerialGC`。

根据日志发现 串行GC 的效率非常低下。

## CMS GC

### 不限定堆内存大小

根据日志我们可以看到这种情况下 JVM 使用了 ParNew GC 作为年轻代回收器。
同时可以发现默认情况下 JVM 设置了最大堆内存空间为 8G，也就是系统内存的 $1/4$，并且在运行过程中，堆内存大小随着 GC 次数的增加在逼近这个值，而且在这种情况下 CMS 并没有对老年代进行清理。

程序最终执行次数在 36000 左右。吞吐量非常低下，但是在 GC暂停 的表现上确实不错，即便堆内存大小有 8g 也和 2g 堆内存的 并发GC 差不多。

通过 `-Xmx2304m` 将最大内存设置在 2g 左右，此时程序执行次数在 40000 左右，但是 GC 暂停表现非常好，最高也没有超过 10ms，相比 并发GC 在暂停这方面有一定的优势，但是在吞吐量上相差非常大。

### 限定堆内存大小

当我们指定  `-Xmx1g -Xms1g` 时，发现 CMS 对老年代开始回收了。

程序最终执行次数在 50000 左右。

手动指定 `-Xmx4g -Xms4g`。

程序最终执行次数在 45000 左右，但是明显比上一种稳定了很多。但是通过观察可以发现实际上并没有发生 CMS 的 GC。

手动指定 `-Xmx2304m -Xms2304m -Xmn512m`，程序执行次数在 45000 左右，但是 GC 的延迟表现却不好。

手动指定 `-Xmx2048m -Xms2048m -Xmn200m`，程序执行次数在 41000 左右，但是 GC 的延迟比较稳定，全部都在 10ms 及以下，但是平均值比较高比较靠近 10ms。

手动指定 `-Xmx2048m -Xms2048m -Xmn600m`，程序执行次数在 46000 左右，吞吐量比较不错，但是依旧有波动，但是 GC 延迟方面非常不错，大部分延迟都在 10ms 一下，极少部分在 10-20ms 之间。

### CMS 总结

CMS 总体来说比较适合堆内存不是特别大的配置（小于 4g 甚至 2g），当堆特别大时延迟就会明显提高；年轻代容量比较适合占堆内存的 $1/3$（此时吞吐量较大），两个条件都满足时比较接近 CMS 的理想情况，吞吐量 与 GC延迟 都比较理想。如果整个堆内存比较大，比如到达了 4g，那么此时如果想要得到一个低的 GC 延迟就要牺牲非常多的吞吐量。

## G1 GC

### 不限定堆内存大小

程序最终执行次数在 40000 左右。此时 JVM 申请了 8g 的堆内存，观察 GC 暂停可以发现延迟表现还是比较不错的，基本都在 10ms 一下，少部分在 10-20ms之间。

### 限定堆内存大小

使用参数 `-Xmx1g -Xms1g`限定堆大小为 1g。

程序最终执行次数在 40000 左右（比不限定吞吐量接近，或者稍低）。此时的 GC 延迟表现依旧不错，所有的 GC 延迟都控制在了 10ms 以下。

使用参数 `-Xmx2g -Xms2g` 限定堆大小为 2g。

程序最终执行次数在 53000 左右，吞吐量大幅提升，同时延迟表现也非常不错，几乎全部 GC 暂停都控制在了 10ms 以下，少数在 10-20ms 之间。但是在程序执行时间加到 5s 的情况下，无论是 GC 暂停 还是 吞吐量 都出现了很大的劣化。

使用参数 `-Xmx4g -Xms4g` 指定堆大小为4G。程序最终执行次数依然在 50000 左右，吞吐量提升不大，但是延迟方面有比较大的损耗，开始出现一些在 10-30ms 之间的 GC 暂停。但是如果把程序执行时间加长到 5s 及以上，吞吐量提升非常大，超过了 $50000*5$，而且 GC暂停 的表现相比 1s 的情况没有太大的变化，甚至在平均值上表现更好了。


### G1 总结

G1 GC 在给到 4g 以上的堆内存大小时会比较有优势，在这个内存大小下吞吐量实际与并行GC差异不大甚至可能更高，同时在平均延迟上表现更好。同时在测试中发现，控制 `-Xmx` 的值与 `-Xms` 的值一致对 G1 来说非常有必要，如果不一致会导致吞吐量下降非常多以及出现一些抖动。
但是反过来讲，在 4g 以下的堆内存可能就不一定适合 G1 了，G1 需要分配足够的内存空间才能有理想的表现。
