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
public class sA_Char {
    
    int cont=0;
    boolean terminado;
    String cadena;
    String q;
    String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    String qAux;
            
    public boolean Estado(String cad){
        cadena = cad;
        System.out.println("araña:"+cadena);
        Estado0();
        if(terminado){
            return true;
        }
        else
            return false;
    }
    
    public void Estado0(){
        q = cadena.substring(cont, cont+1);
        if("c".equals(q) || "C".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado1();
        }
    }
        
    public void Estado1(){
        q = cadena.substring(cont, cont+1);
        if("h".equals(q) || "H".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado2();
        }
    }
    
    public void Estado2(){
        q = cadena.substring(cont, cont+1);
        if("a".equals(q) || "A".equals(q))
        {
            cont++;
            //System.out.println(q);
            Estado3();
        }
    }
    
    public void Estado3(){
        q = cadena.substring(cont, cont+1);
        if("r".equals(q) || "R".equals(q))
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
            Estado6();
          }
            
    }
        
    public void Estado6(){
        q = cadena.substring(cont, cont+1);
        if("[".equals(q))
        {
            cont++;
            //System.out.println(q + " estado6");
            Estado7();
        }
        else{
            if(ChecarAlfabeto())
            {
                cont++;
              //  System.out.println(q + " estado6");
                Estado6();             
            }
            
        }
    }
    
    public void Estado7(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado7");
            Estado8();
          }
            
    }
    
    public void Estado8(){
        q = cadena.substring(cont, cont+1);
        if(ChecarAlfabetoNum())
          {
            cont++;
            //System.out.println(q + " estado8");
            Estado8();
          }
        else{
            if("]".equals(q)){
                cont++;
              //  System.out.println(q + " estado8");
                Estado9();
            }
                
        }
            
            
    }
    public void Estado9(){
        q = cadena.substring(cont, cont+1);
        if(";".equals(q))
          {
            cont++;
            //System.out.println(q + " estado9");
            Estado10();
          }
            
    }
    
    public void Estado10(){
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
    

