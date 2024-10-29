package br.org.rfdouro.prjbuscalargura.logica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Stroke;

import java.util.LinkedList;

/**
 * classe que gera um grafo
 */
public class Grafo {

 /**
  * Lista inicial com os nós guarda os nós criados inicialmente os índices desta lista são os
  * indices dos nós
  *
  */
 private LinkedList listaNos = new LinkedList();
 /**
  * Matriz que busca as ligações dos nós 1 = ligação do nó indice linha com nó indice coluna 0 = não
  * tem ligação
  */
 private int matrizLigacoes[][];
 /**
  * atributos usados para estabelecer ligações indDe = índice de onde indPara = índice para onde
  */
 private int indDe = -1, indPara = -1;
 /**
  * guarda a fila de busca gerada pelo algoritmo exaustivo
  */
 private LinkedList filaBusca = new LinkedList();
 /**
  * variáveis auxilires para a representação gráfica dos nós
  */
 private int estiloNos = 0;
 private int dim = 10;

 /**
  * método construtor vazio
  */
 public Grafo() {
 }

 /**
  * cria uma matriz de inteiros, quadrada e vazia com largura igual à quantidade de nós do grafo
  * atribui para todas as posições dela o número zero (inicialmente stá sem ligação)
  */
 public void clearMatrizNos() {
  int t = this.listaNos.size();
  this.matrizLigacoes = new int[t][t];
  for (int i = 0; i < t; i++) {
   for (int j = 0; j < t; j++) {
    this.matrizLigacoes[i][j] = 0;
   }
  }
 }

 /**
  * adiciona um nó na lista de nós
  *
  * @param no
  */
 public void addNo(No no) {
  no.setOpEstilo(this.estiloNos);
  this.listaNos.add(no);
 }

 /**
  * retorna a lista de nós do grafo
  *
  * @return
  */
 public LinkedList getListaNos() {
  return this.listaNos;
 }

 /*-----------------------------------------------------------------------*/
 /* os métodos abaixo so devem ser executados apos a confirmação de que todos os nós estão ok */
 /*-----------------------------------------------------------------------*/
 /**
  * inclui na matriz dois valores 1 na linha com índice do nó n1 na coluna com índice do nó n2
  *
  * @param n1
  * @param n2
  */
 public void setLigacao(No n1, No n2) {
  this.matrizLigacoes[n1.getIndice()][n2.getIndice()] = 1;
 }

 /**
  * sobrescreve o método acima passando direto os valores dos índices
  *
  * @param indice1
  * @param indice2
  */
 public void setLigacao(int indice1, int indice2) {
  this.matrizLigacoes[indice1][indice2] = 1;
 }

 /**
  * varre a lista de nós marcando as ligações entre eles na matriz de ligações
  */
 public void marcaLigacoes() {
  No aux = null;
  for (int i = 0; i < this.listaNos.size(); i++) {
   aux = (No) this.listaNos.get(i);//pega o nó da lista de nós
   for (int j = 0; j < aux.getLigacoesIndices().size(); j++) {//pega na lista de ligções do nó em questão
    int k = (Integer) aux.getLigacoesIndices().get(j);
    this.setLigacao(aux.getIndice(), k);//marca a ligação na matriz
   }
  }
 }

 /*-----------------------------------------------------------------------*/

 /**
  * retorna a fila de busca formada na execução do algoritmo
  *
  * @return
  */
 public LinkedList getFilaBusca() {
  return this.filaBusca;
 }

 /**
  * verifica se já existe um nó na lista de nós com o nome que é passado como parâmetro
  *
  * @param nome
  * @return
  */
 public boolean confereExisteNo(String nome) {
  for (int i = 0; i < this.listaNos.size(); i++) {
   if (nome.equals(((No) this.listaNos.get(i)).getNome())) {
    return true;
   }
  }
  return false;
 }

