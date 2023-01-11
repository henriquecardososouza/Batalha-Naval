/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.henry.batalhaNaval;

import javax.swing.JFrame;

/**
 * @author Henry
 * Programa para criar um jogo de batalha naval
 */

public class Main {

    public static void main(String[] args) {
        Game jogo = new Game();
        
        JFrame tela = new JFrame("Batalha Naval");
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.add(jogo);
        tela.pack();
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);
        
        tela.setVisible(true);
        
        new Thread(jogo).start();
    }
}
