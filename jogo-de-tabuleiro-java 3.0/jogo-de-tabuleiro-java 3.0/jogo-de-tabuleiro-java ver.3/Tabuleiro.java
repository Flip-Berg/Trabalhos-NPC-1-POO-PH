import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Tabuleiro{
    private static final int TOTAL_CASAS = 40;
    private int jogadorAtual;
    private boolean modoDebug;
    private ArrayList<Jogador> jogadores;
    private Set<Integer> tiposJogadores; //set ->arraylist
    private Set<Jogador> jogadoresPulando; //set->arraylist<boolean>(pode ser dentro do jogador)
    private Scanner scanner=new Scanner(System.in);

    public Tabuleiro(boolean modoDebug) {
        this.jogadores = new ArrayList<>();
        this.jogadorAtual = 0;
        this.modoDebug = modoDebug;
        this.tiposJogadores = new HashSet<>();
        this.jogadoresPulando = new HashSet<>();
        this.scanner = new Scanner(System.in);
    }

    public Tabuleiro(boolean modoDebug, ArrayList<Jogador> jogadores){
        this.jogadores=jogadores;
        this.jogadorAtual = 0;
        this.modoDebug = modoDebug;
        this.tiposJogadores = new HashSet<>();
        this.jogadoresPulando = new HashSet<>();
        this.scanner = new Scanner(System.in);
    }

    public void adicionarJogador() {
        if (jogadores.size() >= 6) {
            System.out.println("Número máximo de jogadores atingido.");
            return;
        }

        System.out.print("Digite a cor do jogador: ");
        String cor = scanner.nextLine();

        System.out.println("Escolha o tipo de jogador:\n" +
                "0 - Normal\n" +
                "1 - Azarado\n" +
                "2 - Sortudo");
        int tipo = -1;
        while (tipo < 0 || tipo > 2) {
            System.out.print("Digite o número correspondente ao tipo: ");
            try {
                tipo = Integer.parseInt(scanner.nextLine()); // Lê como String e converte para int
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número entre 0 e 2.");
            }
        }

        Jogador novoJogador;
        switch (tipo) {
            case 1 -> novoJogador = new JogadorAzarado(cor);
            case 2 -> novoJogador = new JogadorSortudo(cor);
            default -> novoJogador = new JogadorNormal(cor);
        }

        jogadores.add(novoJogador);
        tiposJogadores.add(tipo); // Rastreia o tipo do jogador adicionado

        System.out.printf("Jogador %s (%s) adicionado ao jogo.\n", cor, novoJogador.getClass().getSimpleName());

        // Verifica se há pelo menos dois tipos diferentes
        if (tiposJogadores.size() < 2 && jogadores.size() >= 2) {
            System.out.println("Atenção: ainda não há dois tipos diferentes de jogadores.");
        }
    }

    public void iniciarJogo() {
        while (true) {
            Jogador jogador = jogadores.get(jogadorAtual);

            // Verifica se o jogador deve pular a vez
            if (jogadoresPulando.contains(jogador)) {
                System.out.printf("Jogador %s está pulando esta rodada.\n", jogador.getCor());
                jogadoresPulando.remove(jogador); // Remove para ele voltar a jogar na próxima rodada
            } else {
                System.out.printf("Vez do jogador %s na casa %d.\n", jogador.getCor(), jogador.getCasa());

                if (modoDebug) {
                    debugJogada(jogador);
                } else {
                    System.out.print("Pressione ENTER para rolar os dados...");
                    scanner.nextLine(); // Espera o jogador pressionar ENTER
                    jogador.andar();
                }

                verificarCasa(jogador);
            }

            // Verifica vitória
            if (jogador.getCasa() >= TOTAL_CASAS) {
                System.out.printf("O jogador %s venceu o jogo!\n", jogador.getCor());
                exibirResultados();
                scanner.close();
                break;
            }

            // Passa para o próximo jogador
            jogadorAtual = (jogadorAtual + 1) % jogadores.size();
            System.out.println("------------------\n");
        }
    }

    public void verificarCasa(Jogador jogador) {
        int casa = jogador.getCasa();

        switch (casa) {
            case 5, 15, 30 -> {
                if (!(jogador instanceof JogadorAzarado)) {
                    System.out.printf("Casa %d da sorte! Jogador %s avança 3 casas e vai para a casa %d.\n", casa, jogador.getCor(), (casa + 3));
                    jogador.setCasa(casa + 3);
                } else {
                    System.out.printf("Que azar! Jogador %s caiu na casa %d da sorte mas não pode aproveitar o bônus.\n",jogador.getCor(),casa);
                }
            }
            case 10, 25, 38 -> {
                System.out.printf("Jogador %s parou na casa %d e perderá a próxima rodada.\n", jogador.getCor(), casa);
                jogadoresPulando.add(jogador);
            }
            case 13 -> {
                System.out.printf("Casa %d surpresa!\n", casa);
                alterarTipoJogador(jogador);
            }
            case 17, 27 -> {
                System.out.printf("Casa %d especial!\n",casa);
                enviarOutroAoInicio(jogador);
            }
            case 20, 35 -> {
                System.out.printf("Casa %d mágica! Trocando de lugar com o último jogador.\n",casa);
                trocarComUltimo(jogador);
            }
            default -> System.out.printf("Jogador %s caiu na casa %d.\n", jogador.getCor(), jogador.getCasa());
        }
    }
    private void alterarTipoJogador(Jogador jogador) {
        System.out.println("Pressione ENTER para puxar uma carta...");
        scanner.nextLine(); // Aguarda o jogador pressionar ENTER

        Random random = new Random();
        int tipo = random.nextInt(3); // 0: normal, 1: azarado, 2: sortudo
        Jogador novoJogador;
        switch (tipo) {
            case 1 -> novoJogador = new JogadorAzarado(jogador.getCor());
            case 2 -> novoJogador = new JogadorSortudo(jogador.getCor());
            default -> novoJogador = new JogadorNormal(jogador.getCor());
        }

        // Preservar informações do jogador original
        novoJogador.setCasa(jogador.getCasa());
        novoJogador.setNumJogadas(jogador.getNumJogadas());

        // Substituir na lista de jogadores
        jogadores.set(jogadores.indexOf(jogador), novoJogador);

        System.out.printf("Jogador %s agora é do tipo %s.\n", novoJogador.getCor(), novoJogador.getClass().getSimpleName());
    }


    private void enviarOutroAoInicio(Jogador jogador) {
        System.out.println("Escolha um jogador para enviar ao início.");
        System.out.println("Jogadores disponíveis:");

        // Listar os jogadores com índices
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i) != jogador) {
                System.out.printf("%d - %s (Casa: %d)\n", i, jogadores.get(i).getCor(), jogadores.get(i).getCasa());
            }
        }

        int escolhidoIndex = -1;

        // Validar a escolha do jogador
        while (escolhidoIndex < 0 || escolhidoIndex >= jogadores.size() || jogadores.get(escolhidoIndex) == jogador) {
            System.out.print("Digite o número do jogador que será enviado ao início: ");
            try {
                escolhidoIndex = Integer.parseInt(scanner.nextLine()); // Lê como String e converte para int
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }

        Jogador escolhido = jogadores.get(escolhidoIndex);
        escolhido.setCasa(0); // Enviar ao início

        System.out.printf("Jogador %s foi enviado ao início por %s.\n", escolhido.getCor(), jogador.getCor());
    }


    private void trocarComUltimo(Jogador jogador) {
        Jogador ultimo = jogadores.stream()
                .min(Comparator.comparingInt(Jogador::getCasa))
                .orElse(jogador);

        if (ultimo == jogador) {
            System.out.println("Jogador já é o último, não troca de lugar.");
            return;
        }

        int tempCasa = jogador.getCasa();
        jogador.setCasa(ultimo.getCasa());
        ultimo.setCasa(tempCasa);

        System.out.printf("Jogador %s trocou de lugar com %s.\n", jogador.getCor(), ultimo.getCor());
    }


    private void debugJogada(Jogador jogador) {
        System.out.println("Modo debug ativado. Insira a casa para onde o jogador deve ir:");
        int novaCasa = -1;
        while (novaCasa < 0 || novaCasa >= TOTAL_CASAS) {
            try {
                novaCasa = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número entre 0 e " + (TOTAL_CASAS - 1));
            }
        }
        jogador.setCasa(novaCasa);
        jogador.setNumJogadas(jogador.getNumJogadas() + 1);
    }

    private void exibirResultados() {
        System.out.println("\nResultados finais:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador %s: Casa %d, Jogadas %d\n", jogador.getCor(), jogador.getCasa(), jogador.getNumJogadas());
        }
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void removerJogador(){
        System.out.println("Escolha um jogador para remover:");
        int i;
        for (i = 0; i < jogadores.size(); i++) {
            System.out.printf("%d - %s\n", i+1, jogadores.get(i).getCor());
        }
        i=(scanner.nextInt())-1;
        scanner.nextLine();
        jogadores.remove(i);
        System.out.println("Jogador removido.");
    }
}