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
private sA_Int Int;
private sA_Float Float;
private sA_Char Char;
private sA_Double Double;
private sA_Bool Bool;
private sA_Operaciones Oper;

private String patron = ("(include|stdio.h|stdlib.h|main|for|while|double|if|int|float|do|bool|char|String|cout|printf)|"//palabras reservadas
                            + "([a-zA-Z]+)|"//variables
                            + "([#|(|)|<|>|[|]|{|}|+|-|*|/|;|=]+)|"//simbolos
                            + "([0-9]+)");//numeros
private String codigo;
private Pattern pattern;
private Matcher matcher;
private String errores [] = new String[100];
private int indice_errores = -1;
private boolean hayPrintf = false;
private boolean Stdio = false;
private boolean hayScanf = false;

String cadena;

    public void set_sintactic_Analysis(Interface Interface, data data, sA_Int Int){
        this.Interface = Interface;
        this.data = data;
        this.Int = Int;
    }
    
    public void proccess(){
        Interface.errors.setText("");//limpia la ventana de errores
        for(int i = 0; i < 100; i++)//limpia el vector de errores
        {
           this.errores[i] = null;
        }
        this.indice_errores = -1;//inicializa el indice que apunta a el vector de errores
        this.hayPrintf = false;
        this.Stdio = false;
        this.hayScanf = false;
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
                            if(data.SymbolsTable[j][2].equals("17"))
                            {
                                this.Stdio = true;
                            }
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
         boolean printf = false;
        
        for(int i = 0; i < data.count_symbols; i++)
        {
            try{
            if(data.SymbolsTable[i][2].equals("30"))
            {
                full = 6;
                indice = i;
                printf = true;
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
        if(full == 0 && printf == true )
        {
            this.hayPrintf = true;
        }
        if(full != 0)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en printf";
        }
    } 
    
    public void autonomaScanf(){
        int indice = 0;
        boolean entra = false;
        boolean operadores = false;
        boolean fin = false;
         boolean datos = false;
        String tiposDatos[]={"38","39","40","41"};
        
            for(int i = 0; i < data.count_symbols; i ++){
                try{
                    if(data.SymbolsTable[i][2].equals("43"))
                    {
                            indice=i;
                            entra=true;
                    }
                    if(entra == true && data.SymbolsTable[indice+1][2].equals("2")){ //(
                        if(data.SymbolsTable[indice+2][2].equals("37")){ //"
                            for(int j=0;j<4;j++){
                                if(data.SymbolsTable[indice+3][2].equals(tiposDatos[j])){//%d %f %c %s
                                   datos=true; 
                                   //JOptionPane.showMessageDialog(null,"sc");
                                } 
                            } 
                                    if (data.SymbolsTable[indice+4][2].equals("37") && datos== true ) { //"
                                        if(data.SymbolsTable[indice+5][2].equals("34")){//,
                                          if(data.SymbolsTable[indice+6][2].equals("35")){// &
                                              if(data.SymbolsTable[indice+7][1].equals("variable") //vaariable numero
                                                  ||data.SymbolsTable[indice+7][1].equals("numero")){
                                                  if (data.SymbolsTable[indice+8][2].equals("3")) { //)
                                                      if (data.SymbolsTable[indice+9][2].equals("14")) { // ;
                                                          fin=true;
                                                          this.hayScanf = true;
                                                         // JOptionPane.showMessageDialog(null,"scanf");
                                                          break;
                                                      }
                                                  }
                                                }
                                            }
                                        }
                                    }
                        }
                    }
                }catch(NullPointerException ex){}
            }
            if(fin != true && entra != false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en scanf";
            }
    }
    
    ///SEMANTICO
    public void semanticPrintf(){
        if(hayPrintf == true || hayScanf == true)
        {
            if(Stdio == false)
            {
               this.indice_errores = indice_errores + 1;
               this.errores[indice_errores] = "error semantico, se requiere libreria"; 
            }
        }
    }
    
    public void automataFor(){
        int indice = 0;
        boolean entra = false;
        boolean operadores = false;
        boolean fin = false;
         boolean datos = false;
         String simbolos[]={"31","4","5"};
         for (int i = 0; i <data.count_symbols; i++) {
             try {
                 if (data.SymbolsTable[i][2].equals("20")) {  // for
                     indice=i;
                     entra=true;
                 }
                 if(data.SymbolsTable[indice+1][2].equals("2")&& entra==true){ // (
                     
                     if(data.SymbolsTable[indice+2][1].equals("variable")){    //variable incremental i
                         if(data.SymbolsTable[indice+3][2].equals("31")){     // =
                             if(data.SymbolsTable[indice+4][1].equals("variable")
                                 || data.SymbolsTable[indice+4][1].equals("numero")){ // variable o numero :)
                                 if(data.SymbolsTable[indice+5][2].equals("14")){     // ;
                                     if (data.SymbolsTable[indice+6][1].equals("variable") //asegura que sea la misma variable 
                                         && data.SymbolsTable[indice+6][0].equals(data.SymbolsTable[indice+2][0])) {
                                         try{
                                            if(data.SymbolsTable[indice+8][2].equals("31")){  // =
                                                for(int j=0;j<3;j++){
                                                    if (data.SymbolsTable[indice+7][2].equals(simbolos[j])) {// = < >
                                                        operadores=true;
                                                    }
                                                }
                                                if(data.SymbolsTable[indice+9][1].equals("variable")
                                                    || data.SymbolsTable[indice+9][1].equals("numero") && operadores==true){
                                                    if(data.SymbolsTable[indice+10][2].equals("14")){
                                                      if (data.SymbolsTable[indice+6][1].equals("variable") //asegura que sea la misma variable i
                                                         && data.SymbolsTable[indice+11][0].equals(data.SymbolsTable[indice+2][0])){
                                                          if(data.SymbolsTable[indice+12][2].equals("10")) {
                                                              if(data.SymbolsTable[indice+13][2].equals("10")){
                                                                if(data.SymbolsTable[indice+14][2].equals("3")){
                                                                fin=true;
                                                                break;
                                                                }
                                                              }
                                                          }
                                                      }
                                                    }
                                                }
                                            }
                                         }catch(NullPointerException ex){}
                                        if(data.SymbolsTable[indice+7][2].equals("4")||
                                            data.SymbolsTable[indice+7][2].equals("5")){ //simbolo < >
                                            if(data.SymbolsTable[indice+8][1].equals("variable")
                                                    || data.SymbolsTable[indice+8][1].equals("numero")){
                                                    if(data.SymbolsTable[indice+9][2].equals("14")){ // ;
                                                      if (data.SymbolsTable[indice+6][1].equals("variable") //asegura que sea la misma variable i
                                                         && data.SymbolsTable[indice+10][0].equals(data.SymbolsTable[indice+2][0])){
                                                          if(data.SymbolsTable[indice+11][2].equals("10")) {
                                                              if(data.SymbolsTable[indice+12][2].equals("10")){
                                                                if(data.SymbolsTable[indice+13][2].equals("3")){
                                                                fin=true;
                                                                break;
                                                                }
                                                              }
                                                          }
                                                      }
                                                    }
                                                }
                                        }
                                     }
                                 }
                            }
                         }
                     }
                 }
             } catch (NullPointerException ex) {
             }
        }
          if(fin != true && entra != false)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "error en for";
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
        
        //JOptionPane.showMessageDialog(null, count_openKeys + " " + count_closeKeys);
        if ((count_openKeys != count_closeKeys) || (count_openKeys + count_closeKeys) == 0) 
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en llaves";
        }
    }
    
       public void Int(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
        
        for(int i = 0; i<data.count_symbolsAraña; i++){
            try{
                if(data.SymbolsTableAraña[i][2].equals("24"))
                    {
                        indice = i;
                        banError = true;
                    }
                }catch(NullPointerException e){}
            if(banError == true)
            {
                try{
                    while(!";".equals(data.SymbolsTableAraña[indice][0]) 
                            && !"float".equals(data.SymbolsTableAraña[indice][0])
                            && !"char".equals(data.SymbolsTableAraña[indice][0])     
                            && !"double".equals(data.SymbolsTableAraña[indice][0])
                            && !"bool".equals(data.SymbolsTableAraña[indice][0])
                            && !"42".equals(data.SymbolsTableAraña[indice][2])
                            && !"19".equals(data.SymbolsTableAraña[indice+1][2])
                            && !"int".equals(data.SymbolsTableAraña[indice+1][0])
                            )
                    {
                        try{
                        cadena = cadena + data.SymbolsTableAraña[indice][0];
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
                if(data.SymbolsTableAraña[indice][0].equals(";"))
                {
                   try{
                    cadena = cadena + ";";
                    terminado = Int.Estado(cadena);
                    //JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(terminado == false && banError && cadena != null)
                    {
                        Interface.errors.append("Error en declaracion Int: " + cadena + "\n");
                    }
                
                terminado = false;
                banError = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
    
    public void Float(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
        
        for(int i = 0; i<data.count_symbolsAraña; i++){
            Float = new sA_Float();
            try{
                if(data.SymbolsTableAraña[i][2].equals("25"))
                    {
                        indice = i;
                        banError = true;
                    }
                }catch(NullPointerException e){}
            if( banError == true){
                try{
                    while(!";".equals(data.SymbolsTableAraña[indice][0]) 
                            && !"int".equals(data.SymbolsTableAraña[indice][0])
                            && !"char".equals(data.SymbolsTableAraña[indice][0])     
                            && !"double".equals(data.SymbolsTableAraña[indice][0])
                            && !"bool".equals(data.SymbolsTableAraña[indice][0])
                            && !"float".equals(data.SymbolsTableAraña[indice+1][0])
                            && !"42".equals(data.SymbolsTableAraña[indice][2])
                            )
                    {
                        try{
                        cadena = cadena + data.SymbolsTableAraña[indice][0];
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
                if(data.SymbolsTableAraña[indice][0].equals(";") && banError)
                {
                   try{
                    cadena = cadena + ";";
                    terminado = Float.Estado(cadena);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){}

                if(terminado == false && banError && cadena != null)
                    {
                        Interface.errors.append("Error en declaracion Float: " + cadena + "\n");
                    }
                
                terminado = false;
                banError = false;
                primeravez = true;
                cadena = null;
            }    
        }
    }
    
    public void Char(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
        Char = new sA_Char();
        
        for(int i = 0; i<data.count_symbolsAraña; i++){
            try{
                if(data.SymbolsTableAraña[i][2].equals("28"))
                    {
                        indice = i;
                        banError = true;
                    }
                }catch(NullPointerException e){}
           }
        try{
            while(!";".equals(data.SymbolsTableAraña[indice][0]) 
                    && !"float".equals(data.SymbolsTableAraña[indice][0])
                    && !"int".equals(data.SymbolsTableAraña[indice][0])     
                    && !"double".equals(data.SymbolsTableAraña[indice][0])
                    && !"bool".equals(data.SymbolsTableAraña[indice][0])
                    && !"42".equals(data.SymbolsTableAraña[indice][2])
                    )
            {
                try{
                cadena = cadena + data.SymbolsTableAraña[indice][0];
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
        if(data.SymbolsTableAraña[indice][0].equals(";") && banError)
        {
           try{
            cadena = cadena + ";";
            terminado = Char.Estado(cadena);
           }catch(NullPointerException e){}   
        }
        }catch(ArrayIndexOutOfBoundsException e){}
        
        if(terminado == false && banError)
        {
            Interface.errors.append("Error en declaracion Char\n");
        }
    }
    
     public void Double(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
        
        for(int i = 0; i<data.count_symbolsAraña; i++){
            Double = new sA_Double();    
            try{
                    if(data.SymbolsTableAraña[i][2].equals("26"))
                        {
                            indice = i;
                            banError = true;
                        }
                    }catch(NullPointerException e){}
            
            if(banError == true){    
                try{
                    while(!";".equals(data.SymbolsTableAraña[indice][0]) 
                            && !"float".equals(data.SymbolsTableAraña[indice][0])
                            && !"char".equals(data.SymbolsTableAraña[indice][0])     
                            && !"int".equals(data.SymbolsTableAraña[indice][0])
                            && !"bool".equals(data.SymbolsTableAraña[indice][0])
                            && !"42".equals(data.SymbolsTableAraña[indice][2])
                            && !"double".equals(data.SymbolsTableAraña[indice+1][0])
                            )
                    {
                        try{
                        cadena = cadena + data.SymbolsTableAraña[indice][0];
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
                if(data.SymbolsTableAraña[indice][0].equals(";") && banError)
                {
                   try{
                    cadena = cadena + ";";
                    terminado = Double.Estado(cadena);
                   }catch(NullPointerException e){}   
                }
                }catch(ArrayIndexOutOfBoundsException e){}

                if(terminado == false && banError && cadena != null)
                {
                    Interface.errors.append("Error en declaracion Double: " + cadena + "\n");
                }   
                
                terminado = false;
                banError = false;
                primeravez = true;
                cadena = null;
            }    
        }
    }
    
    public void Bool(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
                
        for(int i = 0; i<data.count_symbolsAraña; i++){
            Bool = new sA_Bool();
            try{
                if(data.SymbolsTableAraña[i][2].equals("27"))
                    {
                        indice = i;
                        banError = true;
                    }
                }catch(NullPointerException e){}
           
            if(banError == true){
                try{
                    while(!";".equals(data.SymbolsTableAraña[indice][0]) 
                            && !"float".equals(data.SymbolsTableAraña[indice][0])
                            && !"char".equals(data.SymbolsTableAraña[indice][0])     
                            && !"int".equals(data.SymbolsTableAraña[indice][0])
                            && !"double".equals(data.SymbolsTableAraña[indice][0])
                            && !"42".equals(data.SymbolsTableAraña[indice][2])
                            && !"bool".equals(data.SymbolsTableAraña[indice+1][0])
                            )
                    {
                        try{
                        cadena = cadena + data.SymbolsTableAraña[indice][0];
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
                if(data.SymbolsTableAraña[indice][0].equals(";") && banError)
                {
                   try{
                    cadena = cadena + ";";
                    terminado = Bool.Estado(cadena);
                   }catch(NullPointerException e){}   
                }
                }catch(ArrayIndexOutOfBoundsException e){}

                if(terminado == false && banError && cadena != null)
                {
                    Interface.errors.append("Error en declaracion Bool: " + cadena + "\n");
                }   
                
                terminado = false;
                banError = false;
                primeravez = true;
                cadena = null;
            }
        }    
    }
    
    public void Operaciones(){
        int indice = 0;
        boolean terminado = false;
        boolean banError = false;
        boolean primeravez = true;
        cadena = null;
        
        for(int i = 0; i<data.count_symbolsAraña; i++){
            Oper = new sA_Operaciones();
            try{
                if(data.SymbolsTableAraña[i][2].equals("42"))
                    {
                        indice = i;
                        banError = true;
                    }
                }catch(ArrayIndexOutOfBoundsException|NullPointerException e){}
           
            if(banError == true){
                try{
                    while(!";".equals(data.SymbolsTableAraña[indice][0])
                            && !"float".equals(data.SymbolsTableAraña[indice][0])
                            && !"char".equals(data.SymbolsTableAraña[indice][0])     
                            && !"int".equals(data.SymbolsTableAraña[indice][0])
                            && !"double".equals(data.SymbolsTableAraña[indice][0])
                            && !"42".equals(data.SymbolsTableAraña[indice+1][2])
                            && !"bool".equals(data.SymbolsTableAraña[indice+1][0])
                            )
                    {
                        try{
                       // JOptionPane.showMessageDialog(null, data.SymbolsTableAraña[indice][0]);
                        cadena = cadena + data.SymbolsTableAraña[indice+1][0];
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
                if(data.SymbolsTableAraña[indice][0].equals(";") && banError)
                {
                   try{
                    terminado = Oper.Estado(cadena);
                    //JOptionPane.showMessageDialog(null, terminado);
                   }catch(NullPointerException e){}   
                }
                }
                catch(ArrayIndexOutOfBoundsException| NullPointerException e){}

                if(terminado == false && banError && cadena != null)
                {
                    Interface.errors.append("Error en operacion: " + cadena + "\n");
                }   
                
                terminado = false;
                banError = false;
                primeravez = true;
                cadena = null;
            }    
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