 /**
  * retorna uma lista contendo os filhos de um determinado nó
  *
  * @param raiz
  * @return
  * @throws Exception
  */
 private LinkedList geraFilaFilhos(No raiz) throws Exception {
  int t = this.matrizLigacoes[0].length;//pega a largura da matriz, lembrando que a largura é igual a altura que é igual ao tamanho da fila de nós do grafo
  LinkedList filaFilhos = new LinkedList();//cria uma fila vazia para colocar os filhos
  No aux = null;
  int indiceRaiz = raiz.getIndice();//pega o índice da raiz
  String nomeRaiz = raiz.getNome();//pega o nome da raiz
  String nomeFilho;

  try {
   for (int i = 0; i < t; i++) {
    nomeFilho = ((No) this.listaNos.get(i)).getNome();
    /*
                 * varre a lista de nós do grafo verificando se:
                 * - possui ligação => this.matrizLigacoes[indiceRaiz][i]==1
                 * - o nome não é igual ao nome da raiz => !nomeFilho.equals(nomeRaiz)
                 * - o filho já não aparece como pai => !raiz.conferePai(nomeFilho)
                 * */
    if (this.matrizLigacoes[indiceRaiz][i] == 1 && !nomeFilho.equals(nomeRaiz) && !raiz.conferePai(nomeFilho)) {
     /*
                     * cria um novo nó que será acrescentado na fila de filhos
                     * esse nó preserva do filho o índice, o nome e o ponteiro para o pai
      */
     aux = new No(i, nomeFilho, raiz);
     filaFilhos.add(aux);//adiciona na fila de filhos
    }
   }
  } catch (Exception e) {
   //trata o erro de estouro de memória caso que acontece quando a fila fica grande demais
   throw new OutOfMemoryError("Erro de estouro de memória");
  }

  return filaFilhos;//retorna a fila de filhos
 }

 /**
  * gera a fila de busca = árvore através do parâmetro de entrada que é o índice do nó raiz
  *
  * @param indiceRaiz
  * @return
  * @throws Exception
  */
 private LinkedList geraFilaBusca(int indiceRaiz) throws Exception {
  int i = 0;
  LinkedList fila = new LinkedList();
  No noAux = null;
  LinkedList listaRaiz = new LinkedList();//cria uma lista para o nó raiz
  LinkedList listaAux, listaFilhos;

  try {
   listaRaiz.add((No) this.listaNos.get(indiceRaiz));//adiciona o nó raiz na lista da raiz
   fila.add(listaRaiz);//adiciona a lista da raiz na fila de busca

   /*
             * enquanto não chegar ao fim da fila de busca faz o loop
    */
   while (i < fila.size()) {
    listaAux = (LinkedList) fila.get(i);//pega a lista dentro da fila de busca
    for (int j = 0; j < listaAux.size(); j++) {//varre a lista
     noAux = (No) listaAux.get(j);//pega o nó
     listaFilhos = this.geraFilaFilhos(noAux);//traz a lista com os filhos do nó em questão
     /*Adiciona a lista dos filhos do nó, na fila
                    * pode adicionar uma fila vazia mostrando que o nó não possui filhos
                    * */
     fila.add(listaFilhos);
    }
    i++;
   }

   this.filaBusca = fila;//seta a fila de busca do grafo como a fila a ser retornada
  } catch (Exception e) {
   //trata erro de estouro memória
   throw new OutOfMemoryError("Erro de estouro de memória");
  }

  return fila;//retorna a fila de busca
 }

 /**
  * é uma busca exaustiva gera o caminho do nó raiz para o nó procurado retorna uma lista encadeada
  * com os nós pertencentes ao caminho
  *
  * @param indiceInicio
  * @param indiceBusca
  * @return
  * @throws Exception
  */
 public LinkedList caminhoAchou(int indiceInicio, int indiceBusca) throws Exception {
  clearMatrizNos();//redefine a matriz de ligacoes zerando as posicoes
  marcaLigacoes();//marca as ligacoes dos nós na matriz   
  LinkedList lb = this.geraFilaBusca(indiceInicio);//gera a fila de busca do índice de início
  LinkedList aux;
  No noAchou = null;
  boolean achou = false;

  /*
         * varre a lista de busca
   */
  for (int i = 0; !achou && i < lb.size(); i++) {
   aux = (LinkedList) lb.get(i);//seleciona a lista de nós dentro da fila de busca
   /*
             * varre a lista de nós
    */
   for (int j = 0; !achou && j < aux.size(); j++) {
    noAchou = (No) aux.get(j);
    if (noAchou.getIndice() == indiceBusca) {//verifica se o indice do nó é o procurado
     achou = true;//se for achou = true
    }
   }
  }

  aux = new LinkedList();//cria uma nova lista vazia
  if (achou) {
   while (noAchou != null) {//pega o caminho inverso pegando os pais dos nós
    aux.addFirst(noAchou);
    noAchou = noAchou.getPai();
   }
  }

  return aux;
 }

