/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Effectenbeurs;
import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Example of RMI using Registry
 *
 * @author Max Meijer
 */
public class RMIClient extends Application {

    // Set binding name for the AEXBanner
    private static final String bindingName = "AEXBanner";


    private Registry registry = null;
    private IEffectenbeurs beurs = null;
    private boolean localRegistry = true;
    private AEXBanner banner;

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
            //printContentsRegistry();
        }

        // Bind Effectenbeurs using registry
        if (registry != null) {
            try {
                beurs = (IEffectenbeurs) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind Effectenbeurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                beurs = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind Effectenbeurs");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                beurs = null;
            }
        }

        // Print result binding Effectenbeurs
        if (beurs != null) {
            System.out.println("Client: Effectenbeurs bound");
        } else {
            System.out.println("Client: Effectenbeurs is null pointer");
        }

        banner = new AEXBanner(beurs);

        // Test RMI connection
        if (beurs != null) {
            testEffectenbeurs();
        }
    }

    public Registry localRegistry (String adress, int portNumber){
        return null;
    }

    // Print contents of registry
    public void printContentsRegistry() {
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
    public void testEffectenbeurs() throws RemoteException {
        // Get number of Koersen
        try {
            System.out.println("Current at: " + new java.util.Date() + " , " +  beurs.getKoersen());
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get beurs");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }

    }

    // Main method
    public static void main(String[] args) {
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

             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     RMIClient client = null;
                     try {
                         client = new RMIClient(ipAddress, portNumber);
                         client.banner.start(new Stage());
                     } catch (RemoteException e) {
                         e.printStackTrace();
                     }

                 }
             });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IEffectenbeurs bindBannerUsingRegistry(){
        return null;
    }

    public IEffectenbeurs bindBannerUsingNaming(String Adress, int portNumber){
        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
