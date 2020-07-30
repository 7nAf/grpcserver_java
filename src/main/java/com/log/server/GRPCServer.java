package com.log.server;

import com.log.server.GreetingServiceGrpc;
import com.log.server.HelloRequest;
import com.log.server.HelloResponse;
import com.log.server.HelloResponseOrBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GRPCServer {
    static public void main(String [] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new GreetingServiceImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }

    public static class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
        @Override
        public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            System.out.println(request);

            String greeting = "Hello there, " + request.getName();

            HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
