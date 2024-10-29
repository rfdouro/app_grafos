package br.org.rfdouro.prjbuscalargura;

import br.org.rfdouro.prjbuscalargura.visao.FrmInstrucoes;
import br.org.rfdouro.prjbuscalargura.visao.FrmSobre;
import br.org.rfdouro.prjbuscalargura.visao.Tela;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * importante: para funcionar redondo o diretório fundosTela deve estar no mesmo diretório da classe
 * se for o jar deve estar no mesmo diretório do jar
 */
public class AppletBuscaLargura extends JApplet {

 private JMenuBar menuBar = new JMenuBar();//barra de menu
 private JMenu menuFile = new JMenu();//menu de ações
 private JMenuItem menuFileExit = new JMenuItem();//menu para sair do programa
 private JMenuItem menuNovoGrafo = new JMenuItem();//menu para um novo grafo
 private JMenuItem menuSalvaImagemGrafo = new JMenuItem();//menu para salvar a imagem do grafo
 private JMenu menuExibir = new JMenu();//menu de exibições
 private JMenuItem menuFilaBusca = new JMenuItem();//menu para exibir a fila de busca
 private JMenuItem menuCaminho = new JMenuItem();//menu para exibir o caminho encontrado

 private JMenu menuConfiguracoes = new JMenu();//menu para as configurações
 private JMenu menuFundoOpcoes = new JMenu();//menu para as opções de fundo de tela
 private ButtonGroup grupoOpcoesFundo = new ButtonGroup();//grupo de opções de fundo de tela, controla a seleção de opções
 private JRadioButtonMenuItem menuFundoBranco = new JRadioButtonMenuItem("Em branco", true);//menu para escolher o fundo branco
 private JRadioButtonMenuItem menuFundoEs = new JRadioButtonMenuItem("Espírito Santo");//menu para escolher a imagem do ES
 private JRadioButtonMenuItem menuFundoBrasil = new JRadioButtonMenuItem("Brasil");//menu para escolher a imagem do brasil
 private JRadioButtonMenuItem menuFundoMundo = new JRadioButtonMenuItem("Mundo");//menu para escolher o mundo

 private JMenu menuEstiloNo = new JMenu();//menu para controlar o estilo dos nós do grafo
 private ButtonGroup grupoOpcoesEstiloNo = new ButtonGroup();//grupo de opções de estilos de nó, controla a seleção de opções
 private JRadioButtonMenuItem menuEstiloPeq = new JRadioButtonMenuItem("Pequeno", true);//menu para escolher o nó pequeno
 private JRadioButtonMenuItem menuEstiloGra = new JRadioButtonMenuItem("Grande (Com índice)");//menu para escolher o nó grande

 private JMenu menuAjuda = new JMenu();//menu para as opções de ajuda
 private JMenuItem menuInstrucoes = new JMenuItem();//menu para exibir as instruções
 private JMenuItem menuSobre = new JMenuItem();//menu para exibir as informações sobre o programa

 /**
  * Tela do grafo é uma classe criada para abrigar o grafo e, principalmente, seus métodos de
  * desenho
  */
 private Tela tela = new Tela();

 private JScrollPane jScrollPane1 = new JScrollPane();//é uma tela auxiliar para agrupar a estrutura da tela em si, é usado apenas por estética
 private JButton jButton3 = new JButton();//botão para executar o programa (algoritmo do grafo)
 private JScrollPane jScrollPane2 = new JScrollPane();//é uma tela auxiliar para agrupar a caixa de texto de exibições de informação, é usado apenas por estética
 private JTextArea jTextArea1 = new JTextArea();//é a caixa de texto de exibição de informações

 static Toolkit toolkit = Toolkit.getDefaultToolkit();//é um assistente para obter informações sobre a tela do computador
 static Dimension dimension = toolkit.getScreenSize();//captura as dimensões da tela do computador no momento em que o programa é iniciado

 /**
  * variáveis de controle para a execuçao da applet na página HTML
  */
 String largura;
 String altura;

 /**
  * método construtor da classe
  */
 public AppletBuscaLargura() {
 }

