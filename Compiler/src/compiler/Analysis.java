/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;
import Interface.Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JOptionPane;
/**
 *
 * @author Diego Olayo
 */
public class Analysis implements ActionListener{
    private Interface Interface;
    private lexical_Analysis lA;
    private sintactic_Analysis sA;
    private data data;
    
    public Analysis(Interface Interface, data data, lexical_Analysis lA, sintactic_Analysis sA){
        this.Interface = Interface;
        this.data = data;
        this.lA = lA;
        this.sA = sA;
        this.Interface.Start.addActionListener(this);
        this.Interface.jButton2.addActionListener(this);
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        Object boton = e.getSource();
        if(boton == Interface.jButton2)
        {
            data.solo_probando();
        }
        else
        {
            data.setData();
            lA.set_lexical_Analysis(Interface, data);
            data.setData();
            lA.proccess();
            
            sA.set_sintactic_Analysis(Interface, data);
            sA.proccess();//borra panel de errores
            sA.cabeceras();
            sA.Main();
            sA.While();
            sA.llaves();
            sA.print_errors();//imprime vector de errores
            
        }
    }
}