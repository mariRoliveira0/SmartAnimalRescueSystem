/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author marianarodriguesoliveira
 */

import generated.grpc.animalDetection.AnimalDetectionServiceGrpc;
import generated.grpc.animalDetection.AnimalReportRequest;
import generated.grpc.animalDetection.AnimalReportResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.jmdns.ServiceInfo;

public class AnimalClient {

    public static void main(String[] args) {

        // Discover the service
        ServiceInfo serviceInfo = ServiceDiscovery.discoverService("_animal._tcp.local.");

        if (serviceInfo == null) {
            System.out.println("Service not found!");
            return;
        }

        String host = serviceInfo.getHostAddresses()[0];
        int port = serviceInfo.getPort();

        System.out.println("Connecting to: " + host + ":" + port);

        // Create the channel
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        // Create the stub
        AnimalDetectionServiceGrpc.AnimalDetectionServiceBlockingStub stub =
                AnimalDetectionServiceGrpc.newBlockingStub(channel);

        // Send the request
        AnimalReportRequest request = AnimalReportRequest.newBuilder()
                .setAnimalId("A101")
                .setSpecies("Fox")
                .setLocation("Park")
                .setCondition("Injured")
                .build();

        AnimalReportResponse response = stub.reportAnimal(request);

        System.out.println("Response: " + response.getMessage());

        channel.shutdown();
    }
}
