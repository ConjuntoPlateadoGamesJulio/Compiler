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
public class sintactic_Analysis {
    private Interface Interface;
    private data data;
    private boolean hayPrintf = false;
    private boolean Stdio = false;
    private String errores [] = new String[100];
    private int indice_errores = -1; 
    
    public void setSintacticAnalysis(Interface Interface, data data) {
        this.Interface = Interface;
        this.data = data;
    }
    
     public void inicializar_y_limpiar(){
        Interface.Errores.setText("");//limpia la ventana de errores
        for(int i = 0; i < 100; i++)//limpia el vector de errores
        {
           this.errores[i] = null;
        }
        this.indice_errores = -1;//inicializa el indice que apunta a el vector de errores
        this.hayPrintf = false;
        this.Stdio = false;
    }
     
     public void print_errors(){
        String texto_errores="";
        for(int j = 0; j <= indice_errores; j ++)
        {
            texto_errores += errores[j]+"\n";
        }
        Interface.Errores.setText(texto_errores);
    }
     
     public void cabeceras(){
        int indice = 0;
        int full = 0;
        int count_cabecera = 0;
        boolean cabecera = false;
        boolean caractInicial = false;
        String automata [] = {"1", "16", "4", "18", "5"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {   
            try{
                if(data.SymbolsTable[i][2].equals("1"))
                {
                    if(i == 0)
                    {
                        caractInicial = true;//Hubo un # en la primera posicion
                    }    
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
                            if(data.SymbolsTable[j][2].equals("17"))
                            {
                                this.Stdio = true;
                            }
                        }
                cabecera = false;
                    }catch(NullPointerException | ArrayIndexOutOfBoundsException ex){}
                }
                if(full != 0)//si no se completo la libreria
                {
                    this.indice_errores = indice_errores + 1;
                    this.errores[indice_errores] = "Error en cabeceras";
                }
                if(full == 0 && caractInicial != true)//si hubo algo antes de las librerias
                {
                    this.indice_errores = indice_errores + 1;
                    this.errores[indice_errores] = "Error se desconocen elementos";
                }
            }
        }
        if(count_cabecera == 0)//si no hubo librerias
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "No hay cabecera o librerias";
        }
    }
     
    
}
