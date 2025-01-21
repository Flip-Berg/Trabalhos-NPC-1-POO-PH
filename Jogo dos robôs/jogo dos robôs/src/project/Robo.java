package project;

public class Robo {
    private int posX; // Coordenada no eixo X
    private int posY; // Coordenada no eixo Y
    private String cor; // Cor do robô

    public Robo(String cor) {
        this.posX = 0;
        this.posY = 0;
        this.cor = cor;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    // Metodo para mover o robo usando string
    public void mover(String direcao) throws MovimentoInvalidoException {
        switch (direcao.toLowerCase()) {
            case "up":
                if (posY + 1 > 3) {
                    throw new MovimentoInvalidoException("Movimento inválido: up");
                }
                posY++;
                break;

            case "down":
                if (posY - 1 < 0) {
                    throw new MovimentoInvalidoException("Movimento inválido: down");
                }
                posY--;
                break;

            case "right":
                if (posX + 1 > 3) {
                    throw new MovimentoInvalidoException("Movimento inválido: right");
                }
                posX++;
                break;

            case "left":
                if (posX - 1 < 0) {
                    throw new MovimentoInvalidoException("Movimento inválido: left");
                }
                posX--;
                break;

            default:
                throw new MovimentoInvalidoException("Direção inválida: " + direcao);
        }

        // Mostra a posição do robô após o movimento
        System.out.printf("Robô %s: (" + posX + ", " + posY + ")\n", cor);
    }

    // Metodo para mover o robo usando um numero inteiro
    public void mover(int direcao) throws MovimentoInvalidoException {
        int novoX = posX;
        int novoY = posY;

        switch (direcao) {
            case 1 -> novoY++; // Up
            case 2 -> novoY--; // Down
            case 3 -> novoX++; // Right
            case 4 -> novoX--; // Left
            default -> throw new MovimentoInvalidoException("Direção inválida.");
        }

        // Validar se o novo movimento está dentro do tabuleiro
        if (novoX < 0 || novoX >= 4 || novoY < 0 || novoY >= 4) {
            throw new MovimentoInvalidoException("Movimento inválido: fora dos limites.");
        }

        // Atualizar a posição
        posX = novoX;
        posY = novoY;
        System.out.println("Robô " + cor + ": (" + posX + ", " + posY + ")");
    }

    // Metodo para verificar se o robo encontrou o alimento
    public boolean encontrouAlimento(int alimentoX, int alimentoY) {
        return this.posX == alimentoX && this.posY == alimentoY;
    }
}
