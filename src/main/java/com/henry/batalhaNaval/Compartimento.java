/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.henry.batalhaNaval;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Henry
 * Classe para criar os compartimentos do tabuleiro
 */

public class Compartimento {
    protected Navio navio;
    protected int id;
    protected int width;
    protected boolean cover = true;
    protected int x;
    protected int y;
    
    public Compartimento(Navio navio, int id) {
        this.navio = navio;
        this.id = id;
        this.width = 40;
        x = 0;
        y = 0;
    }
    
    public void desenha(Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        
        if (this.cover) {
            g.setColor(Color.gray);
            g.fillRect(x, y, width, width);
        }
        
        else {
            g.setColor(navio.getCor());
            g.fillRect(x, y, width, width);
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
}
