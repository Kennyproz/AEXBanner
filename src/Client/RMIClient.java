/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Effectenbeurs;
import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;
import Shared.IFonds;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
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
    private ClientCommunicator communicator;

    // Constructor
    public RMIClient() throws RemoteException {

        communicator = new ClientCommunicator(this);
        communicator.connectToServer("localhost", "AEXBanner");
        communicator.register("UpdateFonds");
        communicator.subscribe("UpdateFonds");

        beurs = null;
        banner = new AEXBanner();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    banner.start(new Stage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void updateKoersen(List<IFonds> fonds) {
        banner.setKoersen(fonds.toString());
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
        try {
            RMIClient client = new RMIClient();
        } catch (RemoteException e) {
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
