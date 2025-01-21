package project;

import java.util.Random;
import java.util.Scanner;

public class Main3 {
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

        // Garantir que o alimento esteja em posições válidas
        if (alimentoX < 0 || alimentoX > 3 || alimentoY < 0 || alimentoY > 3) {
            System.out.println("As coordenadas do alimento devem estar entre 0 e 3!");
            return;
        }

        System.out.println("\nOs robôs começarão na posição (0, 0).");
        System.out.println("O alimento está na posição (" + alimentoX + ", " + alimentoY + ").");

        // Contadores de movimentos para cada robô
        int movimentosRoboNormal = 0;
        int movimentosRoboInteligente = 0;

        // Loop para movimentar os robôs até que ambos encontrem o alimento
        while (!roboNormal.encontrouAlimento(alimentoX, alimentoY) || !roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            // Movimento do robô normal
            if (!roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
                int comandoNormal = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                try {
                    roboNormal.mover(comandoNormal);
                    movimentosRoboNormal++;
                    if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
                        System.out.println("O robô " + roboNormal.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robô " + roboNormal.getCor() + ": " + e.getMessage());
                }
                Thread.sleep(1000); // Retarda a execução
            }

            // Movimento do robô inteligente
            if (!roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
                int comandoInteligente = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                try {
                    roboInteligente.mover(comandoInteligente);
                    movimentosRoboInteligente++;
                    if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
                        System.out.println("O robô " + roboInteligente.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robô " + roboInteligente.getCor() + ": " + e.getMessage());
                }
                Thread.sleep(1000); // Retarda a execução
            }
        }

        // Exibindo o resultado final
        System.out.println("\nRESULTADO:");
        System.out.println("Movimentos do Robô Normal (" + roboNormal.getCor() + "): " + movimentosRoboNormal);
        System.out.println("Movimentos do Robô Inteligente (" + roboInteligente.getCor() + "): " + movimentosRoboInteligente);

        scanner.close();
    }
}

