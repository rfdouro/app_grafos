package br.org.rfdouro.prjbuscalargura.visao;

import br.org.rfdouro.prjbuscalargura.logica.Grafo;
import br.org.rfdouro.prjbuscalargura.logica.No;

import java.applet.AppletContext;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import java.util.LinkedList;

import javax.imageio.ImageIO;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.RepaintManager;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * classe Tela herdeira de JPanel é a tela de execução do programa, onde o grafo é desenhado. contém
 * os eventos de clique de mouse e de execução do grafo
 */
public class Tela extends JPanel {

 private Object[] options = {"Sim", "Não"};//cadeia de string para ser usada nos botões de opção da JOptionPane (apenas tradução)
 private Object[] tiposProcura = {"Otimizada", "Objetiva", "Completa"};//cadeia de string usada para a escolha da opção de execução do programa
 private Grafo grafo = new Grafo();//cria um grafo que será usado na execução do programa
 private LinkedList filaBusca = new LinkedList();//cria uma lista encadeada vazia para armazenar a fila de busca gerada pelo grafo
 private LinkedList caminho = new LinkedList();//cria uma lista encadeada vazia para armazenar o caminho gerado pelo grafo
 private boolean desenhaFundo = false;//flag usada para verificar se desenha o fundo (mapas) ou não
 private int opcaoFundo = -1;//se desenhar o fundo essa variável controla qual fundo será desenhado
 private String opcoesFundo[] = {"es.gif", "brasil.gif", "mundo.gif"};//nomes dos arquivos de fundo de tela

 private Component componentToBePrinted;

