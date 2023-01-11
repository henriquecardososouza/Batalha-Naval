/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.henry.batalhaNaval;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Henry
 * Classe para criar a lógica principal do jogo
 */

public class Game extends Canvas implements Runnable, MouseListener{
    protected static final int WIDTH = 850;
    protected static final int HEIGHT = 510;
    
    protected static final int NColunas_Tabuleiro = 10;
    protected static final int NLinhas_Tabuleiro = 10;
    
    protected static final int tamanho_Submarino = 2;
    protected static final int tamanho_Contratorpedeiro = 3;
    protected static final int tamanho_PortaAviao = 5;
    protected static final int tamanho_NavioTanque = 4;
    
    protected Compartimento[][] tabuleiro = new Compartimento[NLinhas_Tabuleiro][NColunas_Tabuleiro];
    
    protected int mX;
    protected int mY;
    protected boolean click = false;
    
    protected boolean venceu = false;
    protected boolean fim = false;
    
    Legenda l = new Legenda();
    
    public Game() {
        Dimension dimensao = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(dimensao);
        this.addMouseListener(this);
    }
    
    public void atualiza() {
        if (!fim) {
           inputs();

            if (verificaFim()) {
                fim = true;
            } 
        }
    }
    
    public void desenha() {
        BufferStrategy bs = this.getBufferStrategy();
        
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < NLinhas_Tabuleiro; i++) {
            for (int j = 0; j < NColunas_Tabuleiro; j++) {
                tabuleiro[i][j].desenha(g, i * 50 + 10, j * 50 + 10);
            }
        }
        
        l.desenha(g);
        
        if (fim) {
            Color c = new Color(0, 0, 0, 190);
            g.setColor(c);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            
            if (venceu) {
                g.drawString("Você venceu!", WIDTH / 2 - 100, HEIGHT / 2);
            }
            
            else {
                g.drawString("Você perdeu!", WIDTH / 2 - 100, HEIGHT / 2);
            }
            
            g.drawString("<<Pressione para recomeçar>>", WIDTH / 2 - 220, HEIGHT / 2 + 100);
        }
        
