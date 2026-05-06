package com.gateway.worker;

import com.gateway.model.Request;
import com.gateway.queue.RequestQueue;

import java.util.Random;

public class Worker implements Runnable{

    private final RequestQueue requestQueue;
    private final Random random;

    public Worker(RequestQueue requestQueue)
    {
        this.requestQueue=requestQueue;
        this.random=new Random();
    }

    public void run()
    {
        while (true)
        {
            try
            {
                Request request = requestQueue.getRequest();
                request.markProcessing();

                System.out.println(
                        Thread.currentThread().getName()
                                + " processing Request ID: "
                                + request.getId()
                );

                Thread.sleep(1000);

                boolean success = random.nextBoolean();
                if(success)
                {
                    request.markSuccess();
                    System.out.println(
                            "Request "
                                    + request.getId()
                                    + " completed successfully"
                    );
                }
                else
                {
                    request.markFailure();
                    System.out.println(
                            "Request "
                                    + request.getId()
                                    + " failed"
                    );
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                System.out.println("Worker interrupted");
                break;
            }
        }
    }
}
