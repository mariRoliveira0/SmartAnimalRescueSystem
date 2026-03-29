/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vet;

/**
 *
 * @author marianarodriguesoliveira
 */

import io.grpc.Server;
import io.grpc.ServerBuilder;
import naming.ServiceRegistrar;

public class VetServer {

    public static void main(String[] args) throws Exception {

        int port = 50053;

        Server server = ServerBuilder.forPort(port)
                .addService(new VeterinaryServiceImpl())
                .build()
                .start();

        ServiceRegistrar.registerService("_vet._tcp.local.", "VetService", port);

        System.out.println("Veterinary Server started on port " + port);

        server.awaitTermination();
    }
}
