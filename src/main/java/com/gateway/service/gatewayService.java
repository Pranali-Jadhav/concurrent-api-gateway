package com.gateway.service;


import com.gateway.queue.DeadLetterQueue;
import com.gateway.queue.RequestQueue;
import com.gateway.worker.Worker;
import com.gateway.model.Request;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.gateway.handler.RequestResultHandler;


public class gatewayService implements RequestResultHandler{

    private final RequestQueue requestQueue;
    private final ExecutorService executorService;
    private final DeadLetterQueue deadLetterQueue;
    private static final int MAX_RETRY=3;

    public gatewayService(int workerCount)
    {
        this.requestQueue = new RequestQueue();
        this.executorService = Executors.newFixedThreadPool(workerCount);
        this.deadLetterQueue = new DeadLetterQueue();


        startWorkers(workerCount);
    }
    private void startWorkers(int workerCount)
    {
        for (int i=0;i<workerCount;i++)
        {
            executorService.submit(new Worker(requestQueue, this));
        }
    }
    public void submitRequest(Request request)
    {
        try
        {
            requestQueue.addRequest(request);
            System.out.println(
                    "Submitted Request ID: "
                            + request.getId()
            );
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();

            System.out.println(
                    "Request submission interrupted"
            );
        }
    }
    @Override
    public void onSuccess(Request r)
    {
        System.out.println(
                "Request "
                        + r.getId()
                        + " processed successfully"
        );
    }
    @Override
    public void onFailure(Request r)
    {
        try
        {
            if(r.getRetryCount()<MAX_RETRY)
            {
                r.incrementRetry();
                requestQueue.addRequest(r);
                System.out.println(
                        "Retrying Request "
                                + r.getId()
                                + " Attempt: "
                                + r.getRetryCount()
                );
            }
            else
            {
                System.out.println(
                        "Moving Request "
                                + r.getId()
                                + " to DLQ"
                );
                deadLetterQueue.addFailedRequest(r);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    public void shutdown()
    {
        executorService.shutdown();
        System.out.println("Gateway shutdown initiated");
    }
}
