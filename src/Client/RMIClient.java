/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.IEffectenbeurs;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Example of RMI using Registry
 *
 * @author Nico Kuijpers
 */
public class RMIClient {

    // Set binding name for the AEXBanner
    private static final String bindingName = "AEXBanner";


    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;
    private boolean localRegistry = true;

    // Constructor
    public RMIClient(String ipAddress, int portNumber) throws RemoteException {

        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

        // Print contents of registry
        if (registry != null) {
            printContentsRegistry();
        }

        // Bind Effectenbeurs using registry
        if (registry != null) {
            try {
                effectenbeurs = (IEffectenbeurs) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind Effectenbeurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                effectenbeurs = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind Effectenbeurs");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                effectenbeurs = null;
            }
        }

        // Print result binding Effectenbeurs
        if (effectenbeurs != null) {
            System.out.println("Client: Effectenbeurs bound");
        } else {
            System.out.println("Client: Effectenbeurs is null pointer");
        }

        // Test RMI connection
        if (effectenbeurs != null) {
            testEffectenbeurs();
        }
    }

    private Registry localRegistry (String adress, int portNumber){
        return null;
    }

    // Print contents of registry
    private void printContentsRegistry() {
        try {
            String[] listOfNames = registry.list();
            System.out.println("Client: list of names bound in registry:");
            if (listOfNames.length != 0) {
                for (String s : registry.list()) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Client: list of names bound in registry is empty");
            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot show list of names bound in registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }

    // Test RMI connection
    private void testEffectenbeurs() throws RemoteException {
        // Get number of Koersen
      /*  try {
            System.out.println("Client: Number of students: " + effectenbeurs.getKoersen());
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get number of students");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }

        // Get number of Koersen
        try {
            System.out.println("Client: Number of students: " + effectenbeurs.getKoersen());
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get number of students");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }

        // Get first Koers
        try {
            System.out.println("Client: First student: " + effectenbeurs.getKoersen(0));
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get first student");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }

        // Get second Koers
        try {
            System.out.println("Client: Second student: " + effectenbeurs.getKoersen(1));
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get second student");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }

        // Get third Koers (does not exist)
        try {
            System.out.println("Client: Third student: " + effectenbeurs.getKoersen(2));
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get third student");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }*/
    }

    // Main method
    public static void main(String[] args) {

        // Welcome message
        System.out.println("CLIENT USING REGISTRY");

        // Get ip address of server
        Scanner input = new Scanner(System.in);
        System.out.print("Client: Enter IP address of server: ");
        String ipAddress = input.nextLine();

        // Get port number
        System.out.print("Client: Enter port number: ");
        int portNumber = input.nextInt();

        // Create client
        try {
            RMIClient client = new RMIClient(ipAddress, portNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IEffectenbeurs bindBannerUsingRegistry(){
        return null;
    }

    private IEffectenbeurs bindBannerUsingNaming(String Adress, int portNumber){
        return null;
    }

}
