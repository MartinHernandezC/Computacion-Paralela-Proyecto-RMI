package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface ContadorServidor extends Remote {
    void registro(ContadorCliente cliente) throws RemoteException;
    void addWord(String n,String o) throws RemoteException;
//void mensaje (String mensaje) throws RemoteException;
}
