package Servidor;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class ServerContador {
   
    public static void main(String[] args) {
        try {
            
            System.setProperty("java.rmi.server.hostname","10.0.0.18");
            Registry rmi= LocateRegistry.createRegistry(1011);
            rmi.rebind("Chat",(Remote) new implementacionContador());
            System.out.println("Servidor Activo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
