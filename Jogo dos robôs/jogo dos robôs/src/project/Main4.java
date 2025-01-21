package project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main4 {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Criando o robô normal e o robô inteligente
        System.out.print("Digite a cor do robô normal: ");
        String corRoboNormal = scanner.nextLine();
        Robo roboNormal = new Robo(corRoboNormal);

        System.out.print("Digite a cor do robô inteligente: ");
        String corRoboInteligente = scanner.nextLine();
        RoboInteligente roboInteligente = new RoboInteligente(corRoboInteligente);

        // Definindo a posição do alimento
        System.out.println("Digite as coordenadas do alimento (dentro de uma matriz 4x4, de 0 a 3):");
        System.out.print("X: ");
        int alimentoX = scanner.nextInt();
        System.out.print("Y: ");
        int alimentoY = scanner.nextInt();

        // Garantir que o alimento está dentro dos limites da matriz
        if (alimentoX < 0 || alimentoX > 3 || alimentoY < 0 || alimentoY > 3) {
            System.out.println("As coordenadas do alimento devem estar entre 0 e 3!");
            return;
        }

        // Criando obstáculos
        ArrayList<Obstaculo> obstaculos = new ArrayList<>();
        System.out.print("Quantos obstáculos deseja adicionar? ");
        int numObstaculos = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        for (int i = 0; i < numObstaculos; i++) {
            System.out.println("Adicionar obstáculo " + (i + 1) + ":");
            System.out.print("Tipo (1 - project.Bomba, 2 - project.Rocha): ");
            int tipo = scanner.nextInt();
            System.out.print("X: ");
            int x = scanner.nextInt();
            System.out.print("Y: ");
            int y = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            if (x < 0 || x > 3 || y < 0 || y > 3) {
                System.out.println("As coordenadas devem estar entre 0 e 3. Obstáculo ignorado.");
                continue;
            }

            if (tipo == 1) {
                obstaculos.add(new Bomba(i + 1, x, y));
            } else if (tipo == 2) {
                obstaculos.add(new Rocha(i + 1, x, y));
            } else {
                System.out.println("Tipo inválido. Obstáculo ignorado.");
            }
        }

        System.out.println("\nOs robôs começarão na posição (0, 0).");
        System.out.println("O alimento está na posição (" + alimentoX + ", " + alimentoY + ").");

        // Contadores de movimentos para cada robô
        int movimentosRoboNormal = 0;
        int movimentosRoboInteligente = 0;

        // Loop para movimentar os robôs até que um deles encontre o alimento ou ambos explodam
        while ((!roboNormal.encontrouAlimento(alimentoX, alimentoY) && !roboInteligente.encontrouAlimento(alimentoX, alimentoY))
                && (roboNormal.getPosX() >= 0 || roboInteligente.getPosX() >= 0)) {

            // Movimento do robô normal
            if (!roboNormal.encontrouAlimento(alimentoX, alimentoY) && roboNormal.getPosX() >= 0) {
                int comandoNormal = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                if (!verificarObstaculos(roboNormal, obstaculos, comandoNormal)) {
                    try {
                        roboNormal.mover(comandoNormal);
                        movimentosRoboNormal++;
                        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
                            System.out.println("O robô " + roboNormal.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                            break;
                        }
                    } catch (MovimentoInvalidoException e) {
                        System.out.println("Robô " + roboNormal.getCor() + ": " + e.getMessage());
                    }
                }
                Thread.sleep(1000); // Retarda a execução
            }

            // Movimento do robô inteligente
            if (!roboInteligente.encontrouAlimento(alimentoX, alimentoY) && roboInteligente.getPosX() >= 0) {
                int comandoInteligente = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                if (!verificarObstaculos(roboInteligente, obstaculos, comandoInteligente)) {
                    try {
                        roboInteligente.mover(comandoInteligente);
                        movimentosRoboInteligente++;
                        if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
                            System.out.println("O robô " + roboInteligente.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                            break;
                        }
                    } catch (MovimentoInvalidoException e) {
                        System.out.println("Robô " + roboInteligente.getCor() + ": " + e.getMessage());
                    }
                }
                Thread.sleep(1000); // Retarda a execução
            }
        }

        // Exibindo o resultado final
        System.out.println("\nRESULTADO:");
        System.out.println("Movimentos do Robô Normal " + roboNormal.getCor() + ": " + movimentosRoboNormal);
        System.out.println("Movimentos do Robô Inteligente " + roboInteligente.getCor() + ": " + movimentosRoboInteligente);

        scanner.close();
    }

    // Metodo para verificar se o próximo movimento atinge um obstáculo
    public static boolean verificarObstaculos(Robo robo, ArrayList<Obstaculo> obstaculos, int direcao) {
        int proxX = robo.getPosX();
        int proxY = robo.getPosY();

        switch (direcao) {
            case 1: // Up
                proxY++;
                break;
            case 2: // Down
                proxY--;
                break;
            case 3: // Right
                proxX++;
                break;
            case 4: // Left
                proxX--;
                break;
        }

        for (Obstaculo obstaculo : obstaculos) {
            if (proxX == obstaculo.getPosX() && proxY == obstaculo.getPosY()) {
                obstaculo.bater(robo);
                if (obstaculo instanceof Bomba) {
                    obstaculos.remove(obstaculo);
                }
                return true; // Obstáculo bloqueou o movimento
            }
        }

        return false; // Nenhum obstáculo bloqueou o movimento
    }
}

