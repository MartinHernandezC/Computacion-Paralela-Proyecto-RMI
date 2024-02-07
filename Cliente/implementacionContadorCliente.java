package Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JTextArea;
import java.io.File;
import Interface.ContadorCliente;
import Interface.ContadorServidor;



public class implementacionContadorCliente extends UnicastRemoteObject implements ContadorCliente{
    
    
    public String nombre = null;
    private JTextArea tA1;
    private JTextArea tA2;
    private JTextArea tA3;
    private JTextArea txtTiempo;
    
    public implementacionContadorCliente(String nombre,JTextArea tA1,JTextArea tA2,JTextArea tA3, JTextArea txtTiempo) throws RemoteException{
        this.nombre=nombre;
        this.tA1=tA1;
        this.tA2=tA2;
        this.tA3=tA3;
        this.txtTiempo=txtTiempo;
    }
    
    @Override
    public void addWord(String n,int con){
        if(con==1){
            this.tA1.setText("");
            this.tA2.setText("");
            this.tA3.setText("");   
            this.tA1.setText(n);
        }else{
            this.tA2.setText(n);
        }
    }
     
   @Override
    public void getResult(double res, double tiempo){
        tA3.setText(String.valueOf(res));
        txtTiempo.setText(String.valueOf(tiempo));
    }
    
    
    
    @Override
    public void mensajeCliente(String mensaje) throws RemoteException {
        System.err.println(mensaje);
    }
    
}
