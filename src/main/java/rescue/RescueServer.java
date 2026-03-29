/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescue;

/**
 *
 * @author marianarodriguesoliveira
 */


import io.grpc.Server;
import io.grpc.ServerBuilder;
import naming.ServiceRegistrar;

public class RescueServer {

    public static void main(String[] args) throws Exception {

        int port = 50052;

        Server server = ServerBuilder.forPort(port)
                .addService(new RescueServiceImpl())
                .build()
                .start();

        ServiceRegistrar.registerService("_rescue._tcp.local.", "RescueService", port);

        System.out.println("Rescue Server started on port " + port);

        server.awaitTermination();
    }
}
