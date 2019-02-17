package Main;


import Energy.Constants;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import Struct.*;
import static Struct.Constants.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int width = 800;
        final int height = 600;
        // Создаем сруктуру
        Struct struct = new Struct();

        GridPane gridMain = new GridPane();
        GridPane menu = new GridPane();
        Group groupAtoms = new Group();


        //задаем концентрацию для Ge
        double concentrationGe = 0.2;
        int countCells = 10;

        //Параметр решетки вычисляется по закону Вегарда
        double paramCell = paramCellSI * (1 - concentrationGe) + paramCellGE * concentrationGe;
        //Задаем параметры потенциала Терсоффа
        Constants.setTersoff();


        if (struct.сreateCubicStructure(countCells, paramCell)) {
            System.out.println("Структура успешно создана");
            if (struct.setAtomsTypes(concentrationGe, 0)) {
                System.out.println("Типы атомов в структуре заданы");
                if (struct.setAtomsNeighbourhood(0.3)) {
                    System.out.println("Атомы - соседи заданы");
                    if (struct.setAtomsSecondNeighbourhood(0.5)) {
                        System.out.println("Вторые соседи атомов заданы");
                    }
                }
            }
        }
        groupAtoms.getChildren().add(new Sphere());
        for (int i = 0; i <struct.getStructCountAtoms(); i++) {
            Sphere tempSphere = new Sphere();
            tempSphere.setRadius(3);
            tempSphere.setTranslateX(struct.getAtom(i).getAtomX() * 100+20);
            tempSphere.setTranslateY(struct.getAtom(i).getAtomY() * 100+20);
            tempSphere.setTranslateZ(struct.getAtom(i).getAtomZ() * 100+20);
            if(struct.getAtom(i).getAtomType() == AtomType.atomType.Ge.ordinal()){
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.RED);
                material.setSpecularColor(Color.ORANGE);
                tempSphere.setMaterial(material);
            }
            else{
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.BLUE);
                material.setSpecularColor(Color.PURPLE);
                tempSphere.setMaterial(material);
            }


            groupAtoms.getChildren().add(tempSphere);
        }
        groupAtoms.setRotationAxis(new Point3D(1,1,1));
        groupAtoms.setRotate(30);
        gridMain.add(groupAtoms,0,0);
        System.out.println(struct.getStructInfo());

        primaryStage.setTitle("SiGe");
        primaryStage.setScene(new Scene(gridMain, width, height));
        primaryStage.show();

        int ds = 0;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
