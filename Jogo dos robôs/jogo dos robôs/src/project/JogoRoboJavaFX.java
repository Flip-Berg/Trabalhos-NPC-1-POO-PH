package project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class JogoRoboJavaFX extends Application {
    private static final int GRID_SIZE = 4; // Tamanho da matriz 4x4
    private Label[][] gridLabels = new Label[GRID_SIZE][GRID_SIZE];
    private Robo robo1, robo2;
    private int alimentoX, alimentoY;
    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    private String corRobo1 = "blue", corRobo2 = "red"; // Cores padrões dos robôs


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Tela inicial com menu principal
        VBox menuPrincipal = criarMenuPrincipal(primaryStage);
        Scene cenaMenuPrincipal = new Scene(menuPrincipal, 400, 300);

        primaryStage.setTitle("Jogo dos Robôs - Configuração");
        primaryStage.setScene(cenaMenuPrincipal);
        primaryStage.show();
    }

    private VBox criarMenuPrincipal(Stage primaryStage) {
        VBox menu = new VBox(10);
        menu.setAlignment(Pos.CENTER);

        Label titulo = new Label("Jogo dos Robôs - Configuração");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button configurarRobos = new Button("Configurar Robôs");
        configurarRobos.setOnAction(e -> configurarRobos(primaryStage));

        Button configurarObstaculos = new Button("Configurar Obstáculos");
        configurarObstaculos.setOnAction(e -> configurarObstaculos(primaryStage));

        Button configurarAlimento = new Button("Configurar Alimento");
        configurarAlimento.setOnAction(e -> configurarAlimento(primaryStage));

        Button iniciarJogo = new Button("Iniciar Jogo");
        iniciarJogo.setOnAction(e -> iniciarJogo(primaryStage));

        menu.getChildren().addAll(titulo, configurarRobos, configurarObstaculos, configurarAlimento, iniciarJogo);
        return menu;
    }

    private void configurarRobos(Stage primaryStage) {
        VBox tela = new VBox(10);
        tela.setAlignment(Pos.CENTER);

        Label titulo = new Label("Configurar Robôs");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<String> tipoRobo1 = new ComboBox<>();
        tipoRobo1.getItems().addAll("Normal", "Inteligente");
        tipoRobo1.setValue("Normal");

        ColorPicker seletorCor1 = new ColorPicker();
        seletorCor1.setValue(javafx.scene.paint.Color.BLUE);

        ComboBox<String> tipoRobo2 = new ComboBox<>();
        tipoRobo2.getItems().addAll("Normal", "Inteligente");
        tipoRobo2.setValue("Inteligente");

        ColorPicker seletorCor2 = new ColorPicker();
        seletorCor2.setValue(javafx.scene.paint.Color.RED);

        Button salvar = new Button("Salvar");
        salvar.setOnAction(e -> {
            corRobo1 = toHexColor(seletorCor1.getValue());
            corRobo2 = toHexColor(seletorCor2.getValue());
            robo1 = tipoRobo1.getValue().equals("Normal") ? new Robo("1") : new RoboInteligente("Robô 1");
            robo2 = tipoRobo2.getValue().equals("Normal") ? new Robo("2") : new RoboInteligente("Robô 2");
            voltarParaMenuPrincipal(primaryStage);
        });

        tela.getChildren().addAll(titulo, new Label("Robô 1"), tipoRobo1, seletorCor1, new Label("Robô 2"), tipoRobo2, seletorCor2, salvar);
        Scene cena = new Scene(tela, 400, 300);
        primaryStage.setScene(cena);
    }

    private void configurarObstaculos(Stage primaryStage) {
        VBox tela = new VBox(10);
        tela.setAlignment(Pos.CENTER);

        Label titulo = new Label("Configurar Obstáculos");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField posX = new TextField();
        posX.setPromptText("Posição X (0-3)");
        TextField posY = new TextField();
        posY.setPromptText("Posição Y (0-3)");
        ComboBox<String> tipo = new ComboBox<>();
        tipo.getItems().addAll("Bomba", "Rocha");
        tipo.setValue("Bomba");

        Button adicionar = new Button("Adicionar");
        adicionar.setOnAction(e -> {
            int x = Integer.parseInt(posX.getText());
            int y = Integer.parseInt(posY.getText());
            Obstaculo obstaculo = tipo.getValue().equals("Bomba") ? new Bomba(obstaculos.size() + 1, x, y) : new Rocha(obstaculos.size() + 1, x, y);
            obstaculos.add(obstaculo);
            posX.clear();
            posY.clear();
        });

        Button salvar = new Button("Salvar");
        salvar.setOnAction(e -> voltarParaMenuPrincipal(primaryStage));

        tela.getChildren().addAll(titulo, posX, posY, tipo, adicionar, salvar);
        Scene cena = new Scene(tela, 400, 300);
        primaryStage.setScene(cena);
    }

    private void configurarAlimento(Stage primaryStage) {
        VBox tela = new VBox(10);
        tela.setAlignment(Pos.CENTER);

        Label titulo = new Label("Configurar Alimento");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField posX = new TextField();
        posX.setPromptText("Posição X (0-3)");
        TextField posY = new TextField();
        posY.setPromptText("Posição Y (0-3)");

        Button salvar = new Button("Salvar");
        salvar.setOnAction(e -> {
            alimentoX = Integer.parseInt(posX.getText());
            alimentoY = Integer.parseInt(posY.getText());
            voltarParaMenuPrincipal(primaryStage);
        });

        tela.getChildren().addAll(titulo, posX, posY, salvar);
        Scene cena = new Scene(tela, 400, 300);
        primaryStage.setScene(cena);
    }

    private void iniciarJogo(Stage primaryStage) {
        // Criação do tabuleiro
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Label label = new Label(" ");
                label.setMinSize(50, 50);
                label.setStyle("-fx-border-color: black; -fx-alignment: center;");
                grid.add(label, j, i);
                gridLabels[i][j] = label;
            }
        }

        // Atualiza a grid com as configurações iniciais
        atualizarGrid();

        Scene cena = new Scene(grid, 400, 400);
        primaryStage.setScene(cena);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        // Movimentação dos robôs
        movimentarRobos();
    }

    private void atualizarGrid() {
        // Limpa o tabuleiro
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridLabels[i][j].setText(" ");
                gridLabels[i][j].setStyle("-fx-border-color: black; -fx-alignment: center;");
            }
        }

        // Atualiza o alimento
        gridLabels[alimentoY][alimentoX].setText("🍎");

        // Atualiza os obstáculos
        for (Obstaculo obstaculo : obstaculos) {
            String simbolo = obstaculo instanceof Bomba ? "💣" : "🪨";
            gridLabels[obstaculo.getPosY()][obstaculo.getPosX()].setText(simbolo);
        }

        // Atualiza os robôs
        if (robo1.getPosX() >= 0 && robo1.getPosY() >= 0) {
            gridLabels[robo1.getPosY()][robo1.getPosX()].setText("🤖");
            gridLabels[robo1.getPosY()][robo1.getPosX()].setStyle("-fx-background-color: " + corRobo1 + "; -fx-text-fill: white;");
        }

        if (robo2.getPosX() >= 0 && robo2.getPosY() >= 0) {
            gridLabels[robo2.getPosY()][robo2.getPosX()].setText("🤖");
            gridLabels[robo2.getPosY()][robo2.getPosX()].setStyle("-fx-background-color: " + corRobo2 + "; -fx-text-fill: white;");
        }
    }

    private void movimentarRobos() {
        new Thread(() -> {
            Random random = new Random();

            while ((robo1.getPosX() >= 0 || robo2.getPosX() >= 0) &&
                    (!robo1.encontrouAlimento(alimentoX, alimentoY) || !robo2.encontrouAlimento(alimentoX, alimentoY))) {

                // Movimento do robô 1
                if (!robo1.encontrouAlimento(alimentoX, alimentoY) && robo1.getPosX() >= 0) {
                    int comando1 = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                    if (!verificarObstaculos(robo1, obstaculos, comando1)) {
                        try {
                            robo1.mover(comando1);
                            if (robo1.encontrouAlimento(alimentoX, alimentoY)) {
                                System.out.println("O robô " + robo1.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                                break;
                            }
                        } catch (MovimentoInvalidoException e) {
                            System.out.println("Robô " + robo1.getCor() + ": " + e.getMessage());
                        }
                    }
                    try {
                        Thread.sleep(500); // Retarda a execução para visualização
                    } catch (InterruptedException ignored) {
                    }
                }

                // Movimento do robô 2
                if (!robo2.encontrouAlimento(alimentoX, alimentoY) && robo2.getPosX() >= 0) {
                    int comando2 = random.nextInt(4) + 1; // Gera um número aleatório entre 1 e 4
                    if (!verificarObstaculos(robo2, obstaculos, comando2)) {
                        try {
                            robo2.mover(comando2);
                            if (robo2.encontrouAlimento(alimentoX, alimentoY)) {
                                System.out.println("O robô " + robo2.getCor() + " encontrou o alimento na posição (" + alimentoX + ", " + alimentoY + ")!");
                                break;
                            }
                        } catch (MovimentoInvalidoException e) {
                            System.out.println("Robô " + robo2.getCor() + ": " + e.getMessage());
                        }
                    }
                    try {
                        Thread.sleep(500); // Retarda a execução para visualização
                    } catch (InterruptedException ignored) {
                    }
                }

                // Atualizar a interface gráfica
                javafx.application.Platform.runLater(this::atualizarGrid);
            }

            // Exibir mensagem final quando o jogo termina
            if (robo1.encontrouAlimento(alimentoX, alimentoY)) {
                System.out.println("O robô " + robo1.getCor() + " venceu!");
            } else if (robo2.encontrouAlimento(alimentoX, alimentoY)) {
                System.out.println("O robô " + robo2.getCor() + " venceu!");
            } else {
                System.out.println("Ambos os robôs foram desclassificados.");
            }
        }).start();
    }


    private void voltarParaMenuPrincipal(Stage primaryStage) {
        VBox menuPrincipal = criarMenuPrincipal(primaryStage);
        Scene cenaMenuPrincipal = new Scene(menuPrincipal, 400, 300);
        primaryStage.setScene(cenaMenuPrincipal);
    }

    private String toHexColor(javafx.scene.paint.Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    private boolean verificarObstaculos(Robo robo, ArrayList<Obstaculo> obstaculos, int comando) {
        int proxX = robo.getPosX();
        int proxY = robo.getPosY();

        // Calcula a próxima posição com base no comando
        switch (comando) {
            case 1 -> proxY++; // Up
            case 2 -> proxY--; // Down
            case 3 -> proxX++; // Right
            case 4 -> proxX--; // Left
        }

        // Verifica se há um obstáculo na próxima posição
        for (Obstaculo obstaculo : obstaculos) {
            if (proxX == obstaculo.getPosX() && proxY == obstaculo.getPosY()) {
                obstaculo.bater(robo); // Aplica o efeito do obstáculo
                if (obstaculo instanceof Bomba) {
                    obstaculos.remove(obstaculo); // Remove bomba se robo bater
                }
                return true; // Indica que o movimento foi bloqueado
            }
        }

        return false; // Nenhum obstáculo bloqueou o movimento
    }
}