 /**
  * sobrescreve o método acima passando como parâmetrosos nomes dos nós
  *
  * @param nomeInicio
  * @param nomeBusca
  * @return
  * @throws Exception
  */
 public LinkedList caminhoAchou(String nomeInicio, String nomeBusca) throws Exception {
  int indiceInicio = this.getIndiceNoLista(nomeInicio);//pega o índice de inicio
  int indiceBusca = this.getIndiceNoLista(nomeBusca);//pega o índice de busca
  return this.caminhoAchou(indiceInicio, indiceBusca);//executa o método acima

 }

 /**
  * executa o algoritmo de busca objetiva é uma variante do algoritmo acima mas só é executado até o
  * momento em que encontra o nó procurado utiliza uma lista encadeada de listas encadeadas retorna
  * uma lista encadeada com os nós pertencentes ao caminho
  *
  * @param indiceRaiz
  * @param indiceBusca
  * @return
  * @throws Exception
  */
 private LinkedList caminhoAchouOjbetivo(int indiceRaiz, int indiceBusca) throws Exception {
  clearMatrizNos();//redefine a matriz de ligacoes zerando as posicoes
  marcaLigacoes();//marca as ligacoes dos nós na matriz 
  int i = 0;
  LinkedList fila = new LinkedList();
  No noAux = null, noAchou = null;
  LinkedList listaRaiz = new LinkedList();
  LinkedList listaAux, listaFilhos, aux;
  boolean achou = false;

  noAchou = (No) this.listaNos.get(indiceRaiz);//pega o nó raiz
  listaRaiz.add(noAchou);//adiciona na lista da raiz
  fila.add(listaRaiz);//adiciona a lista raiz na lista de procura

  if (indiceRaiz == indiceBusca) {//se o índice de procura é igual ao índice da raiz, achou
   achou = true;
  }

  /*
         * enquanto não achou e i < que o tamanho da fila
   */
  while (!achou && i < fila.size()) {
   listaAux = (LinkedList) fila.get(i);//pega a fila dentro da fila de procura
   for (int j = 0; !achou && j < listaAux.size(); j++) {//dentro da fila filha
    noAux = (No) listaAux.get(j);//pega os nós
    if (noAux.getIndice() == indiceBusca) {//se o indice for igual ao da procura então achou
     achou = true;
     noAchou = noAux;
    }
    listaFilhos = this.geraFilaFilhos(noAux);//pega a fila de nós filhos do nó atual
    fila.add(listaFilhos);//adiciona essa fila na fila de procura
    /*
                 * Confere logo se algum filho é o procurado
                 * */
    if (!achou) {
     for (int k = 0; k < listaFilhos.size(); k++) {
      noAux = (No) listaFilhos.get(k);
      if (noAux.getIndice() == indiceBusca) {
       achou = true;
       noAchou = noAux;
      }
     }
    }
   }
   i++;
  }

  if (!achou) {
   fila = new LinkedList();
  }

  this.filaBusca = fila;

  aux = new LinkedList();
  if (achou) {
   while (noAchou != null) {
    aux.addFirst(noAchou);
    noAchou = noAchou.getPai();
   }
  }

  return aux;
 }

 /**
  * método que retorna uma lista com o caminho objetivo atráves do nome da raiz ao nome do nó
  * procurado utiliza uma lista encadeada de nós retorna uma lista encadeada com os nós pertencentes
  * ao caminho
  *
  * @param nomeRaiz
  * @param nomeBusca
  * @return
  * @throws Exception
  */
 public LinkedList caminhoAchouObjetivo(String nomeRaiz, String nomeBusca) throws Exception {
  return this.caminhoAchouOjbetivo(this.getIndiceNoLista(nomeRaiz), this.getIndiceNoLista(nomeBusca));
 }

