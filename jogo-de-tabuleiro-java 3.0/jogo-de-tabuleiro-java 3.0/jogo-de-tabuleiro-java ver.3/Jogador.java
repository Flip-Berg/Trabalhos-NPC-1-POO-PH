public abstract class Jogador {
    protected String cor;
    protected int casa;
    protected int numJogadas;
    protected Tabuleiro tabuleiro;

    public Jogador(String cor){
        this.cor=cor;
        casa=0;
        numJogadas=0;
    }

    public abstract void andar();

    public String getCor(){
        return cor;
    }
    public int getCasa(){
        return casa;
    }
    public int getNumJogadas(){
        return numJogadas;
    }
    public Tabuleiro getTabuleiro(){
        return tabuleiro;
    }
    public void setCor(String cor){
        this.cor=cor;
    }
    public void setCasa(int casa){
        this.casa=casa;
    }
    public void setNumJogadas(int numJogadas){
        this.numJogadas=numJogadas;
    }
    public void setTabuleiro(Tabuleiro tabuleiro){
        this.tabuleiro=tabuleiro;
    }
}