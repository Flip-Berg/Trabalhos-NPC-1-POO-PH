import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        ArrayList<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new JogadorSortudo("vermelho"));
        jogadores.add(new JogadorNormal("azul"));
        jogadores.add(new JogadorAzarado("verde"));
        Tabuleiro tabuleiro = new Tabuleiro(false, jogadores);
        for (Jogador jogador : jogadores){
            jogador.setTabuleiro(tabuleiro);
        } //inicializa o tabuleiro pra cada jogador(necessario quando os dados vão ser rerolados e a casa é verificada antes disso)
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Adicionar Jogador");
            System.out.println("2 - Remover Jogador");
            System.out.println("3- Iniciar jogo");
            System.out.println("4- Sair do jogo");
            System.out.print("Escolha uma opção: ");

            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine()); // Usa nextLine e converte para evitar InputMismatchException
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número entre 1 e 3.");
                continue; // Volta ao início do loop
            }

            switch (opcao) {
                case 1 -> {
                    if (tabuleiro.getJogadores().size() < 6) {
                        tabuleiro.adicionarJogador();
                    } else {
                        System.out.println("O número máximo de 6 jogadores já foi atingido.");
                    }
                    break;
                }
                case 2 -> {
                    tabuleiro.removerJogador();
                }
                case 3 -> {
                    //checa se os jogadores tem ao menos duas cores diferentes
                    boolean isDiferente=true;
                    for (int i = 0; i < (tabuleiro.getJogadores().size())-1; i++) {
                        if (tabuleiro.getJogadores().get(i).getCor().equals(tabuleiro.getJogadores().get(i+1).getCor())) {
                            isDiferente = false;
                            System.out.println("Adicione jogadores com cores diferentes.");
                            break;
                        }
                    }
                    if (tabuleiro.getJogadores().size() >= 2 && isDiferente) {
                        tabuleiro.iniciarJogo();
                        return;
                    } else {
                        System.out.println("Adicione pelo menos 2 jogadores antes de iniciar o jogo.");
                    }
                    break;
                }
                case 4 -> {
                    System.out.println("Saindo do jogo...");
                    scanner.close();
                    return; // Encerra o programa
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}