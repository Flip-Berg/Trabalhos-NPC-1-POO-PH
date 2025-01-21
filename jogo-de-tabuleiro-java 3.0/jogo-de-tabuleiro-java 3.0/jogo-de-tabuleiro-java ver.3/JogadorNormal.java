import java.util.Random;
import java.util.Scanner;

public class JogadorNormal extends Jogador {
    public JogadorNormal(String cor) {
        super(cor);
        casa=0;
        numJogadas=0;
    }

    public JogadorNormal(String cor,Tabuleiro tabuleiro) {
        super(cor);
        casa=0;
        numJogadas=0;
        this.tabuleiro=tabuleiro;
    }

    @Override
    public void andar(){
        Random random = new Random();
        int dado1, dado2, resultado;
        dado1 = random.nextInt(1,7);
        dado2 = random.nextInt(1,7);
        resultado = dado1 + dado2;
        casa += resultado;
        System.out.printf("%d + %d = %d\n", dado1, dado2, resultado, casa);
        numJogadas++;

        if (dado1 == dado2){
            Scanner scanner= new Scanner(System.in);
            tabuleiro.verificarCasa(this);
            System.out.println("Dados iguais, aperte ENTER para rolar novamente");
            scanner.nextLine();
            andar();
        }
    }
}