 /**
  * executa o algoritmo de busca otimizada retorna uma lista encadeada com os nós pertencentes ao
  * caminho
  *
  * @param indiceRaiz
  * @param indiceBusca
  * @return
  * @throws Exception
  */
 private LinkedList caminhoAchouOjbetivoOtimizado(int indiceRaiz, int indiceBusca) throws Exception {
  clearMatrizNos();//redefine a matriz de ligacoes zerando as posicoes
  marcaLigacoes();//marca as ligacoes dos nós na matriz
  int i = 0;
  LinkedList fila = new LinkedList();
  No noAux = null, noAchou = null;
  LinkedList listaFilhos = new LinkedList(), aux;
  boolean achou = false;

  noAchou = (No) this.listaNos.get(indiceRaiz);//pega o primeiro nó = raiz
  fila.add(noAchou);//adiciona o primeiro no na fila

  /*
         * enquanto não achou e i < que o tamanho da fila
   */
  while (!achou && i < fila.size()) {
   noAux = (No) fila.get(i);//pega o próximo nó da fila
   if (noAux.getIndice() == indiceBusca) {//se o índice do nó = ao procurado então achou
    achou = true;
    noAchou = noAux;
   } else {//se não é o procurado
    listaFilhos = this.geraFilaFilhos(noAux);//pega a lista de filhos do nó
    fila.removeFirst();//remove o nó da fila (e o primeiro da fila)
    i--;
   }
   for (int k = 0; !achou && k < listaFilhos.size(); k++) {
    noAux = (No) listaFilhos.get(k);
    if (noAux.getIndice() == indiceBusca) {//verifica se o nó procurado está na lista de filhos
     achou = true;
     noAchou = noAux;
    } else {//se não está, então acrescenta o nó na lista de procura para poder então pegar seus filhos nas próximas passadas
     fila.add(noAux);
    }
   }
   i++;
  }

  this.filaBusca = fila;

  aux = new LinkedList();//cria uma lista vazia para armazenar o caminho
  if (achou) {//se achou
   while (noAchou != null) {
    aux.addFirst(noAchou);//vai adicionando o nó no início da fila
    noAchou = noAchou.getPai();//e pegando o pai sucessivamente
   }
  }

  return aux;//retorna a lista com o caminho
 }

 /**
  * método que retorna uma lista com o caminho objetivo atráves do nome da raiz ao nome do nó
  * procurado retorna uma lista encadeada com os nós pertencentes ao caminho
  *
  * @param nomeRaiz
  * @param nomeBusca
  * @return
  * @throws Exception
  */
 public LinkedList caminhoAchouObjetivoOtimizado(String nomeRaiz, String nomeBusca) throws Exception {
  return this.caminhoAchouOjbetivoOtimizado(this.getIndiceNoLista(nomeRaiz), this.getIndiceNoLista(nomeBusca));
 }

 /**
  * método usado para pegar o índice de um nó da lista através do nome
  *
  * @param nome
  * @return
  */
 private int getIndiceNoLista(String nome) {
  No aux = null;

  for (int i = 0; i < this.listaNos.size(); i++) {
   aux = (No) this.listaNos.get(i);
   if (aux.getNome().equals(nome)) {
    return aux.getIndice();
   }
  }

  return -1;
 }

 /*métodos utilizados na representação gráfica*/
 /*-------------------------------------------*/
 /**
  * desenha tudo o que precisa ser desenhado
  *
  * @param caminho
  * @param g
  */
 public void desenhaTudo(LinkedList caminho, Graphics g) {
  //Exibe todas as ligações do grafo
  for (int i = 0; i < this.listaNos.size(); i++) {
   ((No) this.listaNos.get(i)).desenhaLigacoes(this.dim, g);
  }
  //desenha apenas o caminho
  if (caminho.size() > 0) {
   this.desenhaCaminho(caminho, g);
  }
  //desenha os nós do grafo
  for (int i = 0; i < this.listaNos.size(); i++) {
   ((No) this.listaNos.get(i)).desenha(g);
  }
 }

