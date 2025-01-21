package project;

public abstract class Obstaculo {
    protected int id; // Identificador único do obstáculo
    protected int posX; // Coordenada no eixo X
    protected int posY; // Coordenada no eixo Y

    public Obstaculo(int id, int posX, int posY) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // Metodo abstrato que define o comportamento ao bater no obstáculo
    public abstract void bater(Robo robo);
}

