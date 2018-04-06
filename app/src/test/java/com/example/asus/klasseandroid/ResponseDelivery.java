package com.example.asus.klasseandroid;

/**
 * Created by 1001737 on 4/4/18.
 */

import com.android.volley.ExecutorDelivery;

import java.util.concurrent.Executor;


// response delivery class that inputs for Volley integration test
    // helps mock reponse delivery without main thread

public class ResponseDelivery extends ExecutorDelivery {

    public ResponseDelivery() {
        super(new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
    }
}
