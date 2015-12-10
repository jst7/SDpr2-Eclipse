package rmistation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable; 
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.axis2.databinding.utils.SimpleArrayReaderStateMachine;
import org.omg.CORBA.Environment;



public class RMIStation {     
    
    private String temperatura="1";
    private String humedad="2";
    private String luminosidad="3";
    private String pantalla="4";
    private String user;
    private String key="pistola";
    
	public RMIStation () {
		String uname = System.getProperty("user.name");
		
        leerArchivo("C:\\Users\\"+uname+"\\Desktop\\station1.txt");		
	}
        
        public String GetTemperatura(){
        
        	escribirlog("Peticion temperatura: "+temperatura);
        	encripta a=new encripta();
        	String temp="";
        	try{
        	 temp=a.encrypt(temperatura, key);
        	}
        	catch(Exception e){}
        	
            return temp;
        }
        
        public String GetHumedad(){
        	escribirlog("Peticion humedad: "+humedad);
        	encripta a=new encripta();
        	String hum="";
        	try{
        	 hum=a.encrypt(humedad, key);
        	}
        	catch(Exception e){}
        	
            return hum;
        }
        
        public String Getluminosidad() {
        	escribirlog("Peticion luminosidad: "+luminosidad);
        	encripta a=new encripta();
        	String lum="";
        	try{
        	 lum=a.encrypt(luminosidad, key);
        	}
        	catch(Exception e){}
        	
            return lum;
        }
        public  String GetPantalla(){
        	escribirlog("Peticion pantalla: "+pantalla);
        	encripta a=new encripta();
        	String pan="";
        	try{
        	 pan=a.encrypt(pantalla, key);
        	}
        	catch(Exception e){}
        	
            return pan;
        }
        public void SetPantalla(String p){
        	encripta a=new encripta();
        	
        	try{
        	pantalla=a.decrypt(p, key);
        	}catch(Exception e){}
        	escribirpantalla(pantalla);
            escribirlog("Escritura pantalla: "+pantalla);
        }
        
        public void SetUsuario (String p){
        	encripta a=new encripta();
        	
        	try{
        	user=a.decrypt(p, key);
        	}catch(Exception e){}
        	escribirpantalla(user);
            escribirlog("Inicio de Sesion: "+user+"---------------------------------------");
        }
        
        public String leerArchivo(String s) {
        File fich = new File(s);
        String respuesta = "";

        System.out.println(fich.getAbsolutePath());//ruta absoluta
        if (fich.exists()) {
            try {
                FileReader fr = new FileReader(fich);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    
                    if(linea.contains("temperatura")){
                        temperatura=linea.split("=")[1];
                    }
                    else if(linea.contains("humedad")){
                        humedad=linea.split("=")[1];
                    }
                    else if(linea.contains("luminosidad")){
                        luminosidad=linea.split("=")[1];
                    }
                    else if(linea.contains("pantalla")){
                        pantalla=linea.split("=")[1];
                    }
                    
                    respuesta += linea;
                }

                br.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            respuesta = "404 - Archivo no encontrado";
        }
        

        return respuesta;
    }
        public void escribirpantalla(String s){
        	
       	 FileWriter fichero = null;
            PrintWriter pw = null;
            String uname = System.getProperty("user.name");
            System.out.println("C:\\Users\\"+uname+"\\Desktop\\station1.txt");
            try
            {
           	 	
                fichero = new FileWriter("C:\\Users\\"+uname+"\\Desktop\\station1.txt");
                pw = new PrintWriter(fichero);  
                pw.println("temperatura="+ temperatura 
                		+ "\nhumedad="+humedad
                		+"\nluminosidad="+luminosidad
                		+"\npantalla="+s);//s en este momento es lo mismo que pantalla
                
                fichero.close();
            }
            catch(Exception e){
           	 System.out.println("no se puede acceder");
            }
       	
       }
        public void escribirlog(String s){
        	
        	 FileWriter fichero = null;
             PrintWriter pw = null;
             String ip = System.getProperty("ip.name");
             String uname = System.getProperty("user.name");
             System.out.println("C:\\Users\\"+uname+"\\Desktop\\prueba.txt");
            
            	    
             		Calendar c = Calendar.getInstance();
             		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            	    String fech=df.format(c.getTime());
            	    try
                    {
                 fichero = new FileWriter("C:\\Users\\"+uname+"\\Desktop\\log_servidor.txt",true);
                 pw = new PrintWriter(fichero);                 
                 pw.println("log: "+fech+"_ "+" "+s);
                 fichero.close();
             }
             catch(Exception e){
            	 System.out.println(e.getMessage());
            	 System.out.println("no se puede acceder");
             }
        	
        }
               
}



