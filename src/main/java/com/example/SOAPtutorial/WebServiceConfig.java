package com.example.SOAPtutorial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class WebServiceConfig {

    // Loads our contract file from src/main/resources/xsd/
    @Bean
    public XsdSchema catalogSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/catalog.xsd"));
    }

    // Builds a WSDL from the XSD.
    // The bean name "catalog" is what makes the URL /ws/catalog.wsdl
    @Bean(name = "catalog")
    public DefaultWsdl11Definition catalogWsdl(XsdSchema catalogSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("CatalogPort");
        wsdl.setLocationUri("http://localhost:8080/ws");
        wsdl.setTargetNamespace("http://example.com/catalog");
        wsdl.setSchema(catalogSchema);
        return wsdl;
    }
}