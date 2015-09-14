/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class sA_Int {
        
    int cont = 0;
    boolean terminado = false, bandera = true;
    String cadena;
    String q;
    String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    String qAux;
              
    public boolean Estado(String cad){
        cadena = cad;
        Estado0();
        System.out.println("araña: "+cadena);
        if(terminado==true)
        {
            return true;
        }
        else
            return false;
        
    }
    
    public void Estado0(){
        try{
        q = cadena.substring(cont, cont+3);
        }catch(StringIndexOutOfBoundsException e){}
        if("int".equals(q))
        {
            cont=cont+3;
            //System.out.println(q+" estado 0");
            Estado4();
        }
    }
        
    public void Estado4(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
        {
            cont++;
            //System.out.println(q + " estado4");
            Estado5();
        }    
    }
        
    public void Estado5(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
        {
            cont++;
            //System.out.println(q + " estado5");
            Estado6();
        }
        else{
            if(",".equals(q))
            {
                cont++;
                //System.out.println(q + " estado5");
                Estado4();
            }
            else{
                if(ChecarAlfabeto())
                {
                    cont++;
                    //System.out.println(q + " estado5");
                    Estado5();             
                }
                
                else{
                    if("=".equals(q))
                    {
                        cont++;
                        //System.out.println(q + " estado5");
                        Estado7();
                    }
                }
            }
        }
    }
    
    public void Estado6(){
        terminado=true;
    }
    
    public void Estado7(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
        {
            cont++;
            //System.out.println(q + " estado7");
            Estado8();
        }
        else
        {
            if(ChecarAlfabeto())
            {
                cont++;
                //System.out.println(q + " estado7");
                Estado8();
            }
        }
    }
    
    public void Estado8(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum()){
            cont++;
            //System.out.println(q + " estado8.");
            Estado8();
        }
        else
        {
            if(ChecarAlfabeto()){
                cont++;
                //System.out.println(q + " estado8.");
                Estado8();
            }
            else{
                if(",".equals(q))
                {
                    cont++;
                  //  System.out.println(q + "estado8");
                    Estado4();
                }
                else{
                    if(";".equals(q))
                    {
                        cont++;
                        Estado6();
                    }
                }
            }
        }
    }
    
    public boolean ChecarAlfabeto(){
        for(int x=0; x<54; x++)
            {
                qAux=alfabeto.substring(x, x+1);
                if(qAux.equals(q))
                {
                    return true;
                }
            }
        return false;
        
    }
    
    public boolean ChecarAlfabetoNum(){
        for(int x=0; x<10; x++)
            {
                qAux = Integer.toString(x);
                if(qAux.equals(q))
                {
                    return true;
                }
            }
        return false;
    }
}

