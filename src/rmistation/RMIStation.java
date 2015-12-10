package rmistation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable; 
import java.rmi.*;
import java.rmi.server.*;

import org.omg.CORBA.Environment;



public class RMIStation {     
    
    private String temperatura="1";
    private String humedad="2";
    private String luminosidad="3";
    private String pantalla="4";
    
	public RMIStation () {
		String uname = System.getProperty("user.name");
        leerArchivo("C:\\Users\\"+uname+"\\Desktop\\station1.txt");		
	}
        
        public String GetTemperatura(){
        
        	escribirlog("Peticion temperatura: "+temperatura);
            return temperatura;
        }
        
        public String GetHumedad(){
        	escribirlog("Peticion humedad: "+humedad);
        	return humedad;
        }
        
        public String Getluminosidad() {
        	escribirlog("Peticion luminosidad: "+luminosidad);
            return luminosidad;
        }
        public  String GetPantalla(){
        	escribirlog("Peticion pantalla: "+pantalla);
            return pantalla;
        }
        public void SetPantalla(String p){
            pantalla=p;
            escribirpantalla(pantalla);
            escribirlog("Escritura pantalla: "+pantalla);
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
             String uname = System.getProperty("user.name");
             System.out.println("C:\\Users\\"+uname+"\\Desktop\\prueba.txt");
             try
             {
            	 
                 fichero = new FileWriter("C:\\Users\\"+uname+"\\Desktop\\prueba.txt",true);
                 pw = new PrintWriter(fichero);                 
                 pw.println(s);
                 fichero.close();
             }
             catch(Exception e){
            	 System.out.println("no se puede acceder");
             }
        	
        }
               
}



