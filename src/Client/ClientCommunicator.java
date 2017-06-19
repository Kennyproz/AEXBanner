package Client;

import Shared.IFonds;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by Max on 24-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * Handles communication at Game room level.
 * Code has been copied and modified from our own use from: https://git.fhict.nl/I889854/FontysPublisherWhiteboard
 *
 */
@SuppressWarnings("ALL")
public class ClientCommunicator
        extends UnicastRemoteObject
        implements IRemotePropertyListener {
    private static final Logger LOGGER = Logger.getLogger(ClientCommunicator.class.getName());

    // Remote publisher
    private IRemotePublisherForDomain publisherForDomain;
    private IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private String bindingName = "";
    private String ipAdress = "";
    private boolean connected = false;
    RMIClient client;

    // Thread pool
    private final int nrThreads = 20;
    private ExecutorService threadPool = null;

    public ClientCommunicator(RMIClient client) throws RemoteException {
        threadPool = Executors.newFixedThreadPool(nrThreads);
        this.client = client;
    }

    /**
     * Connect to a server via a binding name.
     * Note that the connection is currently hardcode to localhost.
     * @param bindingName The name of the server to connect to.
     */
    public void connectToServer(String ip ,String bindingName) {
        try {
            this.bindingName = bindingName;
            this.ipAdress = ip;
            //System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost()));
            //System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
            Registry registry = LocateRegistry.getRegistry(ip, portNumber);
            publisherForDomain = (IRemotePublisherForDomain) registry.lookup(bindingName);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            connected = true;
            System.out.println("Connection with server: " + bindingName + " has been established");
        } catch ( Exception re) {
            connected = false;
            System.out.println("Connection could not be established see log files for more information.");
            LOGGER.severe(String.valueOf(re));
        }
    }

    /**
     * Finds all servers on a address.
     *
     * @return An array of registry bindingnames found on the specific address
     */
    public String[] findServers(String ip) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip);
            String[] registries = registry.list();
            for (String s : registries) {
                System.out.println(s);
            }
            return registries;
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        } catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }
    /**
     * Register property at remote publisher.
     * @param property  property to be registered
     */
    public void register(String property) {
        if (connected) {
            try {
                // System.out.println("Registering property: " +  property);
                // Nothing changes at remote publisher in case property was already registered
                publisherForDomain.registerProperty(property);
            } catch (RemoteException ex) {
            }
        }
    }

    /**
     * Subscribe to property.
     * @param property property to subscribe to
     */
    public void subscribe(String property) {
        if (connected) {
            final IRemotePropertyListener listener = this;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("Subscribing to property: " + property);
                        publisherForListener.subscribeRemoteListener(listener, property);
                    } catch (RemoteException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                }
            });
        }
    }

    public void broadcast(String property, Object newValue) {
        if (connected) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("Broadcasting: " + property + " at value: " + newValue.toString());
                        publisherForDomain.inform(property,null, newValue);
                    } catch (RemoteException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                }
            });
        }
    }

    // Don't forget the break statements you twat.
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        // System.out.println("Property: " + evt.getPropertyName() + " has changed to: " + evt.getNewValue());
        switch(evt.getPropertyName()) {
            case "UpdateFonds":
                client.updateKoersen((List<IFonds>) evt.getNewValue());
                break;

        }
    }

    public String getServerName() {
        return bindingName;
    }

    public String getIpAdress() {
        return ipAdress;
    }
}
