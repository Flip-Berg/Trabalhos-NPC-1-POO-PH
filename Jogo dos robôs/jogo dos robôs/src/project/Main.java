package project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instanciando o robô
        System.out.print("Digite a cor do robô: ");
        String corRobo = scanner.nextLine();
        Robo robo = new Robo(corRobo);

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

        System.out.println("Movimente o robô usando os comandos 'up', 'down', 'right' ou 'left'.");
        System.out.println("O robô começará na posição (0, 0).");

        // Loop para movimentar o robô até encontrar o alimento
        scanner.nextLine(); // Consumir a quebra de linha restante
        while (!robo.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.print("Digite o comando para mover o robô: ");
            String comando = scanner.nextLine();

            try {
                robo.mover(comando);
            } catch (MovimentoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("O robô encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
        scanner.close();
    }
}
