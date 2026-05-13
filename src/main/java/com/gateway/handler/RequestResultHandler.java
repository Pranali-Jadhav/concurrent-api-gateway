package com.gateway.handler;

import com.gateway.model.Request;

public interface RequestResultHandler {

    void onSuccess(Request request);

    void onFailure(Request request);
}
