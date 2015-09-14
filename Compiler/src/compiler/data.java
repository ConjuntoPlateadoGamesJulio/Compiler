/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import javax.swing.JOptionPane;

/**
 *
 * @author Diego Olayo
 */
public class data {
    public int row;
    public int column;
    public int count_symbols;
    private final int n = 100;
    public String [][] SymbolsTable = new String[n][3];
    
    public String [][] SymbolsTableAraña = new String [n][3];
    public int count_symbolsAraña;
    
    
    public String dictionarySimbol[][] = {
                                           {"#","1"},
                                           {"(","2"},
                                           {")","3"},
                                           {"<","4"},
                                           {">","5"},
                                           {"[","6"},
                                           {"]","7"},
                                           {"{","8"},
                                           {"}","9"},
                                           {"+","10"},
                                           {"-","11"},
                                           {"*","12"},
                                           {"/","13"},
                                           {";","14"},
                                           {"=","31"},
                                           {"!","32"},
                                           {"'","33"},
                                           {",","34"},
                                           {"&","35"},//simbolos que faltaban
                                           {"%","36"},//apartir de aqui
                                           {"\"","37"},// y aqui
                                           {".","38"}//Otro simbolo
                                          };
    
    public String dictionaryWReser[][] = {
                                           {"include","16",},
                                           {"stdio.h","17"},
                                           {"stdlib.h","18"},
                                           {"main","19"},
                                           {"for","20"},
                                           {"while","21"},
                                           {"do","22"},
                                           {"if","23"},
                                           {"int","24"},
                                           {"float","25"},
                                           {"double","26"},
                                           {"bool","27"},
                                           {"char","28"},
                                           {"String","29"},
                                           {"printf","30"},
                                           {"scanf","43"},
                                           {"%d","38"}, //agregue esta madre porque
                                           {"%f","39"}, //%d lo separaba y la d tenia 
                                           {"%s","40"}, // que estar declarada como
                                           {"%c","41"}, // variable
                                           {"ope:","42"}
                                          };
    public String tipos[] = {"24","25","26","27","28","29"};
    
    public void setData(){//constructor
        this.row = -1;
        this.column = 0;
        
        for(int i = 0; i < 42; i ++)
        {
            for(int j = 0; j < 3; j ++)
            {
                SymbolsTable[i][j] = null;
            }
        }
        this.count_symbols = 0;//variable que cuenta cuantos tokens se han introducido
        this.count_symbolsAraña = 0;
    }
    
    public void insert_SymbolsTable(String token, String clase){
        //cada vez que entra
        this.row = this.row + 1;//la fila aumenta
        this.column = 0;//columna cero
        this.count_symbols = count_symbols + 1;
        this.count_symbolsAraña = count_symbolsAraña + 1;
        
        if(clase.equals("WR"))//si es una palabra reservada
        {
            this.SymbolsTable[row][column] = token;//inserta la palabra reervada
            this.SymbolsTableAraña[row][column] = token;
            this.column = this.column + 1;//pasamos a  la otra columna
            this.SymbolsTable[row][column] = "Palabra Reservada";//se escribe palabra reservada
            this.SymbolsTableAraña[row][column] = "Palbra Reservada";
            this.column = this.column + 1;//pasamos a la otra columna
            
            for(int i = 0; i <21; i ++)
            {
                if(dictionaryWReser[i][0].equals(token))//se busca la palabra reservada
                {
                    this.SymbolsTable[row][column] = dictionaryWReser[i][1];//se escribe el valor de la palabra reservada
                    this.SymbolsTableAraña[row][column] = dictionaryWReser[i][1];
                }
            }
        }
        else
        {
            if(clase.equals("Simbol"))//si es simbolo
            {
                try{
                this.SymbolsTable[row][column] = token;//inserta el simbolo
                this.SymbolsTableAraña[row][column] = token;
                this.column = this.column + 1;//pasamos a  la otra columna
                this.SymbolsTable[row][column] = "Simbolo";//se escribe el simbolo
                this.SymbolsTableAraña[row][column] = "Simbolo";
                this.column = this.column + 1;//pasamos a la otra columna
                }
                catch(ArrayIndexOutOfBoundsException ex){}
            
                for(int i = 0; i <21; i ++)
                    {
                        if(dictionarySimbol[i][0].equals(token))//se busca el simbolo
                        {
                            this.SymbolsTable[row][column] = dictionarySimbol[i][1];//se escribe el valor del simbolo
                            this.SymbolsTableAraña[row][column] = dictionarySimbol[i][1];
                        }
                    }
            }
        }
        
        boolean variable = false;
        if(clase.equals("variable"))
        {//JOptionPane.showMessageDialog(null, "hay variable");
            
            this.SymbolsTableAraña[row][0] = token; 
            this.SymbolsTableAraña[row][1] = "Variable";
            this.SymbolsTableAraña[row][2] = "3.1416";
            
            for(int i = 0; i < 7; i++)
            {
                try {
                    if (SymbolsTable[row - 1][column + 2].equals(tipos[i])) 
                    {
                        this.SymbolsTable[row][column] = token;//inserta el simbolo
                        this.column = this.column + 1;//pasamos a  la otra columna
                        this.SymbolsTable[row][column] = "variable";//se escribe el simbolo
                        variable = true;
                        break;
                    }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException ex){}
            }
            if(variable != true)
            {
                for(int i = 0; i < count_symbols - 1; i ++)
                {
                    try{
                    if(SymbolsTable[i][0].equals(token))
                    {   this.column=0;
                        this.SymbolsTable[row][column] = token;//inserta el simbolo
                        this.column = this.column + 1;//pasamos a  la otra columna
                        this.SymbolsTable[row][column] = "variable";//se escribe el simbolo
                    }
                    }catch(NullPointerException e){}
                }
            }
        }
        
        if(clase.equals("numero"))
        {
            this.SymbolsTable[row][column] = token;//inserta el simbolo
            this.SymbolsTableAraña[row][column] = token;
            this.column = this.column + 1;//pasamos a  la otra columna
            this.SymbolsTable[row][column] = "numero";//se escribe el simbolo
            this.SymbolsTableAraña[row][column] = "numero";
        }
        
    }
    //esta mierda solo es para probar que se esten guardando bien los simbolos y palabras reservadas en la tabla de simbolos
    public void solo_probando(){
        System.out.println("TABLA DE SIMBOLOS");
        System.out.println("Token\tClase\tTipo");
        for(int i= 0; i<count_symbols;i++)
        {
            for(int j = 0; j<3; j++)
            {
                System.out.print(SymbolsTable[i][j]+"\t");
            }
            System.out.print("\n");
        }
        
        System.out.println("Tabla Araña \nToken\tClase\tTipo");
        for(int i= 0; i<count_symbolsAraña;i++)
        {
            for(int j = 0; j<3; j++)
            {
                System.out.print(SymbolsTableAraña[i][j]+"\t");
            }
            System.out.print("\n");
        }
    }
}
