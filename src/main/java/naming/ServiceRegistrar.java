/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naming;

/**
 *
 * @author marianarodriguesoliveira
 */

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

public class ServiceRegistrar {

    public static void registerService(String serviceType, String serviceName, int port) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            ServiceInfo serviceInfo = ServiceInfo.create(
                    serviceType,   // e.g. _animal._tcp.local.
                    serviceName,   // e.g. AnimalService
                    port,
                    "Animal Detection Service"
            );

            jmdns.registerService(serviceInfo);

            System.out.println(serviceName + " registered on port " + port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
