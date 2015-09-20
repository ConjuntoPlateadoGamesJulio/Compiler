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
    private final int n = 10000;
    public String [][] SymbolsTable = new String[n][3];
    
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
                                           {".","36"},
                                           {"\"","37"},
                                           {"&","43"}
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
                                           {"else","35"},
                                           {"%d","38"},
                                           {"%f","39"},
                                           {"%s","40"},
                                           {"%c","41"},
                                           {"scanf","42"},
                                           {"ope:","44"}
                                          };
    public String tipos[] = {"24","25","26","27","28","29"};
    
    public void setData(){//constructor
        this.row = -1;
        this.column = 0;
        
        for(int i = 0; i < 300; i ++)
        {
            for(int j = 0; j < 3; j ++)
            {
                SymbolsTable[i][j] = null;
            }
        }
        this.count_symbols = 0;//variable que cuenta cuantos tokens se han introducido
    }
    
    public void insert_SymbolsTable(String token, String clase){
        //cada vez que entra
        this.row = this.row + 1;//la fila aumenta
        this.column = 0;//columna cero
        
        if(clase.equals("WR"))//si es una palabra reservada
        {
            this.SymbolsTable[row][column] = token;//inserta la palabra reervada
            this.column = this.column + 1;//pasamos a  la otra columna
            this.SymbolsTable[row][column] = "Palabra Reservada";//se escribe palabra reservada
            this.column = this.column + 1;//pasamos a la otra columna
            
            for(int i = 0; i < 22; i ++)
            {
                if(dictionaryWReser[i][0].equals(token))//se busca la palabra reservada
                {
                    this.SymbolsTable[row][column] = dictionaryWReser[i][1];//se escribe el valor de la palabra reservada
                }
            }
            this.count_symbols = count_symbols + 1;//se inserto un simbolo
        }

        if(clase.equals("Simbol"))//si es simbolo
        {
            this.SymbolsTable[row][column] = token;//inserta el simbolo
            this.column = this.column + 1;//pasamos a  la otra columna
            this.SymbolsTable[row][column] = "Simbolo";//se escribe el simbolo
            this.column = this.column + 1;//pasamos a la otra columna
            
            for(int i = 0; i < 21; i ++)
                {
                    if(dictionarySimbol[i][0].equals(token))//se busca el simbolo
                    {
                        this.SymbolsTable[row][column] = dictionarySimbol[i][1];//se escribe el valor del simbolo
                    }
                }
            this.count_symbols = count_symbols + 1;//se inserto un simbolo
        }
        
        if(clase.equals("variable"))
        {   
            boolean variable = false;
            
            for(int i = 0; i < 6; i++)//verifica si hay tipo
            {
                try {
                    if (SymbolsTable[row - 1][column + 2].equals(tipos[i])) 
                    {
                        this.SymbolsTable[row][column] = token;//inserta el simbolo
                        this.column = this.column + 1;//pasamos a  la otra columna
                        this.SymbolsTable[row][column] = "variable";//se escribe el simbolo
                        this.count_symbols = count_symbols + 1;//se inserto un simbolo
                        variable = true;
                        break;
                    }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException ex){}
            }
            
            if(variable == false)//si no tiene tipo antes
            {   
                boolean declarada = false;
                for(int i = 0; i < count_symbols; i ++)
                {
                    if(SymbolsTable[i][1].equals("variable")&&SymbolsTable[i][0].equals(token))//busca si ya existe esa palabra
                    {
                        this.SymbolsTable[row][column] = token;
                        this.column = this.column + 1;//pasamos a  la otra columna
                        this.SymbolsTable[row][column] = "variable";//tipo
                        this.count_symbols = count_symbols + 1;//se inserto un simbolo
                        declarada = true;
                        break;
                    }                         
                }
                if(declarada == false)
                {
                    this.SymbolsTable[row][column] = token;
                    this.column = this.column + 1;//pasamos a  la otra columna
                    this.SymbolsTable[row][column] = "texto";//tipo
                    this.column = this.column + 1;//pasamos a  la otra columna
                    this.SymbolsTable[row][column] = "erroneo";//tipo///////////////////////////
                    this.count_symbols = count_symbols + 1;//se inserto un simbolo
                }
            }
        }
        
        if(clase.equals("numero"))
        {
            this.SymbolsTable[row][column] = token;//inserta el simbolo
            this.column = this.column + 1;//pasamos a  la otra columna
            this.SymbolsTable[row][column] = "numero";//tipo
            this.count_symbols=count_symbols + 1;
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
    }
    
}
