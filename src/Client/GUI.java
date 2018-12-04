package Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class GUI extends Application {

    public static final int MIN_TIME_VALUE = 3;
    public static final int MAX_TIME_VAULE = 6;

    public static final int MIN_BUTTONS_VALUE = 1;
    public static final int MAX_BUTTONS_VALUE = 4;

    public static final int NUMBER_OF_BUTTONS = 16;

    public static Button[] btns = new Button[16];
    private Button startButton = new Button("START");
    private int start = 1;
    public boolean running = false;
    public boolean runagain = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("ButtonGame v0.1 Ahrer/Kamleitner");

        initBtnsArray();
        Group root = new Group();

        startButton.setPrefSize(1000, 100);
        startButton.setStyle("-fx-background-color: #90aa00");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(start == 1){
                    System.out.println("started");
                    startButton.setText("STOP");
                    start = 0;
                    running = true;

                    Thread gameThread = new Thread(){
                        @Override
                        public void run() {
                            while (running){

                                try {
                                    Thread.sleep(getRandomTime());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                int anzButtons = getRandomAmountOfButtons();

                                for (int i = 0; i <= anzButtons ; i++) {
                                    int selectedNumber = getLineRow()*4 + getLineRow() - 1;
                                    btns[selectedNumber].setDisable(false);
                                    btns[selectedNumber].setStyle("-fx-background-color: #00ff00");
                                }

                                for(int bt = 0; bt <= 15; bt++){
                                    if(btns[bt].isDisabled() == false){
                                        runagain = true;
                                    }
                                }
                                while (runagain){
                                    int btr = 0;
                                    for(int bt = 0; bt <= 15; bt++){
                                        if(btns[bt].isDisabled()){
                                            btr++;
                                            if(btr == 16){
                                                runagain = false;
                                            }
                                        }
                                    }
                                }
                            }
                            // while ...
                        }
                    };
                    gameThread.start();

                } else {
                    System.out.println("stopped");
                    startButton.setText("START");
                    start = 1;
                    running = false;
                }
            }
        });


        BorderPane abc = new BorderPane();
        abc.setCenter(getGrid());
        abc.setBottom(startButton);

        root.getChildren().add(abc);
        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private Pane getGrid() {
        int i = 0;
        int rowIndex = 1;
        GridPane gridPane = new GridPane();

        for(Button b : btns) {
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    b.setStyle("-fx-background-color: #dedede");
                    b.setDisable(true);
                }
            });

            if(i%4 == 0){
                rowIndex++;
                i=0;
            }
            gridPane.add(b, i*(i+(int)b.getWidth()), rowIndex);
            i++;
        }
        return gridPane;
    }

    private void initBtnsArray() {
        for(int i = 0; i < btns.length; i++) {
            btns[i] = new Button("Button");
            btns[i].setPrefSize(250, 100);
            btns[i].setDisable(true);
        }
    }

    public static int getRandomTime(){
        Random random = new Random();
        int randomNumber = random.nextInt(MAX_TIME_VAULE - MIN_TIME_VALUE) + MIN_TIME_VALUE;
        randomNumber = randomNumber * 1000; // *1000 for s
        return(randomNumber);
    }

    protected int getRandomAmountOfButtons(){
        Random random = new Random();
        int randomNumber = random.nextInt(MAX_BUTTONS_VALUE - MIN_BUTTONS_VALUE) + MIN_BUTTONS_VALUE;
        return(randomNumber);
    }

    protected int getLineRow(){
        Random random = new Random();
        int randomNumber = random.nextInt((int)Math.sqrt(NUMBER_OF_BUTTONS) - 1) + 1;
        return(randomNumber);
    }
}