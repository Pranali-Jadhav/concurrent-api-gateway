package com.gateway.service;


import com.gateway.queue.RequestQueue;
import com.gateway.worker.Worker;
import com.gateway.model.Request;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class gatewayService {

    private final RequestQueue requestQueue;
    private final ExecutorService executorService;

    public gatewayService(int workerCount)
    {
        this.requestQueue = new RequestQueue();
        this.executorService = Executors.newFixedThreadPool(workerCount);

        startWorkers(workerCount);
    }
    private void startWorkers(int workerCount)
    {
        for (int i=0;i<workerCount;i++)
        {
            executorService.submit(new Worker(requestQueue));
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
    public void shutdown()
    {
        executorService.shutdown();
        System.out.println("Gateway shutdown initiated");
    }
}
