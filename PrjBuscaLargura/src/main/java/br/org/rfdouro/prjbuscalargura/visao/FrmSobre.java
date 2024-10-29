package br.org.rfdouro.prjbuscalargura.visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * classe usada apenas para gerar uma janela com informações sobre o programa
 */
public class FrmSobre extends JDialog {

 private JScrollPane jScrollPane1 = new JScrollPane();//painel de barras de rolagem
 private JButton jButton1 = new JButton();//cria botão para OK
 private JTextArea jTextArea1 = new JTextArea();//área de texto com a informação

 /**
  * método construtor
  */
 public FrmSobre() {
  this(null, "", false);
 }

 /**
  * sobrescreve o método construtor com opções a mais
  *
  * @param parent
  * @param title
  * @param modal
  */
 public FrmSobre(Frame parent, String title, boolean modal) {
  super(parent, title, modal);
  try {
   jbInit();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 /**
  * inicio de tudo, cria, posiciona etc
  *
  * @throws Exception
  */
 private void jbInit() throws Exception {
  this.setSize(new Dimension(425, 300));//dimensiona a janela
  this.getContentPane().setLayout(null);//layout nulo para posicionar os objetos
  this.setTitle("Sobre o Programa");//coloca o título da janela
  this.getContentPane().add(jButton1, null);//adiciona o botão na janela
  jScrollPane1.getViewport().add(jTextArea1, null);//adiciona a área de texto no painel
  this.getContentPane().add(jScrollPane1, null);//adiciona o painel na janela
  /**
   * dimensiona e posiciona o painel da área de texto
   */
  jScrollPane1.setBounds(new Rectangle(5, 5, this.getWidth() - 15, this.getHeight() - 80));
  jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);//sempre barras horizontais
  jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//sempre barras verticais
  jButton1.setText("OK");//seta o que é escrito no botão
  jButton1.setBounds(new Rectangle(160, 240, 71, 23));//posiciona ele
  /**
   * configura a ação do botão OK
   */
  jButton1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    jButton1_actionPerformed(e);//ação do botão
   }
  });
  jTextArea1.setBackground(Color.black);//fundo preto
  jTextArea1.setForeground(Color.green);//letras verdes

  String sobre
          = "SOBRE O PROGRAMA"
          + "\n-------------------------------------------"
          + "\nEste programa foi desenvolvido para "
          + "\nrepresentar o uso do algoritmo de busca em largura."
          + "\nO algoritmo é parte dos estudos feitos dentro"
          + "\nda matéria Inteligência Artificial no curso de"
          + "\nCiência da Computação"
          + "\nsob orientação da professora Denise"
          + "\n-------------------------------------------"
          + "\nO GRUPO"
          + "\nRômulo , Lauro e André";

  jTextArea1.setText(sobre);//adiciona na área de texto o texto acima
 }

 /**
  * método para fechar a janela
  *
  * @param e
  */
 private void jButton1_actionPerformed(ActionEvent e) {
  this.dispose();//fecha a janela, 'mata' o objeto
 }
}
