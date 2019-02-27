package Main;

import Permutation.Permutation;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import Struct.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static Struct.Constants.*;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        final int width = 800;
        final int height = 600;
        Struct struct = new Struct();
        GridPane gridMain = new GridPane();
        Group groupAtoms = new Group();

        //Задаем параметры камеры
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(5000.0);
        camera.setTranslateZ(-3200);

        //параеметры снапшота (для созранения изображения сцены)
        SnapshotParameters sp = new SnapshotParameters();
        sp.setCamera(camera);
        sp.setFill(Color.BLACK);

        //задаем нашу сцену отрисовки
        Scene scene = new Scene(gridMain, width, height, true);
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);

        //задаем концентрацию для Ge
        double concentrationGe = 0.1;
        int countCells = 10;

        //Параметр решетки вычисляется по закону Вегарда
        double paramCell = paramCellSI * (1 - concentrationGe) + paramCellGE * concentrationGe;
        if (struct.сreateCubicStructure(countCells, paramCell)) {
            System.out.println("Структура успешно создана");
            if (struct.setAtomsTypes(concentrationGe, Distribution.distribution.Segregation.ordinal())) {
                System.out.println("Типы атомов в структуре заданы");
                if (struct.setAtomsNeighbourhood(paramCell / 2)) {
                    System.out.println("Атомы - соседи заданы");
                    if (struct.setAtomsSecondNeighbourhood(2 * paramCell * Math.sqrt(3.0) / 4.0)) {
                        System.out.println("Вторые соседи атомов заданы");
                    }
                }
            }
        }


        gridMain.add(groupAtoms, 0, 0);
        struct.calculateCountGeSi();
        System.out.println(struct.getStructInfo());

        System.out.println("\n\rЭнергия структуры = " + Energy.Tersoff.totalTersoffEnergy(struct));

        drawScene(struct, groupAtoms, camera);

        saveAsPng(groupAtoms, "./src/Images/До перестановок", sp);

        System.out.println(Permutation.severalPermutation(100000, struct));

        drawScene(struct, groupAtoms, camera);

        saveAsPng(groupAtoms, "./src/Images/После перестановок", sp);

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
                    if (keycode == KeyCode.NUMPAD5) {
                        System.out.println(Permutation.severalPermutation(1000, struct));
                        drawScene(struct, groupAtoms, camera);
                    }
                }
        );


        primaryStage.setTitle("SiGe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Функция для сохранения изображения
     *
     * @param scene - сцена
     * @param name  - имя файла
     * @param ssp   - параметры снапшота
     */
    public void saveAsPng(Node scene, String name, SnapshotParameters ssp) {
        WritableImage image = scene.snapshot(ssp, null);
        File file = new File(name + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {

        }
    }

    /**
     * Функция для добавления группы атомов на сцену
     *
     * @param struct     - структура
     * @param groupAtoms - группа атомов (сферы)
     * @param camera     - камера
     */
    public static void drawScene(Struct struct, Group groupAtoms, Camera camera) {
        groupAtoms.getChildren().clear();
        groupAtoms.getChildren().add(camera);
        for (int i = 0; i < struct.getStructCountAtoms(); i++) {
            Sphere tempSphere = new Sphere();
            //задаем координаты сцен + нормализуем по центру
            tempSphere.setTranslateX((struct.getAtom(i).getAtomX() - struct.getMaxX() / 2) * 200);
            tempSphere.setTranslateY((struct.getAtom(i).getAtomY() - struct.getMaxY() / 2) * 200);
            tempSphere.setTranslateZ((struct.getAtom(i).getAtomZ() - struct.getMaxZ() / 2) * 200);
            //для атомов Ge
            if (struct.getAtom(i).getAtomType() == AtomType.atomType.Ge.ordinal()) {
                tempSphere.setRadius(8);
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.RED);
                material.setSpecularColor(Color.ORANGE);
                tempSphere.setMaterial(material);
            } else {
                //для атомов Si
                tempSphere.setRadius(7);
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.BLUE);
                material.setSpecularColor(Color.PURPLE);
                tempSphere.setMaterial(material);
            }
            groupAtoms.getChildren().add(tempSphere);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
