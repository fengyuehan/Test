package com.example.suspend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val mScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //main()
        //asyncTest2()
        //asyncTest1()
        //testCoroutineContext()
        //testContinuationInterceptor()
        testLunach()
    }

    private fun testLunach() {
        mScope.launch {
            var time1 = System.currentTimeMillis();
            launch {
                println("launch new continuation todo something. current thread: ${Thread.currentThread().name}")
            }
            println("launch1 时间 ： ${System.currentTimeMillis() - time1}")
            /*withContext(Dispatchers.Main){
                println("launch new continuation todo something in the main thread. current thread: ${Thread.currentThread().name}")
            }*/
            var time2 = System.currentTimeMillis();
            println("launch end. current thread: ${Thread.currentThread().name}")
            println("launch2 时间 ： ${System.currentTimeMillis() - time2}")
        }
    }

    /**
     * 测试拦截器
     *
     * 执行结果：
     *  2021-08-04 11:17:18.391 25123-25123/com.example.suspend I/System.out: intercept todo something. change run to thread
        2021-08-04 11:17:18.391 25123-25123/com.example.suspend I/System.out:  intercept create new thread
        2021-08-04 11:17:18.392 25123-25152/com.example.suspend I/System.out: intercept launch start. current thread: Thread-2
        2021-08-04 11:17:18.411 25123-25123/com.example.suspend I/System.out: intercept new continuation todo something in the main thread. current thread: main
        2021-08-04 11:17:18.411 25123-25123/com.example.suspend I/System.out:  intercept create new thread
        2021-08-04 11:17:18.412 25123-25153/com.example.suspend I/System.out: intercept todo something. change run to thread
        2021-08-04 11:17:18.413 25123-25153/com.example.suspend I/System.out:  intercept create new thread
        2021-08-04 11:17:18.413 25123-25153/com.example.suspend I/System.out: intercept launch end. current thread: Thread-3
        2021-08-04 11:17:18.413 25123-25154/com.example.suspend I/System.out: intercept new continuation todo something. current thread: Thread-4
     */
    private fun testContinuationInterceptor() {
        val interceptor = object :ContinuationInterceptor{
            override val key: CoroutineContext.Key<*> = ContinuationInterceptor

            override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
                println("intercept todo something. change run to thread")
                return object :Continuation<T> by continuation{
                    override fun resumeWith(result: Result<T>) {
                        println(" intercept create new thread")
                        thread {
                            continuation.resumeWith(result)
                        }
                    }
                }
            }

        }
        println(Thread.currentThread().name)
        mScope.launch (interceptor){
            println("intercept launch start. current thread: ${Thread.currentThread().name}")

            withContext(Dispatchers.Main){
                println("intercept new continuation todo something in the main thread. current thread: ${Thread.currentThread().name}")
            }

            launch {
                println("intercept new continuation todo something. current thread: ${Thread.currentThread().name}")
            }

            println("intercept launch end. current thread: ${Thread.currentThread().name}")
        }
    }

    /**
     * 三个上下文中的Job是同一个对象。
        第二个上下文在第一个的基础上增加了一个新的CoroutineName,新增的CoroutineName替换了第一个上下文中的CoroutineName。
        第三个上下文在第二个的基础上又增加了一个新的CoroutineName和Dispatchers,同时他们也替换了第二个上下文中的CoroutineName和Dispatchers。
     */
    /**
     * 2021-08-03 20:30:15.709 17867-17867/com.example.suspend D/TAG: [JobImpl{Active}@9e4df7c, CoroutineName(这是第一个上下文)]
        2021-08-03 20:30:15.709 17867-17867/com.example.suspend D/TAG: [JobImpl{Active}@9e4df7c, CoroutineName(这是第二个上下文), Dispatchers.Default]
        2021-08-03 20:30:15.709 17867-17867/com.example.suspend D/TAG: [JobImpl{Active}@9e4df7c, CoroutineName(这是第三个上下文), Dispatchers.Main]
     */
    private fun testCoroutineContext() {
        val coroutineContext1 = Job() + CoroutineName("这是第一个上下文")
        Log.d("TAG", "$coroutineContext1")
        val  coroutineContext2 = coroutineContext1 + Dispatchers.Default + CoroutineName("这是第二个上下文")
        Log.d("TAG", "$coroutineContext2")
        val coroutineContext3 = coroutineContext2 + Dispatchers.Main + CoroutineName("这是第三个上下文")
        Log.d("TAG", "$coroutineContext3")
    }

    private fun asyncTest1() {
        val runBlockingJob = runBlocking {
            Log.d("TAG", "runBlocking 启动一个协程")
        }
        Log.d("runBlockingJob", "$runBlockingJob")
        val launchJob = GlobalScope.launch{
            Log.d("TAG", "launchJob  启动一个协程")
        }
        Log.d("launchJob", "$launchJob")
        val asyncJob = GlobalScope.async{
            Log.d("TAG", "asyncJob  启动一个协程")
            "我是返回值"
        }
        Log.d("asyncJob", "$asyncJob")

    }


    fun asyncTest2() {
        mScope.async {
            var time:Long = System.currentTimeMillis()
            val job1 = async {
                // 请求1
                delay(5000)
                "1"
            }
            val job2 = async {
                // 请求2
                delay(5000)
                "2"
            }
            val job3 = async {
                // 请求3
                delay(5000)
                "3"
            }
            val job4 = async {
                // 请求4
                delay(5000)
                "4"
            }
            val job5 = async {
                // 请求5
                delay(5000)
                Log.d("TAG",(System.currentTimeMillis() - time) .toString())
                "5"
            }

            Log.d(
                    "TAG",
                    "asyncTest2: ${job1.await()} ${job2.await()} ${job3.await()} ${job4.await()} ${job5.await()}"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mScope.cancel()
    }

    fun main() {
        Log.e("zzf","launch start")
        GlobalScope.launch {
            launch {
                delay(200)
                Log.e("zzf","launch A")
            }
            launch {
                delay(100)
                Log.e("zzf","launch B")
            }
            Log.e("zzf","launch GlobalScope")
        }
        Log.e("zzf","launch end")
    }
}
