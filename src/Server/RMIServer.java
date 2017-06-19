/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.ClientCommunicator;
import fontyspublisher.RemotePublisher;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Example of RMI using Registry
 *
 * @author Max Meijer
 */
public class RMIServer {

    // Set port number
    private static final int portNumber = 1099;

    // Set binding name for student administration
    private static final String bindingName = "AEXBanner";

    // References to registry and student administration
    private Registry registry = null;
    private MockEffectenbeurs beurs = null;
    private boolean createRegistry = true;
    ClientCommunicator communicator;
    RemotePublisher remotePublisher = null;


    // Constructor
    public RMIServer() {



        // Create an instance of RemotePublisher
        RemotePublisher remotePublisher = null;
        try {
            remotePublisher = new RemotePublisher();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            //System.setProperty("java.rmi.server.hostname", "localhost");
            registry.rebind("AEXBanner", remotePublisher);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Remote publisher registered
        System.out.println("Server started");
        System.out.println("Port number  : " + portNumber);
        System.out.println("Binding name : " + "AEXBanner");

        try {
            beurs = new MockEffectenbeurs();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Print IP addresses and network interfaces
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        // Print IP addresses and network interfaces
        printIPAddresses();

        // Create server
        RMIServer server = new RMIServer();
    }
}
