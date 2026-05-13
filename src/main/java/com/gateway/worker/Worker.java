package com.gateway.worker;

import com.gateway.handler.RequestResultHandler;
import com.gateway.model.Request;
import com.gateway.queue.RequestQueue;

import java.util.Random;

public class Worker implements Runnable{

    private final RequestQueue requestQueue;
    private final Random random;
    private final RequestResultHandler requestResultHandler;

    public Worker(RequestQueue requestQueue, RequestResultHandler requestResultHandler)
    {
        this.requestQueue=requestQueue;
        this.random=new Random();
        this.requestResultHandler=requestResultHandler;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                Request request = requestQueue.getRequest();
                request.markProcessing();
                requestResultHandler.onSuccess(request);

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
                    requestResultHandler.onFailure(request);
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