 /**
  * método de início
  *
  * @throws Exception
  */
 private void jbInit() throws Exception {
  /**
   * se a applet está sendo excecutada em uma página HTML, verifica se esses parâmetros estão
   * configurados na página. a falta desses parâmetros pode comprometer o correto funcionamento da
   * applet na página
   */
  try {
   largura = getParameter("largura");
   altura = getParameter("altura");
  } catch (NullPointerException npe) {
   largura = null;
   altura = null;
  }
  /**
   * se a applet está numa página reconfigura as dimensões da tela do programa
   */
  if (largura != null && altura != null) {
   this.setBounds(new Rectangle(Integer.parseInt(largura), Integer.parseInt(altura)));
  }
  //adiciona a barra de menu
  this.setJMenuBar(menuBar);
  //seta o layout como null permitindo q os obetos possam ficar melhor dispostos na tela
  this.getContentPane().setLayout(null);
  menuFile.setText("Ações");//atribui o nome ao item de menu
  menuFileExit.setText("Sair");//atribui o nome ao item de menu
  menuFileExit.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    fileExit_ActionPerformed(ae);//atribui a ação que o item de menu irá executar
   }
  });
  menuNovoGrafo.setText("Novo Grafo");//atribui o nome ao item de menu
  menuNovoGrafo.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    novoGrafo_ActionPerformed(ae);//atribui a ação que o item de menu irá executar
   }
  });
  menuSalvaImagemGrafo.setText("Salvar Imagem do Grafo");//atribui o nome ao item de menu
  menuSalvaImagemGrafo.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    salvaImagemGrafo_ActionPerformed(ae);//atribui a ação que o item de menu irá executar
   }
  });
  menuFile.add(menuNovoGrafo);//adiciona o item de menu ao menu
  menuFile.insertSeparator(1);//adiciona um separador = estética
  menuFile.add(menuSalvaImagemGrafo);//adiciona o item de menu ao menu
  menuFile.insertSeparator(3);//adiciona um separador = estética
  menuFile.add(menuFileExit);//adiciona o item de menu ao menu
  menuBar.add(menuFile);//adiciona o menu à barra

  menuExibir.setText("Exibir");//atribui o nome ao item de menu
  menuFilaBusca.setText("Fila de Busca");//atribui o nome ao item de menu
  menuFilaBusca.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    exibeFilaBusca(ae);//atribui a ação que o item de menu irá executar
   }
  });
  menuCaminho.setText("Caminho Gerado");//atribui o nome ao item de menu
  menuCaminho.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    exibeCaminho(ae);//atribui a ação que o item de menu irá executar
   }
  });
  menuExibir.add(menuFilaBusca);//adiciona o item de menu ao menu
  menuExibir.add(menuCaminho);//adiciona o item de menu ao menu
  menuBar.add(menuExibir);//adiciona o menu à barra

  ActionListener acaoOpcaoFundo = new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    selecionaFundo(e);//atribui a ação que o item de menu irá executar
   }
  };
  menuFundoBranco.addActionListener(acaoOpcaoFundo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  menuFundoEs.setText("Espírito Santo");//atribui o nome ao item de menu
  menuFundoEs.addActionListener(acaoOpcaoFundo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  menuFundoBrasil.setText("Brasil");//atribui o nome ao item de menu
  menuFundoBrasil.addActionListener(acaoOpcaoFundo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  menuFundoMundo.setText("Mundo");//atribui o nome ao item de menu
  menuFundoMundo.addActionListener(acaoOpcaoFundo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  grupoOpcoesFundo.add(menuFundoBranco);//atribui o item de menu (radio) ao grupo a que pertence
  grupoOpcoesFundo.add(menuFundoEs);//atribui o item de menu (radio) ao grupo a que pertence
  grupoOpcoesFundo.add(menuFundoBrasil);//atribui o item de menu (radio) ao grupo a que pertence
  grupoOpcoesFundo.add(menuFundoMundo);//atribui o item de menu (radio) ao grupo a que pertence
  menuFundoOpcoes.setText("Opções de Fundo");//atribui o nome ao item de menu
  menuFundoOpcoes.add(menuFundoBranco);//adiciona o item de menu ao menu
  menuFundoOpcoes.add(menuFundoEs);//adiciona o item de menu ao menu
  menuFundoOpcoes.add(menuFundoBrasil);//adiciona o item de menu ao menu
  menuFundoOpcoes.add(menuFundoMundo);//adiciona o item de menu ao menu
  menuConfiguracoes.setText("Configurações");//atribui o nome ao item de menu
  menuConfiguracoes.add(menuFundoOpcoes);//adiciona o submenu ao menu

  ActionListener acaoOpcaoEstiloNo = new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    selecionaEstiloNo(e);//atribui a ação que o item de menu irá executar
   }
  };
  menuEstiloPeq.addActionListener(acaoOpcaoEstiloNo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  menuEstiloGra.addActionListener(acaoOpcaoEstiloNo);//aqui é onde é capturado o evento de clique no botão e executa a função associada
  grupoOpcoesEstiloNo.add(menuEstiloPeq);//atribui o item de menu (radio) ao grupo a que pertence
  grupoOpcoesEstiloNo.add(menuEstiloGra);//atribui o item de menu (radio) ao grupo a que pertence
  menuEstiloNo.setText("Estilo do Nó");//atribui o nome ao item de menu
  menuEstiloNo.add(menuEstiloPeq);//adiciona o item de menu ao menu
  menuEstiloNo.add(menuEstiloGra);//adiciona o item de menu ao menu
  menuConfiguracoes.add(menuEstiloNo);//adiciona o submenu ao menu
  menuBar.add(menuConfiguracoes);//adiciona o menu à barra

  menuAjuda.setText("Ajuda");//atribui o nome ao item de menu
  menuInstrucoes.setText("Instruções");//atribui o nome ao item de menu
  menuInstrucoes.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    FrmInstrucoes fins = new FrmInstrucoes();//cria um novo formulario de instruções
    fins.setLocationRelativeTo(null);//coloca o diálogo no centro da janela
    fins.setVisible(true);//exibe as instruções
   }
  });
  menuSobre.setText("Sobre");//atribui o nome ao item de menu
  menuSobre.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent ae) {
    FrmSobre fsob = new FrmSobre();//cria um novo formulário sobre o programa
    fsob.setLocationRelativeTo(null);//coloca o diálogo no centro da janela
    fsob.setVisible(true);//exibe o formulário
   }
  });
  menuAjuda.add(menuInstrucoes);//adiciona o item de menu ao menu
  menuAjuda.insertSeparator(1);//adiciona um separador ao menu
  menuAjuda.add(menuSobre);//adiciona o item de menu ao menu
  menuBar.add(menuAjuda);//adiciona o menu à barra
  /**
   * seta as dimensões da tela verificando se está em uma página
   */
  if (largura != null && altura != null) {
   this.jScrollPane1.setBounds(new Rectangle(5, 5, Integer.parseInt(largura) - 15, Integer.parseInt(altura) - 170));
  } else {
   this.jScrollPane1.setBounds(new Rectangle(5, 5, (int) AppletBuscaLargura.dimension.getWidth() - 15, (int) AppletBuscaLargura.dimension.getHeight() - 170));
  }
  jButton3.setText("Executar");//atribui o nome ao item de menu
  /**
   * posiciona e dimensiona o botão na janela
   */
  jButton3.setBounds(new Rectangle(5, this.jScrollPane1.getY() + this.jScrollPane1.getHeight() + 5, 90, 25));
  jButton3.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    jButton3_actionPerformed(e);//diz qual é a função de execução do programa
   }
  });
  //posiciona e dimensiona o painel que agrupa a caixa de texto
  jScrollPane2.setBounds(new Rectangle(130, this.jScrollPane1.getY() + this.jScrollPane1.getHeight() + 5, 530, 65));
  jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//sempre mostra barras verticais
  jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);//sempre mostra barras horizontais
  jTextArea1.setEditable(false);//impede que o conteúdo da caixa de texto seja apagado manualmente
  jTextArea1.setBackground(Color.black);//fundo da aixa é preto
  jTextArea1.setForeground(Color.green);//letras são verdes
  jScrollPane2.getViewport().add(jTextArea1, null);//adiciona a caixa no painel
  /**
   * adiciona a tela ao painel correspondente
   */
  jScrollPane1.getViewport().add(tela, null);
  /**
   * adiciona na janela o painel com a caixa de texto
   */
  this.getContentPane().add(jScrollPane2, null);
  /**
   * adiciona na janela o botão
   */
  this.getContentPane().add(jButton3, null);
  /**
   * adiciona na janela o painel com a tela
   */
  this.getContentPane().add(jScrollPane1, null);
 }

 /**
  * método chamado no começo do programa ele é que 'desenha' a tela e todo o resto na janela
  */
 public void init() {
  try {
   jbInit();
  } catch (Exception e) {
   System.out.println(e.getMessage());
   e.printStackTrace();
  }
 }

 /**
  * método do menu sair se estiver numa página então, ou vai para a página anterior ou fecha a
  * janela isso é controlado pelo javascript na janela normal apenas fecha a janela
  *
  * @param e
  */
 void fileExit_ActionPerformed(ActionEvent e) {
  if (largura != null && altura != null) {
   AppletContext context = getAppletContext();
   URL url;
   try {
    url = new URL("javascript:(history.length>0)?history.go(-1):window.close();");
    context.showDocument(url);
   } catch (MalformedURLException mfue) {
    mfue.printStackTrace();
   }
  } else {
   System.exit(0);
  }
 }

 /**
  * acao para controlar a opção de fundo de tela se estiver em uma página não desenha nada, é
  * desabilitado
  *
  * @param e
  */
 void selecionaFundo(ActionEvent e) {
  try {
   String op = e.getActionCommand();
   if (largura == null && altura == null) {
    if (op.equals("Em branco")) {
     this.tela.desenhaFundo(-1);
    } else if (op.equals("Espírito Santo")) {
     this.tela.desenhaFundo(0);
    } else if (op.equals("Brasil")) {
     this.tela.desenhaFundo(1);
    } else if (op.equals("Mundo")) {
     this.tela.desenhaFundo(2);
    }
   } else {
    JOptionPane.showMessageDialog(null, "Esta opção está desabilitada para a aplicação on-line", "Atenção", 2);
   }
  } catch (Exception ex) {
   ex.printStackTrace();
  }
 }

 /**
  * ação para selecionar o tipo de nó desejado
  *
  * @param e
  */
 void selecionaEstiloNo(ActionEvent e) {
  try {
   String op = e.getActionCommand();
   if (op.equals("Pequeno")) {
    this.tela.modificaEstiloNos(0);
   } else if (op.equals("Grande (Com índice)")) {
    this.tela.modificaEstiloNos(1);
   }
  } catch (Exception ex) {
   ex.printStackTrace();
  }
 }

 /**
  * ação para 'limpar' o grafo atual e criar um novo vazio
  *
  * @param e
  */
 void novoGrafo_ActionPerformed(ActionEvent e) {
  this.tela.limparTudo();
  this.jTextArea1.setText("");
 }

 /**
  * ação para salvar a imagem do grafo atual
  *
  * @param e
  */
 void salvaImagemGrafo_ActionPerformed(ActionEvent e) {
  this.tela.salvaImagem();
 }

 /**
  * ação do botão que executa o procedimento de busca esse procedimento é chamado em cadeia através
  * da classe Tela
  *
  * @param e
  */
 private void jButton3_actionPerformed(ActionEvent e) {
  this.tela.executar();
 }

 /**
  * mostra na caixa de texto a fila de busca
  *
  * @param e
  */
 private void exibeFilaBusca(ActionEvent e) {
  this.jTextArea1.setText("Fila de busca: (Geradora da árvore de busca)\n" + this.tela.getFilaBusca());
 }

 /**
  * mostra na caixa de texto o caminho encontrado pelo algoritmo
  *
  * @param e
  */
 private void exibeCaminho(ActionEvent e) {
  this.jTextArea1.setText("Caminho gerado:\n" + this.tela.getCaminho());
 }

 /**
  * método responsável por criar a aplicaçao em modo 'aplicativo'
  *
  * @param args
  */
 public static void main(String[] args) {
  AppletBuscaLargura applet = new AppletBuscaLargura();//cria um novo objeto applet
  JFrame frame = new JFrame();//cria um novo jframne = formulário = janela
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//seta a ação default para sair do programa quando clicar no 'x' do canto
  frame.getContentPane().add(applet, BorderLayout.CENTER);//coloca a applet criada no centro da tela
  frame.setResizable(true);//coloca a janela para poder ser extendida
  frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  frame.setTitle("Busca em Largura - IA - 2007 / 2");//seta o título da janela
  applet.init();//inicia a applet, método herdado da classe JApplet (herança de objetos = extends)
  applet.start();//starta a applet, método herdado da classe JApplet (herança de objetos = extends)
  frame.setSize(dimension);//dimensiona o frame
  /**
   * posiciona o frame na tela do computador
   */
  Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
  Dimension frameSize = frame.getSize();
  frame.setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
  frame.setVisible(true);//mostra o frame
 }
}
