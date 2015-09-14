/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author Luis
 */
public class sA_Double {
    int cont=0;
    boolean terminado;
    String cadena;
    String q;
    String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    String qAux;
            
          
    public boolean Estado(String cad){
        cadena = cad;
        System.out.println("araña: "+cadena);
        Estado0();
        if(terminado){
            return true;
        }
        else
            return false;
    }
    
    public void Estado0(){
        q = cadena.substring(cont, cont+1);
        if("d".equals(q) || "D".equals(q))
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
        if("u".equals(q) || "U".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado3();
        }
    }
    
    public void Estado3(){
        q = cadena.substring(cont, cont+1);
        if("b".equals(q) || "B".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado4();
        }
    }
    
    public void Estado4(){
        q = cadena.substring(cont, cont+2);
        if("le".equals(q) || "LE".equals(q))
        {
            cont=cont+2;
            //System.out.println(q + " estado4");
            Estado6();
        }
    }
    
    public void Estado6(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
          {
            cont++;
            //System.out.println(q + " estado6");
            Estado7();
          }
    }
        
    public void Estado7(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
        {
            cont++;
            //System.out.println(q + " estado7");
            Estado8();
        }
        else{
            if(",".equals(q))
            {
                cont++;
               // System.out.println(q + " estado7");
                Estado6();
            }
            else{
                if(ChecarAlfabeto())
                {
                    cont++;
                   // System.out.println(q + " estado7");
                    Estado7();             
                }
            
                else{
                    if("=".equals(q))
                    {
                        cont++;
                     //   System.out.println(q + " estado7");
                        Estado9();
                    }
                }
           }
        }
    }
    
    public void Estado8(){
        //System.out.println(q + " Estado8 ;)");
        terminado=true;
    }
    
    public void Estado9(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
        {
            cont++;
           // System.out.println(q + " estado9");
            Estado10();
        }//
    }
    
    public void Estado10(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum()){
            cont++;
            //System.out.println(q + " estado10");
            Estado10();
        }
        else{
            if(";".equals(q))
            {
                cont++;
                Estado8();
            }
            else{
                if(",".equals(q))
                {
                    cont++;
              //      System.out.println(q + " estado10");
                    Estado6();
                }
                else{
                    if(".".equals(q)){
                        cont++;
                //        System.out.println(q + "estasdo10");
                        Estado11();
                    }
                }
            }
        }
        
    }
    
    public void Estado11(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
        {
            cont++;
            //System.out.println(q + " estado11");
            Estado12();
        }
    }
    
    public void Estado12(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum()){
            cont++;
            //System.out.println(q + " estado12");
            Estado12();
        }
        else{
            if(";".equals(q))
            {
                cont++;
                Estado8();
            }
            else{
                if(",".equals(q))
                {
                    cont++;
              //      System.out.println(q + " estado12");
                    Estado6();
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
