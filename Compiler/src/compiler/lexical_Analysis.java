/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Interface.Interface;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego Olayo
 */
public class lexical_Analysis {
    private Interface Interface;
    private data data;

    private String patron = ("(include|stdio.h|stdlib.h|main|for|while|double|if|int|float|do|bool|char|String|cout|printf|scanf|else|ope:|%d|%f|%s|%c)|"//palabras reservadas
                            + "([a-zA-Z]+)|"//variables
                            + "([#|(|)|<|>|[|]|{|}|+|-|*|/|;|=|'|,|.|\"|&]+)|"//simbolos
                            + "([0-9]+)");//numeros

    public void set_lexical_Analysis(Interface Interface, data data){
        this.Interface = Interface;
        this.data = data;
    }
    
    public void proccess(){
            String codigo = Interface.Codigo.getText();
            Pattern pattern = Pattern.compile(patron);
            Matcher matcher = pattern.matcher(codigo);

            while(matcher.find()){
                String token1 = matcher.group(1);
                if(token1 != null)
                {
                    System.out.println("palabra reservada: "+token1);
                    data.insert_SymbolsTable(token1, "WR");
                }

                String token2 = matcher.group(2);
                if(token2 != null)
                {
                    System.out.println("variable: "+token2);
                    data.insert_SymbolsTable(token2, "variable");
                }

                String token3 = matcher.group(3);
                if(token3 != null)
                {
                    System.out.println("simbolo: "+token3);
                    data.insert_SymbolsTable(token3, "Simbol");
                }

                String token4 = matcher.group(4);
                if(token4 != null)
                {
                    System.out.println("numero: "+token4);
                    data.insert_SymbolsTable(token4, "numero");
                }     
            }
    }
}
