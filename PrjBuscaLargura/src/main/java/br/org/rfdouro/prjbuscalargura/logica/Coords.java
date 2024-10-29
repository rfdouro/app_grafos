package br.org.rfdouro.prjbuscalargura.logica;

/**
 * classe auxiliar
 * serve para armazenar as coordenadas de um ponto
 */
public class Coords{
    //atributos públicos, não tem muito interesse em ser privado e torna o acesso mais rápido??
    double x, y;
    
    /**
     * método construtor, simples
     * @param x
     * @param y
     */
    public Coords(double x, double y){
        this.x=x;
        this.y=y;
    }
}