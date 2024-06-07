package com.mentoria.update_card;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LambdaHandler implements RequestStreamHandler {

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(CardApplication.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        context.getLogger().log("Input: " + input);

        // Proxy the stream (this will internally call the Spring Boot application)
        handler.proxyStream(new java.io.ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), outputStream, context);

        outputStream.flush();
        String output = new String(((java.io.ByteArrayOutputStream) outputStream).toByteArray(), StandardCharsets.UTF_8);
        context.getLogger().log("Output: " + output);
    }
}