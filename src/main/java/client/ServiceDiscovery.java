/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author marianarodriguesoliveira
 */
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

public class ServiceDiscovery {

    public static ServiceInfo discoverService(String serviceType) {

        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            System.out.println("Searching for service...");

            Thread.sleep(2000); // 🔥 IMPORTANT (wait for network)

            ServiceInfo[] services = jmdns.list(serviceType);

            for (ServiceInfo service : services) {
                System.out.println("Found: " + service.getName());
                return service;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
