package Main;


import Energy.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Struct.*;
import Energy.*;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SiGe");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Struct struct = new Struct();
        if (struct.сreateCubicStructure(5, 0.2)) {
            System.out.println("Структура успешно создана");
            if (struct.setAtomsTypes(0.5, 0)) {
                System.out.println("Типы атомов в структуре заданы");
            }
        }

        int temp = Math.abs(new Random().nextInt()) % struct.getStructCountAtoms();
        System.out.println(struct.getAtom(temp).getAtomInfo());
        temp = Math.abs(new Random().nextInt()) % struct.getStructCountAtoms();
        System.out.println(struct.getAtom(temp).getAtomInfo());
        System.out.println(struct.getStructCountAtoms());
        Constants.setTersoff();
        System.out.println(Constants.tersoffs[0].A);
        int t = 0;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