 public Tela() {
  /**
   * cria as traduções para os botões padrão usados na JOptionPane apenas para os botõs usados neste
   * programa
   */
  UIManager.put("OptionPane.yesButtonText", "Sim");
  UIManager.put("OptionPane.cancelButtonText", "Cancelar");
  UIManager.put("OptionPane.noButtonText", "Não");
  UIManager.put("OptionPane.okButtonText", "OK");
  /**
   * define a cor do fundo da tela
   */
  this.setBackground(Color.WHITE);
  /**
   * dá início
   */
  try {
   jbInit();//chama a função jbinit
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 private void jbInit() throws Exception {
  this.setLayout(null);//seta o layout para nulo podendo colocar objetos em disosição X, Y
  this.addMouseListener(new MouseAdapter() {
   public void mouseClicked(MouseEvent e) {//captura o evento de clicar com o mouse
    this_mouseClicked(e);//trata o evento de clique do mouse
   }
  });
 }

 /**
  * retorna a lista encadeada que contém o caminho obtido na resolução do algoritmo
  *
  * @return
  */
 public LinkedList getCaminho() {
  return this.caminho;
 }

 /**
  * retorna a lista encadeada que contém o caminho de busca usado pelo grafo na resolução do
  * algoritmo
  *
  * @return
  */
 public LinkedList getFilaBusca() {
  return this.filaBusca;
 }

 /**
  * método usado para desenhar o fundo de tela escolhido pelo usuário se retornar true = desenhou,
  * false = não
  *
  * @param nomeImg
  * @param g2
  * @return
  */
 private boolean desenhaFundo(String nomeImg, Graphics2D g2) {
  File f = new File("");//cria um arquivo auxiliar que é usado para trazer o caminho do diretório da aplicação usado abaixo
  //com o caminho do diretório da aplicação obém-se o arquivo completo da imagem de fundo
  File imagem = new File(f.getAbsolutePath() + "\\fundosTela\\" + nomeImg);
  BufferedImage bimg;//imagem bufferizada
  int x, y;
  try {
   InputStream imageIn = new FileInputStream(imagem);//cria uma cadeia de stream para a imagem
   BufferedInputStream in = new BufferedInputStream(imageIn);//bufferiza a imagem
   bimg = ImageIO.read(in);//traz para a variável a imagem em buffer
   x = (this.getWidth() - bimg.getWidth()) / 2;//calcula a coorderna x da imagem, onde será colocada na tela
   y = (this.getHeight() - bimg.getHeight()) / 2;//calcula a coorderna y da imagem, onde será colocada na tela
   g2.drawImage(bimg, x, y, null);//desenha a imagem na tela
  } catch (IOException e) {
   //tratamento de exceções
   JOptionPane.showMessageDialog(null, "Erro ao desenhar fundo\n" + e.getMessage(), "Erro", 0);
   return false;
  }
  return true;
 }

 /**
  * método usado para desenhar o fundo de tela escolhido pelo usuário sobrescreve o método acima
  * usando a opção de entrada de número para escolher o fundo se retornar true = desenhou, false =
  * não
  *
  * @param op
  * @param g2
  * @return
  */
 private boolean desenhaFundo(int op, Graphics2D g2) {
  //verifica se é uma opção válida de acordo com o array opcoesFundo
  if (op >= 0 && op <= 2) {
   return this.desenhaFundo(this.opcoesFundo[op], g2);
  }
  return false;
 }

 /**
  * sobrescreve o método acima desenhando o fundo esse é o método público
  *
  * @param op
  */
 public void desenhaFundo(int op) {
  //verifica se é uma opção válida, -1 indica q a tela deve ser limpa
  if (op >= -1 && op <= 2) {
   this.opcaoFundo = op;
   this.desenhaFundo = true;
   this.repaint();//manda repintar a tela, chama o método paint (sobrescrito do padrão logo abaixo)
  } else {
   this.opcaoFundo = -1;
   this.desenhaFundo = false;
  }
 }

 /**
  * método sobrescrito do padrão método gráfico usado para desenhar, realmente, a tela e todos os
  * seus objetos visuais aqui o acréscimo é para desenha o fundo de tela e o grafo
  *
  * @param g
  */
 public void paint(Graphics g) {
  Graphics2D g2 = (Graphics2D) g;//captura o objeto de desenho de tela e o transforma em um objeto 2D (atualização da API java)
  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//renderiza o método de desenho eliminando o efeito escada de bitmap (estética)
  g2.clearRect(0, 0, this.getWidth(), this.getHeight());//limpa a área de desenho, no caso a tela toda
  if (this.desenhaFundo) {//verifica se é para desenhar o fundo 
   try {
    this.desenhaFundo(this.opcaoFundo, g2);//desenha o fundo
   } catch (Exception e) {
    //se não conseguiu desenhar o fundo trata a exceção
    JOptionPane.showMessageDialog(null, "Erro ao carregar fundo\n" + e.getMessage(), "Erro", 0);
   }
  }
  //desenha o grafo todo, passa o atributo caminho, caso já tenha sido executado o algoritmo, para também ser desenhado
  this.grafo.desenhaTudo(this.caminho, g);
 }

 /**
  * metódo chamado para executar o algoritmo do grafo
  */
 public void executar() {
  if (this.grafo.getListaNos().size() > 0) {//verifica se já inseriu algum nó no grafo, se não tem nós não faz nada
   //pega o nome do nó de saída através do método estático showInputDialog o qual retorna uma string
   String saida = JOptionPane.showInputDialog(null, "Entre com o nome da saída", "Saída", 1);
   if (saida != null && !saida.equals("")) {//verifica se foi digitado alguma coisa, se não digitou nada não faz nada
    if (this.grafo.confereExisteNo(saida)) {//verifica se existe o nó com o nome digitado
     //pega o nome do nó de chegada através do método estático showInputDialog o qual retorna uma string
     String chegada = JOptionPane.showInputDialog(null, "Entre com o nome da chegada", "Chegada", 1);
     if (chegada != null && !chegada.equals("")) {//verifica se foi digitado alguma coisa, se não digitou nada não faz nada
      if (this.grafo.confereExisteNo(chegada)) {//verifica se existe o nó com o nome digitado
       try {
        String mess = "Caminho saindo de " + saida + " e chegando em " + chegada;
        /*Opção para procurar otimizadamente, objetivamente ou exaustivamente(Mostra toda a árvore)*/
        String tipoProc = (String) JOptionPane.showInputDialog(null, "Efetua que tipo de procura?", "Tipo de Procura", 3, null, tiposProcura, tiposProcura[0]);
        if (tipoProc != null) {
         if (tipoProc.equals("Otimizada")) {
          caminho = this.grafo.caminhoAchouObjetivoOtimizado(saida, chegada);//efetua a busca otimizada
         } else if (tipoProc.equals("Objetiva")) {
          caminho = this.grafo.caminhoAchouObjetivo(saida, chegada);//efetua a busca objetiva
         } else {
          caminho = this.grafo.caminhoAchou(saida, chegada);//efetua a busca exaustiva, completa com a árvore
         }
        }

        //recupera a fila de busca gerada pelo grafo
        filaBusca = this.grafo.getFilaBusca();
        this.repaint();//redesenha a tela

        /*confere se obteve sucesso no caminho procurado
                                 vê se tem 'alguma coisa' na lista que contém o caminho*/
        if (caminho.size() > 0) {
         JOptionPane.showMessageDialog(null, caminho, mess, 1);//se tem caminho mostra numa mensagem
        } else {
         JOptionPane.showMessageDialog(null, "Não é possivel encontrar um caminho", mess, 0);//se não tem mostra para o usuário
        }
       } catch (OutOfMemoryError oome) {
        //trata o erro de estouro de memória, geralmente causado na busca completa
        JOptionPane.showMessageDialog(null, "Erro de estouro de memória\n" + oome.toString() + "\n\nUtilize a Busca Objetiva", "ERRO", 0);
       } catch (Exception e) {
        //trata um erro genérico
        JOptionPane.showMessageDialog(null, "Exceção gerada\n" + e.getMessage(), "ERRO", 0);
       }
      } else {
       //trata o erro se o nome de chegada digitado não está associado a nenhum nó do grafo
       JOptionPane.showMessageDialog(null, "O nome digitado não está associado a nenhum nó do grafo.", "Erro", 0);
      }
     }
    } else {
     //trata o erro se o nome de saída digitado não está associado a nenhum nó do grafo
     JOptionPane.showMessageDialog(null, "O nome digitado não está associado a nenhum nó do grafo.", "Erro", 0);
    }
   }
  }
 }

 /**
  * método usado para tratar os eventos de clique de mouse na tela
  *
  * @param e
  */
 private void this_mouseClicked(MouseEvent e) {
  No n = new No();//cria um novo nó vazio
  int indice;//variável auxiliar para pegar o índice do nó
  String nomeNo;//variável auxiliar para pegar o nome do nó

  if (e.getButton() == 3) {//verifica se o botão clicado do mouse é o direito o que indica a pretensão de incluir um novo nó
   if (this.grafo.posicaoValida(e.getX(), e.getY())) {//verifica se já existe algum nó na posição clicada
    if (JOptionPane.showOptionDialog(null, "Deseja incluir um nó aqui?", "Incluir nó", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == 0) {
     n.setPosX(e.getX());//seta a posição x do nó como o x do mouse no momento do clique
     n.setPosY(e.getY());//seta a posição y do nó como o y do mouse no momento do clique
     //pega o nome do nó
     nomeNo = JOptionPane.showInputDialog(this, "Entre com o nome do nó", "Nome do nó", 1);
     if (nomeNo != null && !nomeNo.equals("")) {
      //se digitou um nome verifica se já não existe algum nó com esse nome
      if (!this.grafo.confereExisteNo(nomeNo)) {
       n.setNome(nomeNo);//seta o nome do nó
       //o indice do nó será dado pela quantidade atual de nós que o grafo possui
       indice = this.grafo.getListaNos().size();
       n.setIndice(indice);//seta o indice do nó
       this.grafo.addNo(n);//adiciona o nó no grafo
      } else {
       JOptionPane.showMessageDialog(null, "Já existe um nó no grafo com este nome!", "Erro", 0);
      }
     }
    }
   } else {
    JOptionPane.showMessageDialog(null, "Já existe um nó no grafo na área selecionada!", "Erro", 0);
   }
  } else {
   /*
             * se for um clique com o botão esquerdo então vai conferir com o método abaixo e
             * se tudo estiver ok, criar um novo nó
    */
   this.grafo.confereCliqueTudo(e.getX(), e.getY());
  }

  //redesenha a tela
  this.repaint();
 }

 /**
  * reseta as varáveis deixando tudo nulo e limpa a tela
  */
 public void limparTudo() {
  this.grafo = new Grafo();
  this.caminho = new LinkedList();
  this.filaBusca = new LinkedList();
  this.repaint();
 }

 /**
  * seta um novo estilo de nó e acessa o grafo para mofificá-lo redesenha a tela em seguida com o
  * novo estilo de nó
  *
  * @param op
  */
 public void modificaEstiloNos(int op) {
  this.grafo.setEstiloNos(op);
  this.repaint();
 }

 /**
  * este método salva a imagem criada na tela
  */
 public void salvaImagem() {
  /**
   * traduz as mensagens da janela de salvar arquivo para português
   */
  if (!UIManager.get("FileChooser.lookInLabelText").equals("Consulte:")) {//confere, se já tiver traduzido não traduz novamente
   UIManager.put("FileChooser.lookInLabelText", "Consulte:");
   UIManager.put("FileChooser.lookInLabelMnemonic", "o");
   UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo:");
   UIManager.put("FileChooser.fileNameLabelMnemonic", "N");
   UIManager.put("FileChooser.filesOfTypeLabelText", "Arquivos do tipo:");
   UIManager.put("FileChooser.filesOfTypeLabelMnemonic", "t");
   UIManager.put("FileChooser.upFolderToolTipText", "Um Nível Acima");
   UIManager.put("FileChooser.upFolderAccessibleName", "Para Cima");
   UIManager.put("FileChooser.homeFolderToolTipText", "Inicio");
   UIManager.put("FileChooser.homeFolderAccessibleName", "Inicio");
   UIManager.put("FileChooser.newFolderToolTipText", "Criar uma Nova Pasta");
   UIManager.put("FileChooser.newFolderAccessibleName", "Nova Pasta");
   UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
   UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");
   UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
   UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Detalhes");
   UIManager.put("FileChooser.cancelButtonText", "Cancelar");
   UIManager.put("FileChooser.cancelButtonMnemonic", "C");
   UIManager.put("FileChooser.cancelButtonToolTipText", "Cancelar");
   UIManager.put("FileChooser.openButtonText", "Abrir");
   UIManager.put("FileChooser.openButtonMnemonic", "A");
   UIManager.put("FileChooser.saveButtonText", "Salvar");
   UIManager.put("FileChooser.saveButtonToolTipText", "Salvar Arquivo");
   UIManager.put("FileChooser.saveInLabelText", "Salvar em:");
  }

  Image capture = this.createImage(this.getWidth(), this.getHeight());//cria uma imagem vazia do tamanho da tela do grafo
  File f;//arquivo que será a imagem
  JFileChooser fc = new JFileChooser();//cria uma janela padrão para 
  fc.setDialogTitle("Salvar");//seta o título da janela de salvamento
  fc.setAcceptAllFileFilterUsed(false);//retira a opção de tipo de arquivo da janela de salvamento
  fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//lista arquivos e diretórios na janela de salvamento

  //coloca a janela de salvamento para visualizar e verifica se está ok
  if (fc.showSaveDialog(this) == fc.APPROVE_OPTION) {
   f = fc.getSelectedFile();//traz o arquivo selecionado, no caso de não existir, cria um arquivo novo
   BufferedImage image = null;//cria uma imagem vazia

   Graphics captureG = capture.getGraphics();//cria um elemento gráfico vazio
   this.paint(captureG);//captura os elementos gráficos da tela e pinta no novo criado

   try {
    if (f.exists()) {
     f.delete();//se o arquivo selecionado existir, deleta-o
    }
    FileOutputStream out = new FileOutputStream(f);//cria um stream para transferencia de bits
    image = (BufferedImage) capture;//transforma a imagem capturada em uma imagem bufferizada para transferir os bits
    if (image != null) {
     ImageIO.write(image, "jpg", f);//salva o arquivo de imagem
     out.flush();//termina o fluxo de envio de streams
     out.close();//fecha o canal de transferencia de bits
    }
   } catch (Exception e) {
    //caso dê erro exibe a mensagem
    JOptionPane.showMessageDialog(null, "Erro ao salvar imagem:\n" + e.getMessage(), "Erro", 0);
   }
  }
 }
}
