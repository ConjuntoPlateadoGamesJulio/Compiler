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
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Interface Interface = new Interface();
        Interface.setVisible(true);//mostrar ventana
        data data = new data();
        lexical_Analysis lA = new lexical_Analysis();
        sintactic_Analysis sA = new sintactic_Analysis(); 
        Analysis A = new Analysis(Interface, data, lA, sA);//contructor (inicializador)
    }
    
}
