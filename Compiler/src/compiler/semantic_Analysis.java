/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Interface.Interface;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Olayo
 */
public class semantic_Analysis {
private Interface Interface;
private data data;
private sintactic_Analysis sintactic;
private Boolean errorVar;

    public void set_semantic_Analysis(Interface Interface, data data, sintactic_Analysis sA) {
        this.Interface = Interface;
        this.data = data;
        this.sintactic = sA;
        
        this.errorVar = false;
    }
    
    public void checkLibrary(){
        if(sintactic.hayPrintf == true && sintactic.Stdio == false)
        {
            sintactic.indice_errores = sintactic.indice_errores + 1;
            sintactic.errores[sintactic.indice_errores] = "Error.-No se encuentra libreria requerida";
        }
    }
    
    public void checkVars(){
        
        for(int i = 0; i < sintactic.contVars; i++)
        {
            for(int j = i+1; j < sintactic.contVars; j++)
            {
                if(sintactic.Vars[i][0].equals(sintactic.Vars[j][0]))
                {
                    //JOptionPane.showMessageDialog(null, sintactic.ints[i] + " " + sintactic.ints[j]);
                    errorVar = true;
                }
            }
        }
        
        if(errorVar)
        {
            sintactic.indice_errores = sintactic.indice_errores + 1;
            sintactic.errores[sintactic.indice_errores] = "Error.- Declaracion de variable repetida";
        }
    }
    
    public void checkOperacion(){        
        int cont = 0;
        int ope =0;
        
        for(int i = 0; i < sintactic.contVars2; i++)
        {
            for(int j = 0; j < sintactic.contVars; j++)
            {
                if(sintactic.variables[i][0].equals(sintactic.Vars[j][0]))
                {
                    sintactic.variables[i][1] = sintactic.Vars[j][1]; 
                    //System.out.println(sintactic.variables[i][0] + " " + sintactic.variables[i][1]);
                }
            }
        }
        
        for(int i = 0; i < sintactic.contVars2; i++)
        {
            for(int j = i+1; j < sintactic.contVars2; j++)
            {
                try{
                if(sintactic.variables[i][1].equals(sintactic.variables[j][1]))
                {
                    //System.out.println(sintactic.variables[i][1] + " " + sintactic.variables[j][1]);
                    cont++;
                }
                }catch(NullPointerException e){}
            }
        }
        
        for(int i = 0; i < sintactic.contVars2; i++)
        {
            ope = i + ope;
        }
        
        //JOptionPane.showMessageDialog(null, cont+" "+ope);
        if(cont != ope && cont != 0)
        {
            sintactic.indice_errores = sintactic.indice_errores + 1;
            sintactic.errores[sintactic.indice_errores] = "Error.- Operacion entre tipos inconpatibles";
        }
    }
}
