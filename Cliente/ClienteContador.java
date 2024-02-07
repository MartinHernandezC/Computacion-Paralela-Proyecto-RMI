package Cliente;

import java.awt.Button;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import Interface.ContadorCliente;
import Interface.ContadorServidor;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public class ClienteContador extends JFrame{

    private ContadorServidor server;
    private ContadorCliente client;
    private String name;
    private JTextArea tA1=new JTextArea("");
    private JTextArea tA2=new JTextArea("");
    private JTextArea tA3=new JTextArea("");
    //private JTextArea tA4;
    private JButton boton;
    private JComboBox<String> cb;
    private JLabel lblTiempo, lblTexto, lblTextoMostr, lblConteo, lblPalabracont; JLabel numero1;
    private JTextField tA4, txtTexto1;
    private JTextArea txtTiempo=new JTextArea("");
    private JScrollPane scroll1,scroll2;
    
    public ClienteContador(ContadorServidor server, String name) throws RemoteException{
        super("Chat Client - " + name);
        this.server = server;
        this.name = name;
        this.client = new implementacionContadorCliente(name,tA1,tA2,tA3,txtTiempo);
        this.server.registro(client);
        
        //Call all the methods
        this.setupComponents(name);
        this.setupEvents();
    }
    
    private void setupComponents(String name) {
    this.setTitle("Proyecto 3er Parcial Computación Paralela" + name);
    setSize(450, 450);
    setLayout(null);

    String[] choices = {"Escoge","Secuencial","ExecutorService","ForkJoin"};

    this.cb = new JComboBox<String>(choices);
    this.cb.setBounds(20, 250, 150, 20);
    this.add(cb);
    
        //Ingreso de texto
        tA4 = new JTextField();
        tA4.setBounds(210,15,110,30);
        add(tA4);
        
        //Etiqueta de texto
        lblTexto = new JLabel("Escriba el texto/Palabra a contar:");
        lblTexto.setBounds(15,15,230,30);
        add(lblTexto);
    
        lblTextoMostr = new JLabel("Texto mostrado");
        lblTextoMostr.setBounds(15,110,150,30);
        add(lblTextoMostr);
        
        /*this.tA1 = new JTextArea();
        this.tA1.setEnabled(false);
        this.scroll1 = new JScrollPane(tA1);
        this.scroll1.setBounds(15,150,180,70);
        this.scroll1.setEnabled(false);
        this.add(scroll1);*/
        
        lblConteo = new JLabel("Palabra a contar");
        lblConteo.setBounds(215,110,150,30);
        add(lblConteo);
        
        /*this.tA2 = new JTextArea();
        this.tA2.setEnabled(false);
        this.scroll2 = new JScrollPane(tA2);
        this.scroll2.setBounds(215,150,180,70);
        this.scroll2.setEnabled(false);
        this.add(scroll2);*/
        
        lblTiempo = new JLabel("Duración:");
        lblTiempo.setBounds(15,350,150,30);
        add(lblTiempo);
        
        txtTiempo.setBounds(75,350,80,25);
        txtTiempo.disable();
        add(txtTiempo);     
        
        this.tA1.setBounds(15,150,180,70);
        this.add(tA1);
        this.tA1.disable();
        //this.tA2=new JTextArea("");
        this.tA2.setBounds(215,150,180,70);
        this.add(tA2);
        this.tA2.disable();
    
        JLabel numero1;
        numero1 = new JLabel("=");
        numero1.setBounds(170,500,20,20);
        this.add(numero1);
        //this.tA3=new JTextArea("");
    
        JLabel veces;
        veces = new JLabel("Conteo");
        veces.setBounds(215,250,100,20);
        this.add(veces);
        this.tA3.setBounds(215,280,150,30);
        this.tA3.disable();
        this.add(tA3);
    
        //this.tA4=new JTextArea("");
        //this.tA4.setBounds(20,580,50,20);
        //this.add(tA4);
    
        this.boton = new JButton("Enviar");
        this.boton.setBounds(210,50,110,30);
        this.add(boton);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    private void setupEvents() {
        
        this.boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=tA4.getText();
                if(cb.getSelectedItem().equals("Escoge") || s.equals("")){
                    System.out.println("Ingresa los datos");
                }else{
                    
                    try {
                        server.addWord(s,cb.getSelectedItem().toString());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClienteContador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }
        });
    }
    
    public static void main(String[] args) {
        try {
            
            String nombre = JOptionPane.showInputDialog("Ingresa tu nombre");
            
            Registry rmii = LocateRegistry.getRegistry("localhost", 1011);//192.168.56.1
            ContadorServidor servidor= (ContadorServidor) rmii.lookup("Chat");
            
            ClienteContador calculadora = new ClienteContador(servidor, nombre);
            calculadora.setVisible(true);
             
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
