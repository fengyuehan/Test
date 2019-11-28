package com.example.produceandconsum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.produceandconsum.Lock.BlockedQueue;
import com.example.produceandconsum.Lock.Consumer;
import com.example.produceandconsum.Lock.Producer;

public class MainActivity extends AppCompatActivity {

    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();
            }
        });
    }

    private void data(){
        BlockedQueue<String> queue = new BlockedQueue<>(10);
        for (int i = 0; i < 3; i++){
            String producerName = "producer" + i;
            Thread producer = new Thread(new Producer<String>(queue) {
                @Override
                protected String[] generateTask() {
                    String[] tasks = new String[20];
                    for (int j = 0;j < tasks.length;j++){
                        long timestamp = System.currentTimeMillis();
                        tasks[j] = "Task_" + timestamp + "_" + j;
                    }
                    return tasks;
                }
            },producerName);
            producer.start();
        }

        for (int i = 0; i < 5; i++) {
            String consumerName = "Consumer-" + i;
            Thread consumer = new Thread(new Consumer<String>(queue) {
                @Override
                public void exec(String task) {
                    System.out.println(Thread.currentThread().getName() + " do task [" + task + "]");
                    //休眠一会，模拟任务执行耗时
                    sleep(2000);
                }

                private void sleep(long millis) {
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, consumerName);
            consumer.start();
        }
    }
}
