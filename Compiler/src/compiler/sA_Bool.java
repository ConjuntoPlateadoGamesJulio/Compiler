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
public class sA_Bool {
    
    private int cont = 0;
    private boolean terminado;
    private String cadena;
    private String q;
    private final String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private String qAux;     
    
    public void Init_Bool (String cad){
        this.terminado = false;
        this.cadena = cad;
        this.cont = 0;
    }
              
    public boolean Estado(){
        Estado0();
        System.out.println("araña: "+cadena);
        if(this.terminado)
        {
            return true;
        }
        else
            return false;      
    }
    
    public void Estado0(){
        q = cadena.substring(cont, cont+1);
        if("b".equals(q) || "B".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado1();
        }
    }
        
    public void Estado1(){
        q = cadena.substring(cont, cont+1);
        if("o".equals(q) || "O".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado2();
        }
    }
    
    public void Estado2(){
        q = cadena.substring(cont, cont+1);
        if("o".equals(q) || "O".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado3();
        }
    }
    
    public void Estado3(){
        q = cadena.substring(cont, cont+1);
        if("l".equals(q) || "L".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado5();
        }
    }
    
    public void Estado5(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
          {
            cont++;
            //System.out.println(q + " estado5");
            Estado8();
          }
            
    }
        
    
    
    public void Estado8(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
          {
            cont++;
            //System.out.println(q + " estado8");
            Estado8();
          }
        else{
            if(ChecarAlfabetoNum()){
                cont++;
                Estado8();
            }
            else{
                if(";".equals(q)){
                    cont++;
                  //  System.out.println(q + " estado8");
                    Estado11();
                }
                else
                {
                    if(",".equals(q)){
                        cont++;
                        Estado8();
                    }
                    else{
                        if("=".equals(q)){
                            cont++;
                        //  System.out.println(q + " estado8");
                            Estado9();
                        }   
                    }
                }
            }
        }       
    }
    
    public void Estado9(){
        try{
        q = cadena.substring(cont, cont+4);
        }catch(StringIndexOutOfBoundsException e){}
        if("true".equals(q))
          {
            cont=cont+4;
            //System.out.println(q + " estado9");
            Estado10();
          }
        else
        {
            try{
            q = cadena.substring(cont,cont+5);
            }catch(StringIndexOutOfBoundsException e){}
            if("false".equals(q))
            {
              cont=cont+5;
              //System.out.println(q + " estado9");
              Estado10();
            }
        }
            
            
    }
    public void Estado10(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
          {
            cont++;
            //System.out.println(q + " estado9");
            Estado11();
          }
            
    }
    
    public void Estado11(){
        //System.out.println("Estado10 ;)");
        terminado=true;
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