        bs.show();
    }

    @Override
    public void run() {
        this.criaTabuleiro();
        
        while (true) {
            this.atualiza();
            this.desenha();
            
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void criaTabuleiro() {
        for (int i = 0; i < NLinhas_Tabuleiro; i++) {
            for (int j = 0; j < NColunas_Tabuleiro; j++) {
                tabuleiro[i][j] = null;
            }
        }
        
        preencheTabuleiro();
    }
    
    private void preencheTabuleiro() {
        Navio n = null;
        
        int quant_Submarino = 4;
        int quant_Contratorpedeiro = 3;
        int quant_NavioTanque = 2;
        int quant_PortaAviao = 1;
        int quant_Bombas = 30;
        
        int tamanho = 0;
        int posX;
        int posY;
        
        String orientacao;
        
        Random r = new Random();
        
        while(quant_Submarino > 0 || quant_Contratorpedeiro > 0 || quant_NavioTanque > 0 || quant_PortaAviao > 0) {
            posX = r.nextInt(10);
            posY = r.nextInt(10);
            
            if (r.nextBoolean()) {
                orientacao = "Horizontal";
            }
            
            else {
                orientacao = "Vertical";
            }
            
            if (quant_Submarino > 0) {
                tamanho = tamanho_Submarino;
                n = new Submarino();
            }
            
            else if (quant_NavioTanque > 0) {
                tamanho = tamanho_NavioTanque;
                n = new NavioTanque();
            }
            
            else if (quant_PortaAviao > 0) {
                tamanho = tamanho_PortaAviao;
                n = new PortaAviao();
            }
            
            else if (quant_Contratorpedeiro > 0) {
                tamanho = tamanho_Contratorpedeiro;
                n = new Contratorpedeiro();
            }
            
            if (verificaPosicao(posX, posY, tamanho, orientacao)) {
                for (int i = 0; i < tamanho; i++) {
                    if ("Horizontal".equals(orientacao)) {
                        tabuleiro[posX + i][posY] = new Compartimento(n , i);
                    }
                    
                    else {
                        tabuleiro[posX][posY + i] = new Compartimento(n, i);
                    }
                }
                
                switch (tamanho) {
                    case tamanho_Submarino -> quant_Submarino --;
                    case tamanho_NavioTanque -> quant_NavioTanque --;
                    case tamanho_Contratorpedeiro -> quant_Contratorpedeiro --;
                    case tamanho_PortaAviao -> quant_PortaAviao --;
                    default -> {
                    }
                }
            }
        }
        
        while(quant_Bombas > 0) {
            posX = r.nextInt(10);
            posY = r.nextInt(10);
            orientacao = "Horizontal";
            n = new Bomba();

            if (verificaPosicao(posX, posY, 1, orientacao)) {
                tabuleiro[posX][posY] = new Compartimento(n , 0);
                quant_Bombas --;
            }
        }
        
        for (int i = 0; i < NColunas_Tabuleiro; i++) {
            for (int j = 0; j < NLinhas_Tabuleiro; j++) {
                if (verificaPosicao(j, i, 1, "Horizontal")) {
                    tabuleiro[j][i] = new Compartimento(new Agua(), 0);
                }
            }
        }
    }
    
    private boolean verificaPosicao(int x, int y, int tamanho, String orientacao) {
        if ("Horizontal".equals(orientacao)) {
            if (NLinhas_Tabuleiro < x + tamanho) {
                return false;
            }
            
            else {
                for (int i = 0; i < tamanho; i++) {
                    if (tabuleiro[x + i][y] != null) {
                        return false;
                    }
                }
            }
        }
        
        else {
            if (NColunas_Tabuleiro < y + tamanho) {
                return false;
            }
            else {
                for (int i = 0; i < tamanho; i++) {
                    if (tabuleiro[x][y + i] != null) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean verificaFim() {
        int tamanho = 0;
        
        Navio prova = new Agua();
        
        Navio b = new Bomba();
        Navio s = new Submarino();
        Navio c = new Contratorpedeiro();
        Navio p = new PortaAviao();
        Navio n = new NavioTanque();
        
        for (int i = 0; i < NColunas_Tabuleiro; i++) {
            for (int j = 0; j < NLinhas_Tabuleiro; j++) {
                if (!tabuleiro[i][j].cover) {
                    if (tabuleiro[i][j].navio.getClass() == b.getClass()) {
                        venceu = false;
                        return true;
                    }
                    
                    else if (prova.getClass() == tabuleiro[i][j].navio.getClass() && prova.getClass() != new Agua().getClass()){
                        tamanho ++;
                        
                        if (tabuleiro[i][j].navio.getClass() == s.getClass() && tamanho >= tamanho_Submarino - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[i][j].navio.getClass() == c.getClass() && tamanho >= tamanho_Contratorpedeiro - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[i][j].navio.getClass() == p.getClass() && tamanho >= tamanho_PortaAviao - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[i][j].navio.getClass() == n.getClass() && tamanho >= tamanho_NavioTanque - 1) {
                            venceu = true;
                            return true;
                        }
                    }
                        
                    else {
                        prova = tabuleiro[i][j].navio;
                        tamanho = 0;
                    }
                }
            }
        }
        
        tamanho = 0;
        prova = new Agua();
        
        for (int i = 0; i < NColunas_Tabuleiro; i++) {
            for (int j = 0; j < NLinhas_Tabuleiro; j++) {
                if (!tabuleiro[j][i].cover) {
                    if (tabuleiro[j][i].navio.getClass() == b.getClass()) {
                        venceu = false;
                        return true;
                    }
                    
                    else if (prova.getClass() == tabuleiro[j][i].navio.getClass() && prova.getClass() != new Agua().getClass()){
                        tamanho ++;
                        
                        if (tabuleiro[j][i].navio.getClass() == s.getClass() && tamanho == tamanho_Submarino - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[j][i].navio.getClass() == c.getClass() && tamanho == tamanho_Contratorpedeiro - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[i][j].navio.getClass() == p.getClass() && tamanho == tamanho_PortaAviao - 1) {
                            venceu = true;
                            return true;
                        }

                        else if (tabuleiro[i][j].navio.getClass() == n.getClass() && tamanho == tamanho_NavioTanque - 1) {
                            venceu = true;
                            return true;
                        }
                    }
                        
                    else {
                        prova = tabuleiro[j][i].navio;
                        tamanho = 0;
                    }
                }
            }
        }
        
        return false;
    }
    
    private void inputs() {
        if (click) {
            for (int i = 0; i < NColunas_Tabuleiro; i++) {
                for (int j = 0; j < NLinhas_Tabuleiro; j++) {
                    if (mX >= tabuleiro[i][j].getX() &&
                        mX <= tabuleiro[i][j].getX() + tabuleiro[i][j].width &&
                        mY >= tabuleiro[i][j].getY() &&
                        mY <= tabuleiro[i][j].getY() + tabuleiro[i][j].width) {
                        tabuleiro[i][j].cover = false;
                        break;
                    }
                }
            }
            
            click = false;
        }
    }
    
    private void reset() {
        click = false;
        fim = false;
        venceu = false;
        
        criaTabuleiro();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (fim) {
            reset();
        }
        
        else {
            mX = e.getX();
            mY = e.getY();
            click = true; 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
