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
public class sintactic_Analysis {
    private Interface Interface;
    private data data;
    private sA_Int Int;
    private boolean hayPrintf = false;
    private boolean Stdio = false;
    private String errores [] = new String[100];
    private int indice_errores = -1; 
    private int numeroElse=0;
    private int numeroIf=0;
    
    public void setSintacticAnalysis(Interface Interface, data data, sA_Int Int) {
        this.Interface = Interface;
        this.data = data;
        this.Int = Int;
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
        this.numeroElse=0;
        this.numeroIf=0;
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
    
     public void While(){
        int full = 0;
        int indice = 0; 
        boolean While = false;
        boolean operadores = false;
        boolean completo = false;
        boolean hayComa = false;
        String automata[] = {"32", "4", "5", "31"};
        
        for(int i = 0; i < data.count_symbols; i ++)
        {
            While = false;
            try{
                if(data.SymbolsTable[i][2].equals("21"))
                {
                    hayComa = false;
                    full = 4;
                    indice = i;
                    operadores = false;
                    completo = false;
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
                            if (data.SymbolsTable[indice + 5][2].equals("3")&&(data.SymbolsTable[indice + 6][2].equals("8")||data.SymbolsTable[indice + 6][2].equals("14"))) {
                                completo = true;
                                if(data.SymbolsTable[indice + 6][2].equals("14"))
                                {
                                    hayComa = true;
                                }
                            }
                        }
                    }
                }
            }catch(NullPointerException ex){}
        if(completo != true && While != false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en ciclo";
            }

        boolean doWhile = false;
        boolean dowhile = false;
        if(completo == true)
        {
            for(int f = 0; f < data.count_symbols; f ++)
            {
                try {
                    if (data.SymbolsTable[f][2].equals("22")) 
                    {
                        dowhile = true;
                        if (data.SymbolsTable[f + 1][2].equals("8") && f < indice) 
                        {
                            doWhile = true;
                        }
                    }
                } catch (NullPointerException e) {}
            }
            if(While == true && doWhile != true && dowhile == true)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en ciclo";
            }
            if(While == true && dowhile == false && hayComa == true)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "while tiene punto y coma";
            }
            if(While == true && dowhile == true && doWhile == true && hayComa == false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "do-while sin punto y coma"; 
            }
        }
      }
    }
     void automataIf(){
        int indice=0,in=0,tamaño=0;
        boolean entra=false,fin=false;
        boolean simbol=false;
        String simboloCompara[]={"31","32","4","5"};
        Boolean IF[]=new Boolean[100];
        for (int i = 0; i <IF.length; i++) {
            IF[i]=false;
        }
        for(int i=0;i<data.count_symbols;i++){
            try{
                if (data.SymbolsTable[i][2].equals("23")) {  //if
                    indice=i;
                    numeroIf++;
                    entra=true;
                    fin=false;
                }
                if(data.SymbolsTable[indice+1][2].equals("2") && entra==true){ // (
                    if (data.SymbolsTable[indice+2][1].equals("variable") ||  // variable declarada
                        data.SymbolsTable[indice+2][1].equals("numero") ) {   //numero
                          try{
                            if (data.SymbolsTable[indice+4][2].equals("31")) {  // =
                                for(int j=0;j<4;j++){
                                    if (data.SymbolsTable[indice+3][2].equals(simboloCompara[j])) { // ! < > =
                                        simbol=true;
                                    }
                                }
                                if (simbol==true && data.SymbolsTable[indice+5][1].equals("variable") ||  // variable declarada
                                        data.SymbolsTable[indice+5][1].equals("numero")) {               // numero
                                    if (data.SymbolsTable[indice+6][2].equals("3")) {                    // )
                                        if (data.SymbolsTable[indice+7][2].equals("8")) {                // {
                                            fin=true;
                                            IF[numeroIf]=true;
                                            continue;
                                        }
                                    }
                                }
                            }
                        }catch(NullPointerException ex){}
                        if (data.SymbolsTable[indice+3][2].equals("4") || data.SymbolsTable[indice+3][2].equals("5")) { // < >
                            if (data.SymbolsTable[indice+4][1].equals("variable") || // variable declarada
                                    data.SymbolsTable[indice+4][1].equals("numero")) { // numero
                                if (data.SymbolsTable[indice+5][2].equals("3")) { // )
                                    if (data.SymbolsTable[indice+6][2].equals("8")) { // {
                                        fin=true;
                                        IF[numeroIf]=true;
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }catch(NullPointerException ex){}
        }
        boolean noIF=false;
        for (int i =1; i <=numeroIf; i++) {
            if (IF[i]==false) {
                noIF=true;
            }
        }
        if(fin != true && entra != false || noIF==true){
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en if";
        }
        
    }
    void automataElse(){
        int llave=0;
        boolean entra=false,fin=false;
        boolean ELSE[]=new boolean[100];
        boolean dobleElse=false;
        for (int i = 0; i < ELSE.length; i++) {
            ELSE[i]=false;
        }
                for (int i =0; i <data.count_symbols; i++){
                    try{
                        if (data.SymbolsTable[i][2].equals("35") ) { // else
                            llave=i;
                            entra=true;
                            fin=false;
                            numeroElse++;
                        }
                        if (entra==true && data.SymbolsTable[llave+1][2].equals("8")) { // {
                            fin=true;
                            ELSE[numeroElse]=true;
                            entra=false;
                            if (data.SymbolsTable[llave+2][2].equals("35")) { // {
                                dobleElse=true;
                            }
                        }
                    }catch(NullPointerException ex){}
                }
        boolean noElse=false;
        for (int i = 1; i <= numeroElse; i++) {
            if (ELSE[i]==false) {
                noElse=true;
            }
        }
        if(fin !=true && entra!=false  || dobleElse==true || numeroElse> numeroIf || noElse==true){
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en else";
        }
    }
    
    public void Int(){
        int indice = 0;
        boolean terminado = false;
        boolean banEncontrado = false;
        boolean banError = false;
        boolean primeravez = true;
        String cadena = null;
        
        for(int i = 0; i<data.count_symbols; i++){
            try{
                if(data.SymbolsTable[i][2].equals("24"))
                    {
                        indice = i;
                        banEncontrado = true;
                    }
                }catch(NullPointerException e){}
            
            if(banEncontrado == true)
            {                
                try{
                    while(!";".equals(data.SymbolsTable[indice][0]) 
                            && !"float".equals(data.SymbolsTable[indice][0])
                            && !"char".equals(data.SymbolsTable[indice][0])     
                            && !"double".equals(data.SymbolsTable[indice][0])
                            && !"bool".equals(data.SymbolsTable[indice][0])
                            && !"42".equals(data.SymbolsTable[indice][2])
                            && !"19".equals(data.SymbolsTable[indice+1][2])
                            && !"int".equals(data.SymbolsTable[indice+1][0])
                            )
                    {
                        banError = true;
                        try{
                        cadena = cadena + data.SymbolsTable[indice][0];
                        if(primeravez)
                        {
                            cadena = cadena.substring(4);
                            primeravez = false;
                        }
                        indice++;
                        }catch(NullPointerException e){}   
                    }
                }catch(ArrayIndexOutOfBoundsException e){}

                try{
                if(data.SymbolsTable[indice][0].equals(";"))
                {
                   try{
                    cadena = cadena + ";";
                    terminado = Int.Estado(cadena);
                    JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(terminado == false && banEncontrado && banError)
                    {
                        JOptionPane.showMessageDialog(null, banEncontrado+ "Por que entra  no mamar" + terminado);
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error en Int";
                    }
                
                terminado = false;
                banEncontrado = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
}
