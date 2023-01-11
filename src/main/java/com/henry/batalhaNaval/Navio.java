/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.henry.batalhaNaval;

import java.awt.Color;

/**
 * @author Henry
 * Classe para criar os navios do jogo
 */

class Navio {
    protected Color cor;
    
    public Navio(Color cor) {
        this.cor = cor;
    }
    
    public Color getCor() {
        return this.cor;
    }
}
