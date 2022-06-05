package com.example.grpc.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

public class HelloWorldClient {
    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        GreeterGrpc.GreeterBlockingStub stub =
                GreeterGrpc.newBlockingStub(channel);

        HelloReply reply = stub.sayHello(HelloRequest.newBuilder()
                .setName("World")
                .build()
        );

        logger.info("response: " + reply.getMessage());
        HelloReply replyAgain = stub.sayHelloAgain(HelloRequest.newBuilder()
                .setName("New Again!")
                .build()
        );
        logger.info("response: " + replyAgain.getMessage());
        channel.shutdown();
    }
}
