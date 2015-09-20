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
    private sA_Float Float;
    private sA_Bool Bool;
    private sA_Operaciones Ope;
    public boolean hayPrintf = false;
    public boolean Stdio = false;
    public String errores [] = new String[100];
    public int indice_errores = -1; 
    private int numeroElse=0;
    private int numeroIf=0;
    private int indiceIf=0;
    private int indiceElse=0;
    private int indiceMain = 0;
    private boolean hayMain = false;
    
    public String[] Vars;
    public int contVars;
            
    public void setSintacticAnalysis(Interface Interface, data data, 
            sA_Int Int, sA_Float Float, sA_Bool Bool, sA_Operaciones Ope) {
        this.Interface = Interface;
        this.data = data;
        this.Int = Int;
        this.Float = Float;
        this.Bool = Bool;
        this.Ope = Ope;
        
        this.contVars = 0;
        this.Vars = new String [50];
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
        this.indiceElse=10000000;
        this.indiceIf=0;
        this.indiceMain = 0;
        this.hayMain = false;
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
     
    public void Main(){
        int indice = 0;
        int full = 0;
        int count = 0;
        boolean Main = false;
        String automata[] = {"24", "19", "2", "3", "8"};
        int k = - 1;
        
        for(int i = 0; i < data.count_symbols; i ++)//busca el int y el main juntos
        {
            try{
                if(data.SymbolsTable[i][2].equals("24") && data.SymbolsTable[i + 1][2].equals("19"))
                {
                    full = 5;
                    count = count + 1;
                    if(count <= 1)
                    {
                        indice = i;//se obtiene el indice
                    }
                    Main = true;
                }
            }catch(NullPointerException e){}
            
            if(Main == true && count == 1)//si hubo "int main" y es el unico
            {
                for(int j = indice; j < (indice + 5); j++)
                {
                    k = k + 1;
                    try {
                        if(data.SymbolsTable[j][2].equals(automata[k]))//se valida si cumple con todo
                        {
                            full = full - 1;
                        }
                    }catch(NullPointerException|ArrayIndexOutOfBoundsException e){};
                }
            }
        }
        if(Main == true && full == 0)
        {
            this.hayMain = true;
            this.indiceMain = indice;
        }
        if(count > 1)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "Error.-Hay mas de un metodo main";
        }
        if(Main == true && full != 0)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "Error.-Metodo main";
        }
        if(Main == false)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "Error.-No hay metodo main";
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
                    if (in<1) {
                        indiceIf=i;
                    }
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
        int llave=0,in=0;
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
                            if (in<1) {
                                indiceElse=i;
                            }
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
        if(fin !=true && entra!=false  || dobleElse==true || numeroElse> numeroIf || noElse==true || indiceElse<indiceIf){
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en else";
        }
    }
    public void automataFor(){
        int indice=0,indiceFor=0,numeroFor=0,indiceLlave=0;
        int llaves[]=new int [1000];
        boolean entra=false,fin=false;
        boolean For[]=new boolean [1000];
        boolean anidadoFor=false,operadores=false,var=false;
        String variable[] = new String[1000];
        String simbolos[]={"31","4","5"};
        for (int i = 0; i < llaves.length; i++) {
            llaves[i]=0;
            For[i]=false;
            variable[i]=null;
        }
        for (int i = 0; i < data.count_symbols; i++) {
            try {
                if (data.SymbolsTable[i][2].equals("20")) {
                    indice=i;
                    entra=true;
                    if (numeroFor<1) {
                        indiceFor=i;
                    }
                    variable[numeroFor]=data.SymbolsTable[indice+2][0];
                    numeroFor++;
                }
                if (data.SymbolsTable[indiceFor][2].equals("8")) { // busca llaves {
                    llaves[indiceLlave]=1;     
                    indiceLlave++;
                } 
                if (data.SymbolsTable[indice+1][2].equals("2")) { // (
                    if (llaves[0]==1 && numeroFor>1) {
                        anidadoFor=true;
                    }
                    for(int j=0;j<numeroFor-1;j++){
                        if (data.SymbolsTable[indice+2][0].equals(variable[j])) { // revisa que la variable del for actual
                            var=true;                                            // no se haya utilizado antes en un for
                        }                                                        // anterior si es anidado
                    }
                    if (anidadoFor==true && data.SymbolsTable[indice+2][1].equals("variable")&& var==false) {
                            anidadoFor=false;                                    // es verdadero si la variable esta 
                            var=false;                                           // repetida en for anidado
                    }
                    if (data.SymbolsTable[indice+2][1].equals("variable")&& anidadoFor==false) { // indices
                        if (data.SymbolsTable[indice+3][2].equals("31")) {                       //  =
                            if (data.SymbolsTable[indice+4][1].equals("variable")||
                                    data.SymbolsTable[indice+4][1].equals("numero")) {
                                if (data.SymbolsTable[indice+5][2].equals("14")) {
                                    if (data.SymbolsTable[indice+6][1].equals("variable") //asegura que sea la misma variable 
                                         && data.SymbolsTable[indice+6][0].equals(data.SymbolsTable[indice+2][0])) {
                                        try {
                                            if (data.SymbolsTable[indice+8][2].equals("31")) {
                                                for(int j=0;j<3;j++){
                                                    if (data.SymbolsTable[indice+7][2].equals(simbolos[j])) {// = < >
                                                        operadores=true;
                                                    }
                                                }
                                                if(data.SymbolsTable[indice+9][1].equals("variable")
                                                    || data.SymbolsTable[indice+9][1].equals("numero") && operadores==true){
                                                    if(data.SymbolsTable[indice+10][2].equals("14")){
                                                      if (data.SymbolsTable[indice+11][1].equals("variable") //asegura que sea la misma variable i
                                                         && data.SymbolsTable[indice+11][0].equals(data.SymbolsTable[indice+2][0])){
                                                          if(data.SymbolsTable[indice+12][2].equals("10")) { 
                                                              if(data.SymbolsTable[indice+13][2].equals("10")){
                                                                if(data.SymbolsTable[indice+14][2].equals("3")){
                                                                    if (data.SymbolsTable[indice+15][2].equals("8")) {
                                                                        fin=true;
                                                                        For[numeroFor]=true;
                                                                    }
                                                                }
                                                              }
                                                          }
                                                      }
                                                    }
                                                }
                                            }
                                        } catch (NullPointerException e) { }
                                        if(data.SymbolsTable[indice+7][2].equals("4")||
                                            data.SymbolsTable[indice+7][2].equals("5")){ //simbolo < >
                                            if(data.SymbolsTable[indice+8][1].equals("variable")
                                                || data.SymbolsTable[indice+8][1].equals("numero")){
                                                if(data.SymbolsTable[indice+9][2].equals("14")){ // ;
                                                    if (data.SymbolsTable[indice+10][1].equals("variable") //asegura que sea la misma variable i
                                                        && data.SymbolsTable[indice+10][0].equals(data.SymbolsTable[indice+2][0])){
                                                        if(data.SymbolsTable[indice+11][2].equals("10")) {
                                                            if(data.SymbolsTable[indice+12][2].equals("10")){
                                                                if(data.SymbolsTable[indice+13][2].equals("3")){
                                                                    if (data.SymbolsTable[indice+14][2].equals("8")) {
                                                                        fin=true;
                                                                        For[numeroFor]=true;
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
                }
                try{
                    if (data.SymbolsTable[indiceFor][2].equals("9")) { // busca llaves {
                        indiceLlave--;
                        llaves[indiceLlave]=0;                         // y quita de la lista
                    }
                }catch(ArrayIndexOutOfBoundsException ex){indiceLlave=0;}
            } catch (NullPointerException e) { }
            indiceFor++;
        }
        boolean noFor=false;  
        for (int i = 1; i <= numeroFor; i++) {  // aqui valido que todos los for
            if (For[i]==false) {                // se terminaron con exito
                noFor=true;
            }
        }
        if(fin !=true && entra!=false  || noFor==true ){
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "error en for";
        }
    }
    public void llaves() {
        int sumaLlaves = 0;
        
        if(hayMain == true)
        {
            try{
                if(data.SymbolsTable[data.count_symbols - 1][2].equals("9"))
                {
                    for(int i = (data.count_symbols - 2); i >= (indiceMain + 5); i --)
                    { 
                        if(data.SymbolsTable[i][2].equals("9"))
                        {
                            sumaLlaves = sumaLlaves + 1;
                        }
                        if(data.SymbolsTable[i][2].equals("8"))
                        {
                            sumaLlaves = sumaLlaves - 1;
                        }
                    }
                }
                else
                {
                    this.indice_errores = indice_errores + 1;
                    this.errores[indice_errores] = "Error.-En llaves";
                }
            }catch(NullPointerException e){}
        }
        if(sumaLlaves != 0)
        {
            this.indice_errores = indice_errores + 1;
            this.errores[indice_errores] = "Error.- en llaves";
        }
    }
    
    public void Printf(){
        int indice = 0;
        int full = 0;
        boolean printf = false;
        String automata[] = {"30","2","33","33","3","14"};
        
        
        int texto = 0;
        for(int i = 0; i < data.count_symbols; i++)
        {
            int k = 0;
            printf = false;
            full = 0;
            try{
                if(data.SymbolsTable[i][2].equals("30"))//encontro un printf
                {
                    k = -1;
                    full = 6;
                    indice = i;
                    texto = 0;
                    printf = true;
                }
            }catch(NullPointerException e){}
            if(printf == true)
            {
                for(int j = indice; j < (indice + 3); j++)//valida las 3 primeras posiciones
                {
                    k = k + 1;
                    try{
                        if(data.SymbolsTable[j][2].equals(automata[k]))
                        {
                            full = full - 1;
                        }
                    }catch(NullPointerException e){}
                }
                for(int j = (indice + 3); j < data.count_symbols; j++)
                {
                    if(!data.SymbolsTable[j][1].equals("Simbolo"))
                    {
                        texto = texto + 1;
                    }
                    try{
                        if(data.SymbolsTable[j][2].equals("33"))
                            break;
                    }catch(NullPointerException e){}
                }
                if(full == 3)
                {
                    for(int j = (indice + texto + 3); j < (indice + texto + 6); j ++)
                    {
                        k = k + 1;
                        try{
                            if(data.SymbolsTable[j][2].equals(automata[k]))
                            {
                                full = full - 1 ;
                            }
                        }catch(NullPointerException e){}
                    }
                }
            }
            if(printf == true && full == 0)
            {
                this.hayPrintf = true;
            }
            if(printf == true && full != 0)
            {
                this.indice_errores = indice_errores + 1;
                this.errores[indice_errores] = "Error.-Printf";
            }       
        }
    }
    public void automataScanf(){
        int indice = 0;
        boolean entra = false;
        boolean operadores = false;
        boolean fin = false;
         boolean datos = false;
        String tiposDatos[]={"38","39","40","41"};
        
            for(int i = 0; i < data.count_symbols; i ++){
                try{
                    if(data.SymbolsTable[i][2].equals("42")) //scanf
                    {
                            indice=i;
                            entra=true;
                    }
                    if(entra == true && data.SymbolsTable[indice+1][2].equals("2")){ //(
                        if(data.SymbolsTable[indice+2][2].equals("37")){ //"
                            for(int j=0;j<4;j++){
                                if(data.SymbolsTable[indice+3][2].equals(tiposDatos[j])){//%d %f %c %s
                                   datos=true; 
                                } 
                            } 
                            if (data.SymbolsTable[indice+4][2].equals("37") && datos== true ) { //"
                                if(data.SymbolsTable[indice+5][2].equals("34")){//,
                                    if(data.SymbolsTable[indice+6][2].equals("43")){// &
                                        if(data.SymbolsTable[indice+7][1].equals("variable") //vaariable numero
                                            ||data.SymbolsTable[indice+7][1].equals("numero")){
                                            if (data.SymbolsTable[indice+8][2].equals("3")) { //)
                                                if (data.SymbolsTable[indice+9][2].equals("14")) { // ;
                                                        fin=true;
                                                         // JOptionPane.showMessageDialog(null,"scanf");
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
    public void Int(){
        int indice = 0;
        boolean terminado = false;
        boolean banEncontrado = false;
        boolean banError = false;
        boolean primeravez = true;
        String cadena = null;
        String variable = "";
        
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
                cadena = null;
                try{
                    while(!";".equals(data.SymbolsTable[indice][0]) 
                            && !"float".equals(data.SymbolsTable[indice][0])
                            && !"char".equals(data.SymbolsTable[indice][0])     
                            && !"double".equals(data.SymbolsTable[indice][0])
                            && !"bool".equals(data.SymbolsTable[indice][0])
                            && !"44".equals(data.SymbolsTable[indice][2])
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
                            variable = data.SymbolsTable[indice+1][0];
                            if(data.SymbolsTable[indice+2][1].equals("numero"))
                            {
                                variable = variable + data.SymbolsTable[indice+2][0];
                            }
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
                    Int.Init_Int(cadena);
                    terminado = Int.Estado();
                    if(terminado)
                    {
                        Vars[contVars] = variable;
                        contVars++;
                        //JOptionPane.showMessageDialog(null, ints[cont-1]);
                    }
                    //JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(terminado == false && banEncontrado && banError)
                    {
                        //JOptionPane.showMessageDialog(null, banEncontrado+ "Por que entra  no mamar" + terminado);
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error en Int";
                    }
                
                banError = false;
                terminado = false;
                banEncontrado = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
    
    public void Float(){
        int indice = 0;
        boolean terminado = false;
        boolean banEncontrado = false;
        boolean banError = false;
        boolean primeravez = true;
        String cadena = null;
        String variable = "";
        
        for(int i = 0; i<data.count_symbols; i++){
            try{
                if(data.SymbolsTable[i][2].equals("25"))
                    {
                        indice = i;
                        banEncontrado = true;
                    }
                }catch(NullPointerException e){}
            
            if(banEncontrado == true)
            {        
                cadena = null;
                try{
                    while(!";".equals(data.SymbolsTable[indice][0]) 
                            && !"float".equals(data.SymbolsTable[indice+1][0])
                            && !"char".equals(data.SymbolsTable[indice][0])     
                            && !"double".equals(data.SymbolsTable[indice][0])
                            && !"bool".equals(data.SymbolsTable[indice][0])
                            && !"44".equals(data.SymbolsTable[indice][2])
                            && !"19".equals(data.SymbolsTable[indice+1][2])
                            && !"int".equals(data.SymbolsTable[indice][0])
                            )
                    {
                        banError = true;
                        try{
                        cadena = cadena + data.SymbolsTable[indice][0];
                        if(primeravez)
                        {
                            cadena = cadena.substring(4);
                            variable = data.SymbolsTable[indice+1][0];
                            if(data.SymbolsTable[indice+2][1].equals("numero"))
                            {
                                variable = variable + data.SymbolsTable[indice+2][0];
                            }
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
                    Float.Init_Float(cadena);
                    terminado = Float.Estado();
                    if(terminado)
                    {
                        Vars[contVars] = variable;
                        contVars++;
                        //JOptionPane.showMessageDialog(null, ints[cont-1]);
                    }
                    //JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(terminado == false && banEncontrado && banError)
                    {
                        //JOptionPane.showMessageDialog(null, banEncontrado+ "Por que entra  no mamar" + terminado);
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error en Float";
                    }
                
                banError = false;
                terminado = false;
                banEncontrado = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
    
    public void Bool(){
        int indice = 0;
        boolean terminado = false;
        boolean banEncontrado = false;
        boolean banError = false;
        boolean primeravez = true;
        String cadena = null;
        String variable = "";
        
        for(int i = 0; i<data.count_symbols; i++){
            try{
                if(data.SymbolsTable[i][2].equals("27"))
                    {
                        indice = i;
                        banEncontrado = true;
                    }
                }catch(NullPointerException e){}
            
            if(banEncontrado == true)
            {        
                cadena = null;
                try{
                    while(!";".equals(data.SymbolsTable[indice][0]) 
                            && !"float".equals(data.SymbolsTable[indice][0])
                            && !"char".equals(data.SymbolsTable[indice][0])     
                            && !"double".equals(data.SymbolsTable[indice][0])
                            && !"bool".equals(data.SymbolsTable[indice+1][0])
                            && !"44".equals(data.SymbolsTable[indice][2])
                            && !"19".equals(data.SymbolsTable[indice+1][2])
                            && !"int".equals(data.SymbolsTable[indice][0])
                            )
                    {
                        banError = true;
                        try{
                        cadena = cadena + data.SymbolsTable[indice][0];
                        if(primeravez)
                        {
                            cadena = cadena.substring(4);
                            variable = data.SymbolsTable[indice+1][0];
                            if(data.SymbolsTable[indice+2][1].equals("numero"))
                            {
                                variable = variable + data.SymbolsTable[indice+2][0];
                            }
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
                    Bool.Init_Bool(cadena);
                    terminado = Bool.Estado();
                    if(terminado)
                    {
                        Vars[contVars] = variable;
                        contVars++;
                        //JOptionPane.showMessageDialog(null, ints[cont-1]);
                    }
                    //JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(terminado == false && banEncontrado && banError)
                    {
                        //JOptionPane.showMessageDialog(null, banEncontrado+ "Por que entra  no mamar" + terminado);
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error en Bool";
                    }
                
                banError = false;
                terminado = false;
                banEncontrado = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
    
    public void Operaciones(){
        int indice = 0;
        boolean terminado = false;
        boolean banEncontrado = false;
        boolean banError = false;
        boolean primeravez = true;
        boolean encontrado = false;
        String cadena = null;
        String variable = "";
        
        for(int i = 0; i<data.count_symbols; i++){
            try{
                if(data.SymbolsTable[i][2].equals("44"))
                    {
                        indice = i;
                        banEncontrado = true;
                    }
                }catch(NullPointerException e){}
            
            if(banEncontrado == true)
            {        
                cadena = null;
                try{
                    while(!";".equals(data.SymbolsTable[indice][0]) 
                            && !"float".equals(data.SymbolsTable[indice][0])
                            && !"char".equals(data.SymbolsTable[indice][0])     
                            && !"double".equals(data.SymbolsTable[indice][0])
                            && !"bool".equals(data.SymbolsTable[indice][0])
                            && !"44".equals(data.SymbolsTable[indice+1][2])
                            && !"19".equals(data.SymbolsTable[indice+1][2])
                            && !"int".equals(data.SymbolsTable[indice][0])
                            )
                    {
                        banError = true;
                        try{
                        cadena = cadena + data.SymbolsTable[indice][0];
                        if(primeravez)
                        {
                            cadena = cadena.substring(4);
                            variable = data.SymbolsTable[indice+1][0];
                            if(data.SymbolsTable[indice+2][1].equals("numero"))
                            {
                                variable = variable + data.SymbolsTable[indice+2][0];
                            }
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
                    cadena = cadena.substring(4);
                    Ope.Init_Ope(cadena);
                    terminado = Ope.Estado();
                    for(int a = 0; a < contVars ; a++)
                    {
                        if(variable.equals(Vars[a]))
                        {
                           encontrado = true;
                           break;
                        }
                    }
                    //JOptionPane.showMessageDialog(null, cadena+ "" + terminado);
                   }catch(NullPointerException e){}   
                }
                }catch(NullPointerException|ArrayIndexOutOfBoundsException e){} 
                
                if(encontrado == false)
                {
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error: Variavle no declarada";
                }
                if(terminado == false && banEncontrado && banError)
                    {
                        //JOptionPane.showMessageDialog(null, banEncontrado+ "Por que entra  no mamar" + terminado);
                        this.indice_errores = indice_errores + 1;
                        this.errores[indice_errores] = "Error en Operacion";
                    }
                
                banError = false;
                terminado = false;
                banEncontrado = false;
                primeravez = true;
                cadena = null;
            }
        }
    }
}
