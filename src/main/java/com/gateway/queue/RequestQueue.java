package com.gateway.queue;
import com.gateway.model.Request;

import java.util.concurrent.PriorityBlockingQueue;

public class RequestQueue {

    private final PriorityBlockingQueue<Request> queue;

    public RequestQueue()
    {
        this.queue = new PriorityBlockingQueue<>();
    }
  //“Blocking methods like take() and put() can be interrupted while waiting, so they throw InterruptedException
  // to signal that the thread was interrupted during execution.”
    public void addRequest(Request r) throws InterruptedException
    {
        queue.put(r);
    }
    public Request getRequest() throws InterruptedException
    {
        return queue.take();
    }
    public int size() {
        return queue.size();
    }

}
