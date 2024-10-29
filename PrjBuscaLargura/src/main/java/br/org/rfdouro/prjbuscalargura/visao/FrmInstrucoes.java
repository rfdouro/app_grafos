package br.org.rfdouro.prjbuscalargura.visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * apenas cria uma janela contendo instruções básicas para o uso do programa
 */
public class FrmInstrucoes extends JDialog {

 private JScrollPane jScrollPane1 = new JScrollPane();//painel de barras de rolagem
 private JButton jButton1 = new JButton();//cria botão para OK
 private JTextArea jTextArea1 = new JTextArea();//área de texto com a informação

 /**
  * método construtor
  */
 public FrmInstrucoes() {
  this(null, "", false);
 }

 /**
  * método construtor sobrecarregado com opções a mais
  *
  * @param parent
  * @param title
  * @param modal
  */
 public FrmInstrucoes(Frame parent, String title, boolean modal) {
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
  this.setTitle("Instruções de uso");//coloca o título da janela
  /**
   * dimensiona e posiciona o painel da área de texto
   */
  jScrollPane1.setBounds(new Rectangle(5, 5, this.getWidth() - 15, this.getHeight() - 80));
  jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
  jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  /**
   * configura o botão OK
   */
  jButton1.setText("OK");
  jButton1.setBounds(new Rectangle(160, 240, 71, 23));
  jButton1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    jButton1_actionPerformed(e);//ação do botão
   }
  });
  jTextArea1.setEditable(false);//nega a edição da área de texto
  jTextArea1.setBackground(Color.black);//fundo preto
  jTextArea1.setForeground(Color.GREEN);//letras verdes
  jScrollPane1.getViewport().add(jTextArea1, null);//coloca a área de texto no painel
  this.getContentPane().add(jButton1, null);//adiciona o botão na janela
  this.getContentPane().add(jScrollPane1, null);//adiciona o painel na janela

  String instrucoes
          = "MANUAL DE UTILIZAÇÃO"
          + "\n-------------------------------------------"
          + "\nClique com o botão direito no ponto da tela "
          + "\nonde você deseja inserir um novo NÓ para o grafo"
          + "\n-------------------------------------------"
          + "\nLigue os NÓS desejados clicando em um deles"
          + "\ne em seguina no outro"
          + "\n-------------------------------------------"
          + "\nClique no botão EXECUTAR";

  jTextArea1.setText(instrucoes);//adiciona na área de texto o texto acima
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
