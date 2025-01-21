package project;

public class Rocha extends Obstaculo {
    public Rocha(int id, int posX, int posY) {
        super(id, posX, posY);
    }

    @Override
    public void bater(Robo robo) {
        System.out.println("Robô " + robo.getCor() + " bateu em uma rocha e voltou à posição anterior!");
    }
}
