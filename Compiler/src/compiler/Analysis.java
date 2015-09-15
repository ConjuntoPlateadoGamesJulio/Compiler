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

    public Analysis(Interface Interface, data data, lexical_Analysis lA, sintactic_Analysis sA) {
        this.Interface = Interface;
        this.data = data;
        this.lA = lA;
        this.sA = sA;
        this.Interface.PROBANDO.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ANALISIS LEXICO
        data.setData();//cada vez que inicia reinicia en data para la tabla de simbolos
        lA.set_lexical_Analysis(Interface, data);//constructor
        lA.proccess();//mete todo a la tabla de simbolos
        
        //ANALISIS SINTACTICO
        sA.setSintacticAnalysis(Interface, data);//constructor
        sA.inicializar_y_limpiar();//inicializa y borra el panel de errores
        sA.cabeceras();//evalua las librerias
        
        //IMRPIME TABLA DE SIMBOLOS Y MUESTRA ERRORES
        sA.print_errors();
        data.solo_probando();
    }
}
