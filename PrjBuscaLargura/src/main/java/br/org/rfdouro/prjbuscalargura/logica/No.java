package br.org.rfdouro.prjbuscalargura.logica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import java.util.LinkedList;

import javax.swing.JButton;

/**
 * classe responsável por ser um nó do grafo
 */
public class No extends JButton {

 private int indice;//indice do nó
 private String nome;//nome do nó
 private No pai;//ponteiro para o nó pai
 private LinkedList listaIndicesLigados = new LinkedList();//lista com os indices dos nós ao qual este está ligado

 /*atributos usados na representação gráfica*/
 /*-----------------------------------------*/
 private double dim = 10;//valor do lado do quadrado que é desenhado para represenatar o nó
 private double posx, posy;//coordenadas do canto superior esquerdo do quadrado (é o x,y do clique do mouse)
 private Rectangle2D rec;//variável para criar um quadrado que será desenhado na tela
 private LinkedList ligacoesCoords = new LinkedList();//lista que guarda as coordenadas dos grafos que fazem ligação com este
 private Color cor = Color.ORANGE;//cor do quadrado
 private Color corLigacao = Color.BLUE;//cor da ligação
 private int opEstilo;//estilo de representação do nó

 /*-----------------------------------------*/

 /**
  * método consrutor vazio
  */
 public No() {
 }

 /**
  * contrutor com atributos
  *
  * @param indice
  * @param nome
  */
 public No(int indice, String nome) {
  this();
  this.setIndice(indice);
  this.setNome(nome);
  this.setPai(null);
 }

 /**
  * contrutor com atributos
  *
  * @param indice
  * @param nome
  * @param pai
  */
 public No(int indice, String nome, No pai) {
  this(indice, nome);
  this.setPai(pai);
 }

 /**
  * sobrescreve o método toString da classe pai de todas Object retorna o nome do nó
  *
  * @return
  */
 public String toString() {
  return this.getNome();
 }

 /**
  * atribui o valor do índice ao nó
  *
  * @param indice
  */
 public void setIndice(int indice) {
  this.indice = indice;
 }

 /**
  * retorna o valor do índice atribuido ao nó
  *
  * @return
  */
 public int getIndice() {
  return indice;
 }

 /**
  * atribui o valor do nome ao nó
  *
  * @param nome
  */
 public void setNome(String nome) {
  this.nome = nome;
 }

 /**
  * retorna o nome do nó
  *
  * @return
  */
 public String getNome() {
  return nome;
 }

 /**
  * atribui o ponteiro para o pai do nó
  *
  * @param pai
  */
 public void setPai(No pai) {
  this.pai = pai;
 }

 /**
  * recupera o pai do nó
  *
  * @return
  */
 public No getPai() {
  return pai;
 }

 /**
  * inclui uma ligacao do nó adiciona o índice do nó ligado na lista de ligações
  *
  * @param indiceLigado
  */
 public void setLigacaoIndice(int indiceLigado) {
  this.listaIndicesLigados.add(indiceLigado);
 }

 /**
  * retorna a lista de ligações do nó
  *
  * @return
  */
 public LinkedList getLigacoesIndices() {
  return this.listaIndicesLigados;
 }

 /**
  * verifica se um determinado nomePai é referente a algum nó que está na cadeia de pais deste nó
  *
  * @param nomePai
  * @return
  */
 public boolean conferePai(String nomePai) {
  boolean ePai = false;
  No p = this.pai;//seleciona o pai deste nó
  //enquanto o pai não for nulo e a variável de controle for false faz o loop
  while (!ePai && p != null) {
   if (p.getNome().equals(nomePai)) {
    ePai = true;//se o nó pai tiver o mesmo nome em questão então a variável de controle é true
   } else {
    p = p.pai;//seleciona o próximo pai
   }
  }
  return ePai;//retorna 
 }

 /*métodos usados na representação gráfica*/
 /*---------------------------------------*/
 /**
  * atribui um valor a coordenada x
  *
  * @param x
  */
 public void setPosX(double x) {
  this.posx = x;
 }

 /**
  * atribui um valor para a coordenada y
  *
  * @param y
  */
 public void setPosY(double y) {
  this.posy = y;
 }

 /**
  * retorna a coorderna x
  *
  * @return
  */
 public double getPosX() {
  return this.posx;
 }

 /**
  * retorna a coordenada y
  *
  * @return
  */
 public double getPosY() {
  return this.posy;
 }

 /**
  * retorna as coordenadas do centro do quadrado representativo do nó calculado em relação á
  * dimensão = dim
  *
  * @return
  */
 public Coords centro() {
  return new Coords(this.posx + (this.dim / 2), this.posy + (this.dim / 2));
 }

 /**
  * retorna as coordenadas do centro do quadrado representativo do nó calculado em relação à
  * variável dim que é passada método sobrescrito bastante usado para a variação de estilo de nós
  *
  * @return
  */
 public Coords centro(int dim) {
  return new Coords(this.posx + (dim / 2), this.posy + (dim / 2));
 }

