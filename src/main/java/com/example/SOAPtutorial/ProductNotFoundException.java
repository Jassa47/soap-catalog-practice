package com.example.SOAPtutorial;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

// When thrown, Spring sends back a SOAP Fault with faultcode = Client
@SoapFault(faultCode = FaultCode.CLIENT)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String sku) {
        super("Product not found: " + sku);
    }
}