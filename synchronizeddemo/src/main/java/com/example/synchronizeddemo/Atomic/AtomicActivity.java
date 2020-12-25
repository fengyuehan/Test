package com.example.synchronizeddemo.Atomic;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * author : zhangzf
 * date   : 2020/12/21
 * desc   :
 */
public class AtomicActivity extends AppCompatActivity {
    public  int count = 0;
    public static AtomicLong count1 = new AtomicLong(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atomic);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_atomic_example_1).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AtomicExample1();
            }
        });
        findViewById(R.id.btn_atomic_example_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample2();
            }
        });
        findViewById(R.id.btn_atomic_example_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample2();
            }
        });
        findViewById(R.id.btn_atomic_example_4).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AtomicExample4();
            }
        });
        findViewById(R.id.btn_atomic_example_5).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AtomicExample5();
            }
        });
        findViewById(R.id.btn_atomic_example_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample6();
            }
        });
        findViewById(R.id.btn_atomic_example_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample7();
            }
        });
        findViewById(R.id.btn_atomic_example_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample8();
            }
        });
        findViewById(R.id.btn_atomic_example_9).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AtomicExample9();
            }
        });
        findViewById(R.id.btn_atomic_example_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample10();
            }
        });
        findViewById(R.id.btn_atomic_example_11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample11();
            }
        });
        findViewById(R.id.btn_atomic_example_12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample12();
            }
        });
    }

    /**
     * semaphore
     *
     * 可以用来控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
     * 适用于那些资源有明确访问数量限制的场景，常用于限流 。
     */
    private void AtomicExample12() {
        final Semaphore semaphore=new Semaphore(10);
        for(int i=0;i<20;i++){
            Thread thread=new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("===="+Thread.currentThread().getName()+"来到停车场");
                        if(semaphore.availablePermits()==0){
                            Log.e("zzf","车位不足，请耐心等待");
                        }
                        semaphore.acquire();//获取令牌尝试进入停车场
                        Log.e("zzf",Thread.currentThread().getName()+"成功进入停车场");
                        Thread.sleep(new Random().nextInt(1000));//模拟车辆在停车场停留的时间
                        Log.e("zzf",Thread.currentThread().getName()+"驶出停车场");
                        semaphore.release();//释放令牌，腾出停车场车位
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },i+"号车");

            thread.start();
        }
    }

    /**
     * CyclicBarrier与CountDownLatch的区别
     *
     * CyclicBarrier的计数器由自己控制，而CountDownLatch的计数器则由使用者来控制，在CyclicBarrier中线程调用await方法不仅会将自己阻塞还会将计数器减1，
     * 而在CountDownLatch中线程调用await方法只是将自己阻塞而不会减少计数器的值。
     *
     * CountDownLatch只能拦截一轮，而CyclicBarrier可以实现循环拦截。
     *
     * CountDownLatch是线程组之间的等待，即一个(或多个)线程等待N个线程完成某件事情之后再执行；而CyclicBarrier则是线程组内的等待，
     * 即每个线程相互等待，即N个线程都被拦截之后，然后依次执行。
     */

    /**
     * CyclicBarrier,CyclicBarrier 基于 Condition 来实现的。
     *
     * 在CyclicBarrier类的内部有一个计数器，每个线程在到达屏障点的时候都会调用await方法将自己阻塞，此时计数器会减1，
     * 当计数器减为0的时候所有因调用await方法而被阻塞的线程将被唤醒。
     */
    private void AtomicExample11() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                Log.e("zzf","都到了");
            }
        });

        Thread_01[] threads = new Thread_01[5];
        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread_01(cyclicBarrier);
            threads[i].start();
        }
    }

    /**
     * CountDownLatch
     * 类似于join，可以使一个获多个线程等待其他线程各自执行完毕后再执行。CountDownLatch 定义了一个计数器，和一个阻塞队列， 当计数器的值递减为0之前，阻塞队列里面的线程处于挂起状态，
     * 当计数器递减到0时会唤醒阻塞队列所有线程，这里的计数器是一个标志，可以表示一个任务一个线程，也可以表示一个倒计时器，
     * CountDownLatch可以解决那些一个或者多个线程在执行之前必须依赖于某些必要的前提业务先执行的场景。
     *
     * 在下面的例子中，主线程会等待四个子线程执行完再执行。
     *
     * CountDownLatch 基于 AQS 的共享模式的使用。
     */
    private void AtomicExample10() {
        //用于聚合所有的统计指标
        final Map map=new HashMap();
        //创建计数器，这里需要统计4个指标
        final CountDownLatch countDownLatch=new CountDownLatch(4);
        //记录开始时间
        long startTime=System.currentTimeMillis();
        Thread countUserThread=new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("正在统计新增用户数量");
                    Thread.sleep(3000);//任务执行需要3秒
                    map.put("userNumber",1);//保存结果值
                    countDownLatch.countDown();//标记已经完成一个任务
                    Log.e("zzf","统计新增用户数量完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread countOrderThread=new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("正在统计订单数量");
                    Thread.sleep(3000);//任务执行需要3秒
                    map.put("countOrder",2);//保存结果值
                    countDownLatch.countDown();//标记已经完成一个任务
                    Log.e("zzf","统计订单数量完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread countGoodsThread=new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("正在商品销量");
                    Thread.sleep(3000);//任务执行需要3秒
                    map.put("countGoods",3);//保存结果值
                    countDownLatch.countDown();//标记已经完成一个任务
                    Log.e("zzf","统计商品销量完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread countmoneyThread=new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("正在总销售额");
                    Thread.sleep(3000);//任务执行需要3秒
                    map.put("countmoney",4);//保存结果值
                    countDownLatch.countDown();//标记已经完成一个任务
                    Log.e("zzf","统计销售额完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //启动子线程执行任务
        countUserThread.start();
        countGoodsThread.start();
        countOrderThread.start();
        countmoneyThread.start();
        try {
            //主线程等待所有统计指标执行完毕
            countDownLatch.await();//这个在哪个线程，就阻塞哪个线程，等待其他线程执行完再执行
            long endTime=System.currentTimeMillis();//记录结束时间
            Log.e("zzf","------统计指标全部完成--------");
            Log.e("zzf","统计结果为："+map.toString());
            Log.e("zzf","任务总执行时间为"+(endTime-startTime)/1000+"秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * LongAdder
     * LongAdder克服了高并发下使用AtomicLong的缺点。既然AtomicLong的性能瓶颈是由于过多线程同时去竞争一个变量的更新而产生的，LongAdder则是把一个变量分解为多个变量，让同样多的线程去竞争多个资源，解决了性能问题。
     * LongAdder 是把一个变量拆成多份，变为多个变量，有点像 ConcurrentHashMap 中 的分段锁把一个Long型拆成一个base变量外加多个Cell，每个Cell包装了一个Long型变量。
     * 这样，在同等并发量的情况下，争夺单个变量更新操作的线程量会减少，这变相地减少了争夺共享资源的并发量。<br />另外，多个线程在争夺同一个Cell原子变量时如果失败了，
     * 它并不是在当前Cell变量上一直自旋CAS重试，而是尝试在其他Cell的变量上进行CAS尝试，这个改变增加了当前线程重试CAS成功的可能性。
     * 最后，在获取LongAdder当前值时，是把所有Cell变量的value值累加后再加上base返回的。LongAdder维护了一个延迟初始化的原子性更新数组（默认情况下Cell数组是null）和一个基值变量base。
     * 由于Cells占用的内存是相对比较大的，所以一开始并不创建它，而是在需要时创建，也就是惰性加载。
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AtomicExample9() {
        AtomicLongTest();
        LongAdderTest();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void LongAdderTest() {
        int requestTotal = 100;
        final LongAdder count = new LongAdder();
        final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        long start = System.currentTimeMillis();
        for (int i = 0; i < requestTotal; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    add(count);
                    countDownLatch.countDown();
                }
            }).start();
            Log.e("zzf","count=" + count);
            Log.e("zzf","耗时：" + (System.currentTimeMillis() - start));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void add(LongAdder count) {
        count.add(1);
    }

    private void AtomicLongTest() {
        // 并发线程数
        int requestTotal = 500;
        // 求和总数
        final int sumTotal = 1000000;

        final AtomicLong count = new AtomicLong(0);
        ExecutorService executorService = Executors.newFixedThreadPool(requestTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        long start = System.currentTimeMillis();
        for (int i = 0; i < requestTotal; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    add2(sumTotal,count);
                    countDownLatch.countDown();
                }
            });
        }
        Log.e("zzf","count=" + count);
        Log.e("zzf","耗时：" + (System.currentTimeMillis() - start));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    private void add2(int sumTotal,AtomicLong count) {
        for (int j = 0; j < sumTotal; j++) {
            count.getAndIncrement();
        }
    }

    /**
     * AtomicMarkableReference
     * 原子更新带有版本号的引用类型，该类将整数值与引用关联起来，可用于原子的更新数据和数据版本号，可以解决使用CAS进行原子更新时可能出现的ABA问题。
     */
    private void AtomicExample8() {
        /**
         * 1表示版本号
         */
        final AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(99, 1);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                Log.e("zzf",Thread.currentThread().getName() + "---首次 stamp： " + stamp);
                atomicStampedReference.compareAndSet(99, 100,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                Log.e("zzf",Thread.currentThread().getName() + "---第二次 stamp： " + atomicStampedReference.getStamp());
                atomicStampedReference.compareAndSet(100, 99,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                Log.e("zzf",Thread.currentThread().getName() + "---第三次 stamp： " + atomicStampedReference.getStamp());
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                Log.e("zzf",Thread.currentThread().getName() + "---首次 stamp： " + stamp);
                try {
                    Log.e("zzf",Thread.currentThread().getName() + "---你的校园网正在尝试重新连接......");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean result = atomicStampedReference.compareAndSet(99, 100,
                        stamp, stamp + 1);
                Log.e("zzf",Thread.currentThread().getName() + "---修改成功与否：" + result + "  当前 stamp：" + atomicStampedReference.getStamp());
                Log.e("zzf",Thread.currentThread().getName() + "---当前课程已选人数：" + atomicStampedReference.getReference());
            }
        },"t2");
        t1.start();
        t2.start();
    }

    /**
     * AtomicMarkableReference 通过 boolean 值作为是否更改的标记，所以他的版本号只有 true 和false。在并发中如果两个版本号不断地切换，
     * 任然不能很好地解决 ABA 问题，只是从某种程度降低了ABA事件发生。
     * AtomicMarkableReference只有true和false两种状态，如果来回切换，也不能很好的解决ABA问题。所以引入AtomicStampedReference。
     */
    private void AtomicExample7() {
        Teacher teacher = new Teacher( "小春哥",200);
        AtomicMarkableReference<Teacher> markableReference = new AtomicMarkableReference<>(teacher, true);
        Teacher newTeacher = new Teacher("懵懂少年",210);
        Log.e("zzf","当前返回状态: "+markableReference.compareAndSet(teacher, newTeacher, true, false));
        Log.e("zzf","AtomicMarkableReference 状态: "+markableReference.isMarked());
        Teacher twoTeacher = new Teacher("懵懂少年",201);
        Log.e("zzf","当前返回状态: "+markableReference.compareAndSet(teacher, newTeacher, true, false));
        Log.e("zzf","AtomicMarkableReference 状态: "+markableReference.isMarked());
    }

    /**
     * 允许原子更新指定类的指定voltile字段
     * 更新原子对象的对象有两个条件
     * 1：因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
     * newUpdater(Teacher.class, String.class, "name")：表示想要更新Teacher类中name字段，name字段是String类型，所以传入String.class
     */
    private void AtomicExample6() {
        AtomicReferenceFieldUpdater referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Teacher.class, String.class, "name");
        Teacher teacher = new Teacher("小春哥", 200);
        referenceFieldUpdater.compareAndSet(teacher, "小春哥", "公众号:山间木匠");
        Log.e("zzf",teacher.getName() + "----------" + teacher.getTicketNum());
    }

    /**
     * 原子更新引用类型,更新一个对象
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AtomicExample5() {
        AtomicReference<Teacher> atomicReference = new AtomicReference<>();

        BinaryOperator<Teacher> binaryOperator = new BinaryOperator<Teacher>() {
            @Override
            public Teacher apply(Teacher teacher, Teacher teacher2) {
                return teacher2;
            }
        };

        Teacher teacher = new Teacher("小春哥", 200);
        // 将当前对象设置到引用对象 AtomicReference 中
        atomicReference.set(teacher);
        Teacher updateTeacher = new Teacher("懵懂少年", 180);
        // teacher 和 引用类型AtomicReference 保存的对象一致 则能修改成功
        atomicReference.compareAndSet(teacher, updateTeacher);
        Log.e("zzf",atomicReference.get().getName() + "----------" + atomicReference.get().getTicketNum());

        Teacher accumulateTeacher = new Teacher("懵懂少年", 210);
        // 原子性地更新指定对象，并且返回AtomicReference更新后的值
        atomicReference.accumulateAndGet(accumulateTeacher, binaryOperator);
        Log.e("zzf",atomicReference.get().getName() + "----------" + atomicReference.get().getTicketNum());
    }

    /**
     * 数组
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AtomicExample4() {
        AtomicLongArray arr = new AtomicLongArray(5);
        LongUnaryOperator longUnaryOperator = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long operand) {
                return operand + 10;
            }
        };
        LongBinaryOperator longBinaryOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };

        /**
         * 自增
         */
        Log.e("zzf","索引 0 incrementAndGet=" + arr.getAndIncrement(0));
        Log.e("zzf","索引 0 incrementAndGet=" + arr.getAndIncrement(0));
        /**
         * 自减
         */
        Log.e("zzf","索引 0 incrementAndGet=" + arr.decrementAndGet(0));
        Log.e("zzf","索引 0 incrementAndGet=" + arr.decrementAndGet(0));
        /**
         * 以原子方式将输入的数值与实例中的值（AtomicLongArray（0）里的value）相加
         */
        Log.e("zzf","索引 0 addAndGet=" + arr.addAndGet(0, 100));

        Log.e("zzf","*********** JDK 1.8 ***********");
        /**
         * 返回新值
         */
        Log.e("zzf","索引 0 getAndUpdate=" + arr.updateAndGet(0, longUnaryOperator));
        /**
         * 返回旧值
         */
        Log.e("zzf","索引 0 getAndUpdate=" + arr.getAndUpdate(0, longUnaryOperator));
        /**
         * 使用给定函数应用给指定下标和给定值的结果原子更新当前值,并返回当前值
         */
        Log.e("zzf","索引 1 accumulateAndGet=" + arr.accumulateAndGet(1, 10, longBinaryOperator));
    }

    /**
     * 原子
     */
    private void AtomicExample2() {
        int requestTotal = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        long start = System.currentTimeMillis();
        for (int i = 0; i < requestTotal; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //add();
                    add1();
                    countDownLatch.countDown();
                }

            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("zzf","count=" + count);
        Log.e("zzf","count=" + count1);
        Log.e("zzf","耗时：" + (System.currentTimeMillis() - start));
    }

    private void add() {
        ++count;
    }

    private void add1() {
        count1.getAndIncrement();
    }

    /**
     * AtomicLong通过CAS提供了非阻塞的原子性操作，相比使用阻塞算法的同步器来说它的性能已经很好了，但是JDK开发组并不满足于此。
     * 使用AtomicLong时，在高并发下大量线程会同时去竞争更新同一个原子变量，但是由于同时只有一个线程的CAS操作会成功，
     * 这就造成了大量线程竞争失败后，会通过无限循环不断进行自旋尝试CAS的操作，而这会白白浪费CPU资源。
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AtomicExample1() {
        AtomicLong count = new AtomicLong(0);
        LongUnaryOperator longUnaryOperator = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long operand) {
                return 1;
            }
        };

        LongBinaryOperator longBinaryOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };
        /**
         * getAndIncrement:以原子方式将当前值加1，返回旧值 （i++）
         */
        Log.e("zzf","getAndIncrement=" + count.getAndIncrement());
        /**
         * 以原子方式将当前值加1，返回新值（++i）
         */
        Log.e("zzf","incrementAndGet=" + count.incrementAndGet());
        /**
         * 以原子方式将当前值减少 1，返回旧值（i--）
         */
        Log.e("zzf","incrementAndGet=" + count.getAndDecrement());
        /**
         * /以原子方式将当前值减少 1，返回旧值 （--i）
         */
        Log.e("zzf","incrementAndGet=" + count.decrementAndGet());
        /**
         * 以原子方式将输入的数值与实例中的值（AtomicLong里的value）相加，并返回结果
         */
        Log.e("zzf","addAndGet=" + count.addAndGet(10));
        /**
         * 以原子方式设置为`newValue`的值，并返回旧值
         */
        Log.e("zzf","getAndSet=" + count.getAndSet(100));

        Log.e("zzf","get=" + count.get());

        Log.e("zzf","*********** JDK 1.8 ***********");

        /**
         * 使用将给定函数定函数的结果原子更新当前值，返回上一个值
         */
        Log.e("zzf","getAndUpdate=" + count.getAndUpdate(longUnaryOperator));

        Log.e("zzf","getAndUpdate=" + count.getAndUpdate(longUnaryOperator));
        Log.e("zzf","get=" + count.get());
        /**
         *使用给定函数应用给当前值和给定值的结果原子更新当前值，返回上一个值
         */
        Log.e("zzf","getAndAccumulate=" + count.getAndAccumulate(2, longBinaryOperator));
        Log.e("zzf","getAndAccumulate=" + count.getAndAccumulate(2, longBinaryOperator));
    }
}
