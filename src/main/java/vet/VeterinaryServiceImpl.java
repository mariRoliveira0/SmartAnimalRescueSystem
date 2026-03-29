/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vet;

/**
 *
 * @author marianarodriguesoliveira
 */
import generated.grpc.vetservice.*;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class VeterinaryServiceImpl extends VeterinaryServiceGrpc.VeterinaryServiceImplBase {

    private Map<String, List<TreatmentInfo>> treatmentMap = new HashMap<>();

    // Unary RPC
    @Override
    public void addTreatment(TreatmentRequest request,
                             StreamObserver<TreatmentResponse> responseObserver) {

        TreatmentInfo treatment = TreatmentInfo.newBuilder()
                .setTreatment(request.getTreatment())
                .setVetName(request.getVetName())
                .build();

        treatmentMap.putIfAbsent(request.getAnimalId(), new ArrayList<>());
        treatmentMap.get(request.getAnimalId()).add(treatment);

        TreatmentResponse response = TreatmentResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Treatment recorded")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //  Server Streaming
@Override
public void streamTreatments(AnimalRequest request,
                             StreamObserver<TreatmentInfo> responseObserver) {

    List<TreatmentInfo> treatments = treatmentMap.get(request.getAnimalId());

    if (treatments == null) {
        responseObserver.onError(
                io.grpc.Status.NOT_FOUND
                        .withDescription("No treatments found for this animal")
                        .asRuntimeException()
        );
        return;
    }

    for (TreatmentInfo t : treatments) {
        responseObserver.onNext(t);
    }

    responseObserver.onCompleted();
}
}
