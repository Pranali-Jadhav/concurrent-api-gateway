package com.gateway.queue;

import com.gateway.model.Request;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DeadLetterQueue {

    private final BlockingQueue<Request> deadLetterQueue;

    public DeadLetterQueue()
    {
        this.deadLetterQueue = new LinkedBlockingQueue<>();
    }

    public void addFailedRequest(Request request) throws InterruptedException
    {
        deadLetterQueue.put(request);
    }
    public List<Request> getAllFailedRequest()
    {
        return new ArrayList<>(deadLetterQueue);
    }
}
