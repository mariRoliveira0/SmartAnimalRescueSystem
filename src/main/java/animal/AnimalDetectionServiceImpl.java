/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animal;

/**
 *
 * @author marianarodriguesoliveira
 */

import io.grpc.stub.StreamObserver;
import generated.grpc.animalDetection.AnimalDetectionServiceGrpc;
import generated.grpc.animalDetection.AnimalReportRequest;
import generated.grpc.animalDetection.AnimalReportResponse;
import generated.grpc.animalDetection.AnimalInfo;
import generated.grpc.animalDetection.EmptyRequest;
import generated.grpc.animalDetection.UploadSummary;
import java.util.ArrayList;
import java.util.List;
import generated.grpc.animalDetection.AnimalDetectionServiceGrpc;
import generated.grpc.animalDetection.AnimalReportRequest;
import generated.grpc.animalDetection.AnimalReportResponse;
import generated.grpc.animalDetection.AnimalInfo;
import generated.grpc.animalDetection.EmptyRequest;
import generated.grpc.animalDetection.UploadSummary;

public class AnimalDetectionServiceImpl extends AnimalDetectionServiceGrpc.AnimalDetectionServiceImplBase {

    private List<AnimalInfo> animals = new ArrayList<>();

    @Override
    public void reportAnimal(AnimalReportRequest request,
                             StreamObserver<AnimalReportResponse> responseObserver) {

        AnimalInfo animal = AnimalInfo.newBuilder()
                .setAnimalId(request.getAnimalId())
                .setSpecies(request.getSpecies())
                .setLocation(request.getLocation())
                .setCondition(request.getCondition())
                .build();

        animals.add(animal);

        AnimalReportResponse response = AnimalReportResponse.newBuilder()
                .setMessage("Animal reported successfully")
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