 /**
  * confere o clique do botão direito na tela se tem algum nó já na área ou se vai criar um novo
  *
  * @param x
  * @param y
  */
 public void confereCliqueTudo(int x, int y) {
  //varre toda a lista de nós
  for (int i = 0; i < this.listaNos.size(); i++) {
   //verifica se clicou sobre algum nó
   if (((No) this.listaNos.get(i)).confereClique(x, y)) {
    //se o indice de saída é -1 então o nó clicado é o nó de saída e deve selecioná-lo
    if (indDe < 0) {
     indDe = i;//armazena o índice de saída
     ((No) this.listaNos.get(indDe)).seleciona(true);//seleciona o nó graficamente
    } else {
     //verifica se não está clicando sobre o mesmo nó já selecionado
     if (indDe != i) {
      indPara = i;
      //guarda a posição normal do canto como coordenada do nó
      Coords coo = new Coords(((No) this.listaNos.get(indPara)).getPosX(), ((No) this.listaNos.get(indPara)).getPosY());
      ((No) this.listaNos.get(indDe)).setLigacaoGrafica(coo);//armazena uma ligação gráfica para o nó de saída -> índice = indDe
      ((No) this.listaNos.get(indDe)).setLigacaoIndice(indPara);//armazena uma ligação de índice usado na matriz para o nó de saída com o nó de chegada
      ((No) this.listaNos.get(indPara)).setLigacaoIndice(indDe);//armazena uma ligação de índice usado na matriz para o nó de chegada com o nó de saída
      ((No) this.listaNos.get(indDe)).seleciona(false);//deseleciona o nó
      indDe = -1;//desmarca o indice de saída
      indPara = -1;//desmarca o indice de chegada
     } else {//se clicar sobre o mesmo nó selecionado então o deseleciona
      ((No) this.listaNos.get(indDe)).seleciona(false);
      indDe = -1;
     }
    }
    break;
   }
  }
 }

 /**
  * desenha na tela o caminho a ser percorrido é passado como parâmetro a lista com os nós do
  * caminho
  *
  * @param nosDoCaminho
  * @param g
  */
 public void desenhaCaminho(LinkedList nosDoCaminho, Graphics g) {
  int t = nosDoCaminho.size();
  No aux1 = null, aux2 = null;//nós auxiliares
  Graphics2D g2 = (Graphics2D) g;
  Color c = g2.getColor();//pega a cor atual da tela

  g2.setPaint(Color.GREEN);//a cor de desenho para o caminho é verde
  //stroke é o tipo de decoração da linha
  Stroke str = g2.getStroke();//pega o stroke da tela
  //seta um novo stroke para a tela
  g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
  for (int i = 1; i < t; i++) {
   aux1 = this.getNoGrafo(((No) nosDoCaminho.get(i - 1)).getNome());//pega o nó anterior da fila de nós do caminho na fila de nós do grafo, passando o nome do nó que é preservado
   aux2 = this.getNoGrafo(((No) nosDoCaminho.get(i)).getNome());//pega o nó posterior da fila de nós do caminhona fila de nós do grafo, passando o nome do nó que é preservado
   //desenha a linha do nó anterior ao nó posterior
   g.drawLine((int) aux1.centro(this.dim).x, (int) aux1.centro(this.dim).y, (int) aux2.centro(this.dim).x, (int) aux2.centro(this.dim).y);
  }
  g2.setStroke(str);//retorna o stroke padrão da tela
  g2.setPaint(c);//retorna a cor padrão
 }

 /**
  * Retorna um nó da lista de nós do grafo que possui a chave dada = nome
  *
  * @param nomeNo
  * @return
  */
 private No getNoGrafo(String nomeNo) {
  No ret = null;
  int t = this.listaNos.size();
  for (int i = 0; i < t; i++) {
   ret = (No) this.listaNos.get(i);
   if (nomeNo.equals(ret.getNome())) {//verifica se o nome do nó da lista é igual ao passado como parâmetro
    return ret;//se for retona o nó
   }
  }
  return null;
 }

 /**
  * verifica se a posição clicada é válida ou seja: se não existe nenhum nó na área do clique
  *
  * @param x
  * @param y
  * @return
  */
 public boolean posicaoValida(double x, double y) {
  No aux = null;
  int t = this.listaNos.size();
  boolean ret = false;
  for (int i = 0; !ret && i < t; i++) {
   aux = (No) this.listaNos.get(i);//pega o próximo nó
   ret = aux.contemPonto(x, y, this.dim);//vê se na posição já contém um nó
  }
  return !ret;
 }

 /**
  * modifica o estilo dos nós
  *
  * @param op
  */
 public void setEstiloNos(int op) {
  this.estiloNos = op;//seta o estilo de nós atual
  switch (op) {
   case 0:
    this.dim = 10;//se for pequeno diminui o lado do quadrado
    break;
   case 1:
    this.dim = 20;//se for pequeno aumenta o lado do quadrado
  }
  int t = this.listaNos.size();
  /*varre a lista de nós modificando o estilo em cada um*/
  for (int i = 0; i < t; i++) {
   ((No) this.listaNos.get(i)).setOpEstilo(op);
  }
 }
 /*-------------------------------------------*/

}
