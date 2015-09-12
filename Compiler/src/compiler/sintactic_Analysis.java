/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Interface.Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Olayo
 */
public class sintactic_Analysis {
private Interface Interface;
private data data;
private String patron = ("(include|stdio.h|stdlib.h|main|for|while|double|if|int|float|do|bool|char|String|cout|printf)|"//palabras reservadas
                            + "([a-zA-Z]+)|"//variables
                            + "([#|(|)|<|>|[|]|{|}|+|-|*|/|;|=]+)|"//simbolos
                            + "([0-9]+)");//numeros
private String codigo;
private Pattern pattern;
private Matcher matcher;

    public void set_sintactic_Analysis(Interface Interface, data data){
        this.Interface = Interface;
        this.data = data;
    }
    
    public void proccess(){
        Interface.errors.setText("");//limpia la ventana de errores
    }
    
    public void cabeceras(){
        int indice = 0;
        int full=5;
        boolean cabecera = false;
        int count_cabecera = 0;
        String automata [] = {"1", "16", "4", "18", "5"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {
            try{
                 if(data.SymbolsTable[i][2].equals("1"))
                    {
                        full = 5;
                        indice = i;
                        cabecera = true;
                        count_cabecera = count_cabecera + 1;
                    }
            }catch(NullPointerException ex){}   
        if(cabecera == false && count_cabecera == 0)//error
        {
            Interface.errors.setText("No hay cabecera o librerias");
        }
        if(cabecera== true)
        {   int k = -1;
            for(int j = indice; j <= indice + 4; j ++)
            { k = k + 1;
                try
                {
                    if(data.SymbolsTable[j][2].equals(automata[k]) || (j==(indice + 3)&&data.SymbolsTable[j][2].equals("17")))
                    {
                        full = full - 1;
                    }
            cabecera = false;
                }catch(NullPointerException | ArrayIndexOutOfBoundsException excepcion){}
            }
            if(full != 0)
            {
                Interface.errors.setText("Error en cabeceras");
            } 
        }
    }
  }
}
