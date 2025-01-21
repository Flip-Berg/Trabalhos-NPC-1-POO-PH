package project;

public class Bomba extends Obstaculo {
    public Bomba(int id, int posX, int posY) {
        super(id, posX, posY);
    }

    @Override
    public void bater(Robo robo) {
        System.out.println("Robô (" + robo.getCor() + ") explodiu ao bater na bomba  " + id + "!");
        robo.setPosX(-1); // Move o robô para fora do tabuleiro para indicar que está fora do jogo
        robo.setPosY(-1);
    }
}