 /**
  * atribui um valor para a opção de estilo de nó
  *
  * @param op
  */
 public void setOpEstilo(int op) {
  if (op == 0 || op == 1) {
   this.opEstilo = op;
   switch (op) {
    case 0:
     this.dim = 10;//se o estilo é pequeno(0) então diminui o atributo dim = lado do quadrado
     break;
    case 1:
     this.dim = 20;//se o estilo é grande(1) então aumenta o atributo dim = lado do quadrado
   }
  }
 }

 /**
  * desenha o nó na tela gráfica desenha o quadrado que representa o nó
  *
  * @param g
  */
 public void desenha(Graphics g) {
  Graphics2D g2 = (Graphics2D) g;
  Color c = g2.getColor();//guarda a cor original da tela
  switch (this.opEstilo) {
   case 0://estilo pequeno
    g2.setPaint(cor);//seta a cor de desenho para a cor do nó
    this.rec = new Rectangle2D.Double(this.posx, this.posy, this.dim, this.dim);//cria um quadrado
    g2.fill(this.rec);//desenha o quadrado
    if (this.nome != null) {
     g2.setPaint(Color.RED);//seta a cor de desenho para vermelho
     g2.drawString(this.nome, (int) this.posx - 8, (int) this.posy + (int) this.dim + 10);//Desenha nome em baixo do nó
    }
    break;
   case 1://estilo grande
    g2.setPaint(cor);//seta a cor de desenho para a cor do nó
    this.rec = new Rectangle2D.Double(this.posx, this.posy, this.dim, this.dim);//cria um quadrado
    g2.fill(this.rec);//desenha o quadrado
    if (this.nome != null) {
     g2.setPaint(Color.BLACK);//seta a cor de desenho para preto
     g2.drawString("" + this.indice, (int) this.posx + 2, (int) this.posy + 15);//Desenha índice no meio do nó
     g2.setPaint(Color.RED);//seta a cor de desenho para vermelho
     g2.drawString(this.nome, (int) this.posx - 8, (int) this.posy + (int) this.dim + 10);//Desenha nome em baixo do nó
    }
  }
  g2.setPaint(c);//retorna a cor original da tela
 }

 /**
  * desenha as ligações deste nós com os ligados a ele a variável dim é a dimensão do quadrado a ser
  * desenhado ela é passada pela classe Grafo
  *
  * @param dim
  * @param g
  */
 public void desenhaLigacoes(int dim, Graphics g) {
  Coords co = null;//cria um novo conjunto de coordenadas
  Graphics2D g2 = (Graphics2D) g;
  Color c = g2.getColor();//pega a cor atual da parte gráfica
  g2.setPaint(corLigacao);//seta a cor para a cor da ligação
  for (int i = 0; i < this.ligacoesCoords.size(); i++) {
   co = (Coords) ligacoesCoords.get(i);//pega as coordenadas do nó ligado a este
   //desenha uma linha deste nó ao que é ligado a este
   //do centro ao centro
   g2.drawLine((int) this.centro(dim).x, (int) this.centro(dim).y, (int) (co.x + (this.dim) / 2), (int) (co.y + (this.dim) / 2));
  }
  g2.setPaint(c);
 }

 /**
  * metodo para conferir se o clique dado na tela está dentro da área pertencente a este nó x e y
  * são as coordenadas do clique
  *
  * @param x
  * @param y
  * @return
  */
 public boolean confereClique(int x, int y) {
  if (x >= this.posx && x <= this.posx + this.dim
          && y >= this.posy && y <= this.posy + this.dim) {
   return true;
  }
  return false;
 }

 /**
  * método que seta a cor de seleção do nó após ser desenhado, se clicar com o botão esquerdo então
  * está selecionando o nó apenas muda de cor até o próximo clique com o botão direito ele
  * deseliciona quando clica com o botão direito sobre o mesmo nó
  *
  * @param sn
  */
 public void seleciona(boolean sn) {
  if (sn) {
   this.cor = Color.BLUE;//cor de seleção
  } else {
   this.cor = Color.ORANGE;//cor padrão
  }
 }

 /**
  * adiciona um conjunto de coordenadas na lista de ligações
  *
  * @param coord
  */
 public void setLigacaoGrafica(Coords coord) {
  this.ligacoesCoords.add(coord);
 }

 /**
  * verifica se em um determinado par de coordenadas está a área deste ponto
  *
  * @param x
  * @param y
  * @return
  */
 public boolean contemPonto(double x, double y) {
  return (x >= this.posx && x <= this.posx + this.dim
          && y >= this.posy && y <= this.posy + this.dim);
 }

 /**
  * verifica se em um determinado par de coordenadas está a área deste ponto sobrescrito para entrar
  * com o valor da dimensão do quadrado
  *
  * @param x
  * @param y
  * @return
  */
 public boolean contemPonto(double x, double y, int dim) {
  return (x >= this.posx && x <= this.posx + dim
          && y >= this.posy && y <= this.posy + dim);
 }
 /*--------------------------------------*/
}
