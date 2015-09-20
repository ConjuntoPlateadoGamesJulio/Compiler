/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Interface.Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Olayo
 */
public class Analysis implements ActionListener {
    private Interface Interface;
    private lexical_Analysis lA;
    private sintactic_Analysis sA;
    private data data;
    private sA_Int Int;
    private sA_Float Float;
    private sA_Bool Bool;
    private sA_Operaciones Ope;
    private semantic_Analysis semantic;

    public Analysis(Interface Interface, data data, lexical_Analysis lA, sintactic_Analysis sA, 
            sA_Int Int, sA_Float Float, sA_Bool Bool, sA_Operaciones Ope,
            semantic_Analysis semantic) {
        this.Interface = Interface;
        this.data = data;
        this.lA = lA;
        this.sA = sA;
        this.Interface.PROBANDO.addActionListener(this);
        this.semantic = semantic;
        this.Int = Int;
        this.Float = Float;
        this.Bool = Bool;
        this.Ope = Ope;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ANALISIS LEXICO
        data.setData();//cada vez que inicia reinicia en data para la tabla de simbolos
        lA.set_lexical_Analysis(Interface, data);//constructor
        lA.proccess();//mete todo a la tabla de simbolos
        
        //ANALISIS SINTACTICO
        sA.setSintacticAnalysis(Interface, data, Int, Float, Bool, Ope);//constructor
        sA.inicializar_y_limpiar();//inicializa y borra el panel de errores
        sA.cabeceras();//evalua las librerias
        sA.While();//evalua el ciclo While y do-While
        sA.automataIf();
        sA.automataElse();
        sA.Printf();
        sA.automataScanf();
        sA.automataFor();
        sA.Main();//evalua el metodo main
        sA.vali();
        sA.Int();
        sA.Float();
        sA.Bool();
        sA.Operaciones();
        sA.llaves();
        
        //ANALISI SEMANTICO
        semantic.set_semantic_Analysis(Interface, data, sA);
        semantic.checkLibrary();//revisa que este libreria stdio.h para el scanf y printf
        semantic.checkVars();
        
        //IMRPIME TABLA DE SIMBOLOS Y MUESTRA ERRORES
        sA.print_errors();
        data.solo_probando();
    }
}
