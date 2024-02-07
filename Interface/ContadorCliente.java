package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface ContadorCliente extends Remote{
    void mensajeCliente(String mensaje) throws RemoteException;
    void addWord(String n,int con) throws RemoteException;
    void getResult(double res, double tiempo) throws RemoteException;
}
