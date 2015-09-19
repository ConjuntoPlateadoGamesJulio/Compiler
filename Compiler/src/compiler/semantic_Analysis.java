/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Interface.Interface;

/**
 *
 * @author Diego Olayo
 */
public class semantic_Analysis {
private Interface Interface;
private data data;
private sintactic_Analysis sintactic;

    public void set_semantic_Analysis(Interface Interface, data data, sintactic_Analysis sA) {
        this.Interface = Interface;
        this.data = data;
        this.sintactic = sA;
    }
    
    public void checkLibrary(){
        if(sintactic.hayPrintf == true && sintactic.Stdio == false)
        {
            sintactic.indice_errores = sintactic.indice_errores + 1;
            sintactic.errores[sintactic.indice_errores] = "Error.-No se encuentra libreria requerida";
        }
    }    
}
