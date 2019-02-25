package Main;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
        Group groupAtoms = new Group();


        //задаем концентрацию для Ge
        double concentrationGe = 0;
        int countCells = 5;

        //Параметр решетки вычисляется по закону Вегарда
        double paramCell = paramCellSI * (1 - concentrationGe) + paramCellGE * concentrationGe;
        if (struct.сreateCubicStructure(countCells, paramCell)) {
            System.out.println("Структура успешно создана");
            if (struct.setAtomsTypes(concentrationGe, 0)) {
                System.out.println("Типы атомов в структуре заданы");
                if (struct.setAtomsNeighbourhood(paramCell)) {
                    System.out.println("Атомы - соседи заданы");
                    if (struct.setAtomsSecondNeighbourhood( 2 * paramCell * Math.sqrt(3.0) / 4.0)) {
                        System.out.println("Вторые соседи атомов заданы");
                    }
                }
            }
        }
        System.out.println("\n\rЭнергия структуры - " + Energy.Tersoff.totalTersoffEnergy(struct));
        System.out.println(Energy.Tersoff.atomEnergy(struct,999));


        for (int i = 0; i < struct.getStructCountAtoms(); i++) {
            Sphere tempSphere = new Sphere();
            tempSphere.setTranslateX((struct.getAtom(i).getAtomX() - struct.getMaxX() / 2) * 200);
            tempSphere.setTranslateY((struct.getAtom(i).getAtomY() - struct.getMaxY() / 2) * 200);
            tempSphere.setTranslateZ((struct.getAtom(i).getAtomZ() - struct.getMaxZ() / 2) * 200);
            if (struct.getAtom(i).getAtomType() == AtomType.atomType.Ge.ordinal()) {
                tempSphere.setRadius(8);
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.RED);
                material.setSpecularColor(Color.ORANGE);
                tempSphere.setMaterial(material);
            } else {
                tempSphere.setRadius(5);
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.BLUE);
                material.setSpecularColor(Color.PURPLE);
                tempSphere.setMaterial(material);
            }
            groupAtoms.getChildren().add(tempSphere);
        }


        gridMain.add(groupAtoms, 0, 0);
        System.out.println(struct.getStructInfo());

        PerspectiveCamera camera = new PerspectiveCamera(true);

        groupAtoms.getChildren().add(camera);
        camera.setFarClip(5000.0);
        camera.setTranslateZ(-1200);

        Scene scene = new Scene(gridMain, width, height, true);

        scene.setFill(Color.BLACK);

        scene.setOnKeyPressed(event -> {
                    KeyCode keycode = event.getCode();
                    if (keycode == KeyCode.W) {
                        camera.setTranslateY(camera.getTranslateY() + 10);
                    }
                    if (keycode == KeyCode.S) {
                        camera.setTranslateY(camera.getTranslateY() - 10);
                    }
                    if (keycode == KeyCode.A) {
                        camera.setTranslateX(camera.getTranslateX() + 10);
                    }
                    if (keycode == KeyCode.D) {
                        camera.setTranslateX(camera.getTranslateX() - 10);
                    }
                    if (keycode == KeyCode.Q) {
                        camera.setTranslateZ(camera.getTranslateZ() - 10);
                    }
                    if (keycode == KeyCode.E) {
                        camera.setTranslateZ(camera.getTranslateZ() + 10);
                    }
                    if (keycode == KeyCode.NUMPAD8) {
                        camera.setRotationAxis(new Point3D(1, 0, 0));
                        camera.setRotate(camera.getRotate() + 1);
                    }
                    if (keycode == KeyCode.NUMPAD2) {
                        camera.setRotationAxis(new Point3D(1, 0, 0));
                        camera.setRotate(camera.getRotate() - 1);
                    }
                    if (keycode == KeyCode.NUMPAD4) {
                        camera.setRotationAxis(new Point3D(0, 1, 0));
                        camera.setRotate(camera.getRotate() + 1);
                    }
                    if (keycode == KeyCode.NUMPAD6) {
                        camera.setRotationAxis(new Point3D(0, 1, 0));
                        camera.setRotate(camera.getRotate() - 1);
                    }

                }
        );


        scene.setCamera(camera);
        primaryStage.setTitle("SiGe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
