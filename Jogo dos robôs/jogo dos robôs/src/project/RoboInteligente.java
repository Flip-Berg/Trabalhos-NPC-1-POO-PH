package project;

import java.util.Random;

public class RoboInteligente extends Robo {
    private int ultimaDirecaoInvalida; // Armazena a última direção inválida
    private Random random;

    public RoboInteligente(String cor) {
        super(cor);
        this.ultimaDirecaoInvalida = -1; // Inicialmente, nenhuma direção foi inválida
        this.random = new Random();
    }

    @Override
    public void mover(int direcao) throws MovimentoInvalidoException {
        // Se o movimento foi inválido na última rodada, escolhe uma nova direção
        while (direcao == ultimaDirecaoInvalida) {
            direcao = random.nextInt(4) + 1; // Gera uma nova direção aleatória
        }

        try {
            super.mover(direcao); // Tenta se mover
            ultimaDirecaoInvalida = -1; // Reseta a última direção inválida em caso de sucesso
        } catch (MovimentoInvalidoException e) {
            ultimaDirecaoInvalida = direcao; // Salva a direção inválida
            throw e; // Repassa a exceção
        }
    }
}
