package com.gateway.model;
import java.util.concurrent.atomic.AtomicInteger;

public class Request implements Comparable<Request> {

    private final int id;
    private final String payload;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private Status status;
    private int retryCount;
    private final int priority;


    public Request(int priority, String payload)
    {
        this.id = counter.incrementAndGet();
        this.priority = priority;
        this.payload = payload;
        this.status = Status.PENDING;
        this.retryCount=0;
    }

    public String getPayload()
    {
        return payload;
    }
    public int getId()
    {
        return id;
    }
    public int getRetryCount()
    {
        return retryCount;
    }
    public int getPriority()
    {
        return priority;
    }
    public Status getStatus()
    {
        return status;
    }
    public void markProcessing()
    {
        this.status=Status.PROCESSING;
    }
    public void markSuccess()
    {
        this.status=Status.SUCCESS;
    }
    public void markFailure()
    {
        this.status=Status.FAILED;
    }

    public int compareTo(Request other)
    {
        return Integer.compare(other.priority, this.priority);
    }
    public String toString() {
        return "Request{id=" + id +
                ", priority=" + priority +
                ", status=" + status +
                ", retries=" + retryCount + "}";
    }

    public void incrementRetry() {
        this.retryCount++;
    }
}
