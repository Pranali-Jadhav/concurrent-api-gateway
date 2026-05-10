package com.gateway;
import com.gateway.model.Request;
import com.gateway.service.gatewayService;

public class main {

    public static void main(String[] args)
    {
        gatewayService gatewayService = new gatewayService(3);
        gatewayService.submitRequest(new Request(1, "Normal Request"));
        gatewayService.submitRequest(
                new Request(5, "High Priority Payment")
        );

        gatewayService.submitRequest(
                new Request(3, "Medium Priority Report")
        );

        gatewayService.submitRequest(
                new Request(10, "Critical Admin Request")
        );

        gatewayService.submitRequest(
                new Request(2, "Analytics Request")
        );

    }
}
