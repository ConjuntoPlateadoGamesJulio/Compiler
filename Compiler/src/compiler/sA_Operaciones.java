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
public class sA_Operaciones {
    
    private int cont = 0;
    private boolean terminado;
    private String cadena;
    private String q;
    private final String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private String qAux;     
    
    public void Init_Ope (String cad){
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
        if(ChecarAlfabeto())
        {
            cont++;
            //System.out.println(q + " estado0");
            Estado1();
        }
    }
        
    public void Estado1(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
        {
            cont++;
            //System.out.println(q + " estado1");
            Estado1();
        }
        else{
            if(ChecarAlfabetoNum()){
                cont++;
                Estado1();
            }
            else{
                if("+".equals(q))
                {
                    cont++;
                   // System.out.println(q + " estado1");
                    Estado2();
                }
                else{
                    if("-".equals(q))
                    {
                        cont++;
                    //    System.out.println(q + " estado1");
                        Estado5();
                    }
                    else{
                        if("=".equals(q))
                        {
                            cont++;
                            //System.out.println(q +  " estado1");
                            Estado8();
                        }
                    }
                }
            }
        }
    }
    
    public void Estado2(){
        q = cadena.substring(cont, cont+1);
        if("+".equals(q))
        {
            cont++;
            //System.out.println(q +  " estado2");
            Estado3();
        }
    }
    
    public void Estado3(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
        {
            cont++;
            //System.out.println(q +  " estado3");
            Estado4();
        }
    }
    
    public void Estado4(){
        //System.out.println("Estado4 ;)");
        terminado=true;
    }
        
    public void Estado5(){
        q = cadena.substring(cont, cont+1);
        if("-".equals(q))
          {
            cont++;
          //  System.out.println(q + " estado5");
            Estado6();
          }
            
    }
        
    public void Estado6(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
        {
            cont++;
            //System.out.println(q + " estado6");
            Estado7();
        }
    }
    
    public void Estado7(){
        //System.out.println("Estado7 ;)");
        terminado=true;   
    }
    
    public void Estado8(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado8");
            Estado9();
          }
        else{
            if(ChecarAlfabeto()){
                cont++;
              //  System.out.println(q + " estado8");
                Estado13();
            }
                
        }
            
            
    }
    public void Estado9(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado9");
            Estado9();
          }
        else{
            if(ChecarAlfabeto())
            {
              cont++;
              //System.out.println(q + " estado9");
              Estado13();
            }
            else{
                if("+".equals(q) || "-".equals(q) || "*".equals(q) || "/".equals(q))
                {
                  cont++;
                //  System.out.println(q + " estado9");
                  Estado10();
                }
            }
        }       
    }
    
    public void Estado10(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado10");
            Estado11();
          }
        else{
            if(ChecarAlfabeto())
              {
                cont++;
              //  System.out.println(q + " estado10");
                Estado14();
              }    
        }
    }
    
    public void Estado11(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado11");
            Estado11();
          }
        else{
            if(ChecarAlfabeto())
            {
              cont++;
              //System.out.println(q + " estado11");
              Estado14();
            }
            else{
                if("+".equals(q) || "-".equals(q) || "*".equals(q) || "/".equals(q))
                {
                  cont++;
                //  System.out.println(q + " estado11");
                  Estado10();
                }
                else{
                    if(";".equals(q)){
                        cont++;
                  //      System.out.println(q + " estado11");
                        Estado12();
                    }
                }
            }
        }
            
    }
    
    public void Estado12(){
        //System.out.println("Estado12 ;)");
        terminado=true;
    }
    
    public void Estado13(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
          {
            cont++;
          //  System.out.println(q + " estado13");
            Estado13();
          }
        else{
            if("+".equals(q) || "-".equals(q) || "*".equals(q) || "/".equals(q))
            {
                cont++;
            //    System.out.println(q + " estado13");
                Estado10();
            }
            else
            {
                if(";".equals(q))
                {
                  cont++;
              //    System.out.println(q + " estado13");
                  Estado7();
                }
            }
        }        
    }
    
    public void Estado14(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabeto())
          {
            cont++;
            //System.out.println(q + " estado14");
            Estado14();
          }
        else{
            if("+".equals(q) || "-".equals(q) || "*".equals(q) || "/".equals(q))
            {
                cont++;
              //  System.out.println(q + " estado14");
                Estado10();
            }
            else{
                if(";".equals(q))
                {
                  cont++;
                //  System.out.println(q + " estado14");
                  Estado12();
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
