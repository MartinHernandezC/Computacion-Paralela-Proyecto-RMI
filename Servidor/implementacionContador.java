package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import Interface.ContadorCliente;
import Interface.ContadorServidor;
import javax.swing.JTextArea;


public class implementacionContador extends UnicastRemoteObject implements ContadorServidor{
    
    public ArrayList<ContadorCliente> clientes=new ArrayList<ContadorCliente>();;
    public ArrayList<String> num=new ArrayList<String>();
    public ArrayList<String> op=new ArrayList<String>();
    public Random rand = new Random(System.nanoTime());
    public Runnable mS;
    public JTextArea txtTiempo;
    
    public implementacionContador() throws RemoteException{
        
    }
    
    @Override
    public void registro(ContadorCliente cliente) throws RemoteException {
        if(clientes.size()==2){
            System.out.println("No se permiten mas clientes");
        }else{
            this.clientes.add(cliente);
            System.out.println("conexion creada"+this.clientes);
        }
        
    }

     @Override
    public void addWord(String n,String o) throws RemoteException{
        int i=num.size(),a=0,res=0;

        if(i==0){
            num.add(n);
            op.add(o);
                while(a<clientes.size()){
                    clientes.get(a++).addWord(n, 1);
                }
        }else{
            int b=0;
            double ti,tf;
            String s=num.get(0);
            if(op.get(0).equals(o)){
                //res=num.get(0)+n;
                ti=System.nanoTime();
                switch(op.get(0)){                  
                    case "Secuencial":
                        res=countSubstring(num.get(0),n);
                        break;
                    case "ExecutorService":
                        res=exConteo(res,num.get(0),n);
                        break;
                    case "ForkJoin":
                        res=forkJoinConteo(res,num.get(0),n);
                        break;
                }
                tf=(System.nanoTime()-ti)/100000000;
                /*Imprimir tiempo*/                                                                                                                                                                         
                System.out.println(String.valueOf(tf));
                
                while(a<clientes.size()){
                    System.out.println(a +" "+clientes.size());
                    clientes.get(a).addWord(n, 2);
                    clientes.get(a).getResult(res,tf);
                    a++;
                }
                num.remove(0);
                op.remove(0);
                
                
            }else{
                while(a<clientes.size()){
                    clientes.get(a++).mensajeCliente("Los clientes no ingresaron la misma operacion");
                }
            }
            
        }
        
    }    
    
    int countSubstring(String str1,String str2)
    {
        int n1 = str1.length();
        int n2 = str2.length();
 
        // Caso Base
        if (n1 == 0 || n1 < n2)
        return 0;
 
        // Caso recursivo Verificando si la primera substring corresponde al texto de String1
        if (str1.substring(0, n2).equals(str2))
        return countSubstring(str1.substring(1),str2) + 1;
 
        // De otra manera, retorna el conteo
        return countSubstring(str1.substring(1),str2);
    }
    

    
    
    public int exConteo(int x,String s1,String s2){
        int[] m={x};
        ExecutorService executor = Executors.newFixedThreadPool(8); 
            for(int i=0;i<8;i++){
                mS = new ExSubstringCount(m,s1,s2);
            }
                executor.execute(mS);
                executor.shutdown();
                while(!executor.isTerminated()); 
        return m[0];
    }
    
    public class ExSubstringCount implements Runnable{

        private String s1;
        private String s2;
        private int[] x;
  public ExSubstringCount(int[] x,String str1,String str2) {
      this.s1=str1;
    this.s2=str2;
    this.x=x; 
  }
  

        @Override
        public void run() {
            excountSubstring(s1,s2);
        }
    void excountSubstring(String str1,String str2)
    {
    int n1 = str1.length();
    int n2 = str2.length();
    //System.out.println(n1);
    if (n1 == 0 || n1 < n2){
    return;
    } else if (str1.substring(0, n2).equals(str2)){
        x[0]+= 1;
    }
        excountSubstring(str1.substring(1),str2);
    }
        
    }
    
    
   public static int forkJoinConteo(int x,String s1,String s2){
        int[] m={x};
        ForkJoinConteo task = new ForkJoinConteo(m, s1,s2);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        return m[0];
    }
    
    
    public static class ForkJoinConteo extends RecursiveAction{
        private final String s1;
        private final String s2;
        private static int[] x;
  
    //Iniciar hilos en ForkJoin    
    public ForkJoinConteo(int[] x,String str1,String str2) {
        this.s1=str1;
        this.s2=str2;
        this.x=x;
    }

    //Algoritmo para el conteo de las substring en forkjoin
    @Override
    protected void compute() {
    int n1 = s1.length();
    int n2 = s2.length();
    int y=1;
    
    if (n1 == 0 || n1 < n2){
        return;
    }else if (s1.substring(0, n2).equals(s2)){
        x[0]+= 1;
    }  
    invokeAll(new ForkJoinConteo(x, s1.substring(1),s2)); 
    }
    }
    
    
    
}
