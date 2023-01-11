/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.henry.batalhaNaval;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author Henry
 * Classe para desenhar uma legenda no jogo
 */

public class Legenda {
    protected String titulo;
    protected String text1;
    protected String text2;
    protected String text3;
    protected String text4;
    protected String text5;
    protected String text6;
    
    public Legenda() {
        titulo = "<<<Legenda>>>";
        text1 = "Submarino";
        text2 = "Contratorpedeiro";
        text3 = "Porta Avião";
        text4 = "Navio Tanque";
        text5 = "Bomba";
        text6 = "Água";
    }
    
    public void desenha(Graphics g) {
        g.setColor(Color.white);
        
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(titulo, 520, 200);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text1, 600, 260);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text2, 600, 300);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text3, 600, 340);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text4, 600, 380);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text5, 600, 420);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text6, 600, 460);
        
        g.setColor(Color.green);
        g.fillRect(580, 260 - 15, 15, 15);
        g.setColor(Color.magenta);
        g.fillRect(580, 300 - 15, 15, 15);
        g.setColor(Color.yellow);
        g.fillRect(580, 340 - 15, 15, 15);
        g.setColor(Color.white);
        g.fillRect(580, 380 - 15, 15, 15);
        g.setColor(Color.red);
        g.fillRect(580, 420 - 15, 15, 15);
        g.setColor(Color.blue);
        g.fillRect(580, 460 - 15, 15, 15);
    }
}
