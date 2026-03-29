/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescue;

/**
 *
 * @author marianarodriguesoliveira
 */
import generated.grpc.rescueService.*;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

public class RescueServiceImpl extends RescueServiceGrpc.RescueServiceImplBase {

    private Map<String, String> rescueStatusMap = new HashMap<>();

    // Unary RPC
    @Override
    public void requestRescue(RescueRequest request,
            StreamObserver<RescueResponse> responseObserver) {

        String rescueId = "R" + System.currentTimeMillis();

        rescueStatusMap.put(rescueId, "Requested");

        RescueResponse response = RescueResponse.newBuilder()
                .setRescueId(rescueId)
                .setStatus("Team Assigned")
                .build();
        if (request.getAnimalId().isEmpty()) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Animal ID is required")
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //Server Streaming
    @Override
    public void streamRescueStatus(EmptyRequest request,
            StreamObserver<RescueStatus> responseObserver) {

        for (Map.Entry<String, String> entry : rescueStatusMap.entrySet()) {

            RescueStatus status = RescueStatus.newBuilder()
                    .setRescueId(entry.getKey())
                    .setStatus(entry.getValue())
                    .build();

            responseObserver.onNext(status);
        }

        responseObserver.onCompleted();
    }

    //  Bidirectional Streaming 
    @Override
    public StreamObserver<RescueMessage> liveRescueChat(
            StreamObserver<RescueMessage> responseObserver) {

        return new StreamObserver<RescueMessage>() {

            @Override
            public void onNext(RescueMessage request) {

                System.out.println(request.getSender() + ": " + request.getMessage());

                // back response
                RescueMessage response = RescueMessage.newBuilder()
                        .setSender("ControlCenter")
                        .setMessage("Received: " + request.getMessage())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
