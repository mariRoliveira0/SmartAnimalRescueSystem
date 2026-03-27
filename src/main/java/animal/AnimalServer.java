/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animal;

/**
 *
 * @author marianarodriguesoliveira
 */
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class AnimalServer {

    public static void main(String[] args) throws Exception {

        int port = 50051;

        Server server = ServerBuilder.forPort(port)
                .addService(new AnimalDetectionServiceImpl())
                .build()
                .start();

        System.out.println("Animal Server started on port " + port);

        server.awaitTermination();
    }
}