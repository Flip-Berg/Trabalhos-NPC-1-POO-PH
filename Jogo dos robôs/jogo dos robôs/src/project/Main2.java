package project;

import java.util.Random;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Criando dois robôs
        System.out.print("Digite a cor do robô 1: ");
        String corRobo1 = scanner.nextLine();
        Robo robo1 = new Robo(corRobo1);

        System.out.print("Digite a cor do robô 2: ");
        String corRobo2 = scanner.nextLine();
        Robo robo2 = new Robo(corRobo2);

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

        // Contadores de movimentos válidos e inválidos para cada robô
        int movimentosValidosRobo1 = 0;
        int movimentosInvalidosRobo1 = 0;
        int movimentosValidosRobo2 = 0;
        int movimentosInvalidosRobo2 = 0;

        // Loop para movimentar os robôs até que um deles encontre o alimento
        while (!robo1.encontrouAlimento(alimentoX, alimentoY) && !robo2.encontrouAlimento(alimentoX, alimentoY)) {
            // Movimento do robô 1
            int comandoRobo1 = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
            try {
                robo1.mover(comandoRobo1);
                movimentosValidosRobo1++;
            } catch (MovimentoInvalidoException e) {
                System.out.println("Robô " + robo1.getCor() + ": " + e.getMessage());
                movimentosInvalidosRobo1++;
            }

            // Retardar a execução para visualizar o movimento
            Thread.sleep(1000);

            // Verificar se o robo 1 encontrou o alimento
            if (robo1.encontrouAlimento(alimentoX, alimentoY)) {
                System.out.println("O Robô " + robo1.getCor() + " encontrou o alimento!");
                break;
            }

            // Movimento do robô 2
            int comandoRobo2 = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
            try {
                robo2.mover(comandoRobo2);
                movimentosValidosRobo2++;
            } catch (MovimentoInvalidoException e) {
                System.out.println("Robô " + robo2.getCor() + ": " + e.getMessage());
                movimentosInvalidosRobo2++;
            }

            // Retardar a execução para visualizar o movimento
            Thread.sleep(1000);

            // Verificar se o robô 2 encontrou o alimento
            if (robo2.encontrouAlimento(alimentoX, alimentoY)) {
                System.out.println("O Robô " + robo2.getCor() + " encontrou o alimento!");
                break;
            }
        }

        // Exibindo o resultado final
        System.out.println("\nRESULTADO:");
        System.out.println("Movimentos válidos do Robô " + robo1.getCor() + ": " + movimentosValidosRobo1);
        System.out.println("Movimentos inválidos do Robô " + robo1.getCor() + ": " + movimentosInvalidosRobo1);
        System.out.println("Movimentos válidos do Robô " + robo2.getCor() + ": " + movimentosValidosRobo2);
        System.out.println("Movimentos inválidos do Robô " + robo2.getCor() + ": " + movimentosInvalidosRobo2);

        scanner.close();
    }
}
