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
private String errores [] = new String[100];
private int indice_errores = -1;

    public void set_sintactic_Analysis(Interface Interface, data data){
        this.Interface = Interface;
        this.data = data;
    }
    
    public void proccess(){
        Interface.errors.setText("");//limpia la ventana de errores
        for(int i = 0; i < 100; i++)//limpia el vector de errores
        {
           this.errores[i] = null;
        }
        this.indice_errores = -1;//inicializa el indice que apunta a el vector de errores
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
                    }catch(NullPointerException | ArrayIndexOutOfBoundsException ex){}
                }
                if(full != 0)
                {
                    this.indice_errores = indice_errores + 1;
                    this.errores[indice_errores] = "Error en cabeceras";
                } 
            }
        }
        if(count_cabecera == 0)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "No hay cabecera o librerias";
        }
    }
    
    public void Main(){
        int indice = 0;
        int count_openKeys = 0;
        int count_closeKeys = 0;
        boolean Int = false;
        boolean IntMain = false;
        int full = 0;
        
        String automata[] = {"24", "19", "2", "3"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {full = 0;
            try{
                if(data.SymbolsTable[i][2].equals("24"))
                {
                    full = 4;
                    indice = i;
                    Int = true;
                }
            }catch(NullPointerException ex){} 
            try {
                if (Int == true && data.SymbolsTable[indice + 1][2].equals("19") && IntMain == false) 
                {
                    IntMain = true;
                    int k = -1;
                    for (int j = indice; j <= indice + 3; j++) 
                    {
                        k = k + 1;
                        try {
                            if (data.SymbolsTable[j][2].equals(automata[k])) {
                                full = full - 1;
                            }
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {}
                    }
                }
            } catch (NullPointerException ex) {}
        }
        if(full != 0 && IntMain == true)
        {        
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en parentesis";
        }
        if(IntMain == false)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "No hay metodo main";
        }
    }

    public void While(){
        int full = 0;
        int indice = 0;
        int count_openKeys = 0; 
        int count_closeKeys = 0; 
        boolean While = false;
        boolean operadores = false;
        boolean completo = false;
        String automata[] = {"32", "4", "5", "31"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {
            try{
                if(data.SymbolsTable[i][2].equals("21"))
                {
                    full = 4;
                    indice = i;
                    While = true;
                }
            }catch(NullPointerException ex){}
            try{
            if(While == true && data.SymbolsTable[indice + 1][2].equals("2"))
            {
                if(data.SymbolsTable[indice + 2][1].equals("variable")||data.SymbolsTable[indice + 2][1].equals("numero"))
                {
                    
                    try{
                    if(data.SymbolsTable[indice + 4][2].equals("31")&&data.SymbolsTable[indice + 4][2]!=null)
                    {
                        for(int g = 0 ; g < 4; g ++)
                        {
                            if(data.SymbolsTable[indice + 3][2].equals(automata[g]))
                            {
                                operadores = true;
                            }
                        }
                        if ((data.SymbolsTable[indice + 5][1].equals("variable") || data.SymbolsTable[indice + 5][1].equals("numero")) && operadores == true) {
                            if (data.SymbolsTable[indice + 6][2].equals("3")) {
                                completo = true;
                            }
                        }
                    }
                    }catch(NullPointerException e){}
                        if(data.SymbolsTable[indice + 3][2].equals("4") || data.SymbolsTable[indice + 3][2].equals("5"))
                        {
                            operadores = true;   
                        }
                        if ((data.SymbolsTable[indice + 4][1].equals("variable") || data.SymbolsTable[indice + 4][1].equals("numero")) && operadores == true) {
                            if (data.SymbolsTable[indice + 5][2].equals("3")) {
                                completo = true;
                            }
                        }
                    }
                }
            }catch(NullPointerException ex){}
        }
        if(completo != true && While != false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en ciclo";
            }
        boolean doWhile = false;
        boolean dowhile = false;
        if(completo == true)
        {
            for(int i = 0; i < data.count_symbols; i ++)
            {
                try {
                    if (data.SymbolsTable[i][2].equals("22")) 
                    {dowhile = true;
                        if (data.SymbolsTable[i + 1][2].equals("8") && i < indice) 
                        {
                            doWhile = true;
                        }
                    }
                } catch (NullPointerException e) {}
            }
            if(doWhile != true && dowhile == true)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en ciclo";
            }
        }
    }
    
    public void automataIf(){
        int full = 0;
        int indice = 0;
        boolean entra = false;
        boolean operadores = false;
        boolean fin = false;
        String automata[] = {"32", "4", "5", "31"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {
            try{
                if(data.SymbolsTable[i][2].equals("23"))
                {
                    full = 4;
                    indice = i;
                    entra = true;
                }
            }catch(NullPointerException ex){}
            try{
            if(entra == true && data.SymbolsTable[indice + 1][2].equals("2"))
            {
                if(data.SymbolsTable[indice + 2][1].equals("variable")
                    ||data.SymbolsTable[indice + 2][1].equals("numero"))
                {
                    
                    try{
                    if(data.SymbolsTable[indice + 4][2].equals("31")&&
                        data.SymbolsTable[indice + 4][2]!=null)
                    {
                        for(int g = 0 ; g < 4; g ++)
                        {
                            if(data.SymbolsTable[indice + 3][2].equals(automata[g]))
                            {
                                operadores = true;
                            }
                        }
                        if ((data.SymbolsTable[indice + 5][1].equals("variable") 
                             || data.SymbolsTable[indice + 5][1].equals("numero")) 
                                && operadores == true) {
                            
                            if (data.SymbolsTable[indice + 6][2].equals("3")) {
                                fin = true;
                            }
                        }
                    }
                    }catch(NullPointerException e){}
                        if(data.SymbolsTable[indice + 3][2].equals("4")
                            || data.SymbolsTable[indice + 3][2].equals("5"))
                        {
                            operadores = true;   
                        }
                        if ((data.SymbolsTable[indice + 4][1].equals("variable") 
                            || data.SymbolsTable[indice + 4][1].equals("numero"))
                                && operadores == true) {
                            if (data.SymbolsTable[indice + 5][2].equals("3")) {
                                fin = true;
                            }
                        }
                    }
                }
            }catch(NullPointerException ex){}
        }
        if(fin != true && entra != false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en if";
            }
    }
    
    public void Printf(){
        String automata[] = {"30","2","33","33","3","14"};
        int indice = 0;
        int full = 0;
        
        for(int i = 0; i < data.count_symbols; i++)
        {
            try{
            if(data.SymbolsTable[i][2].equals("30"))
            {
                full = 6;
                indice = i;
                break;
            }
            }catch(NullPointerException e){}
        }
        int k = -1; 
        for(int i = indice; i <= (indice + 5); i++)
        {
            k = k + 1;
            try{
                if(data.SymbolsTable[i][2].equals(automata[k]))
                {
                    full = full - 1;
                }
            }catch(NullPointerException e){}
        }
        if(full != 0)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en printf";
        }
    } 
    
    public void llaves(){
        int count_openKeys = 0;
        int count_closeKeys = 0;
        
        try {
            for (int g = 0; g < data.count_symbols; g++) {
                try{
                if (data.SymbolsTable[g][2].equals("8")) 
                {
                    count_openKeys = count_openKeys + 1;
                }
                }catch(NullPointerException e){}
                try{
                if (data.SymbolsTable[g][2].equals("9")) 
                {
                    count_closeKeys = count_closeKeys + 1;
                }
                }catch(NullPointerException e){}
            }
        } catch (NullPointerException e) {}
        if ((count_openKeys != count_closeKeys) || (count_openKeys + count_closeKeys) == 0) 
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en llaves";
        }
    }

    public void print_errors(){
        String texto_errores="";
        for(int j = 0; j <= indice_errores; j ++)
        {
            texto_errores += errores[j]+"\n";
        }
        Interface.errors.setText(texto_errores);
    }
}
