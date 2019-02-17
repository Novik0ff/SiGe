package Struct;

import java.util.ArrayList;

/**
 * @author Novikov 2019
 * Класс для описания объекта - атом
 */
public class Atom {
    //координаты x,y,z
    private double x, y, z;
    //тип атома
    private int type;
    //ближайшие соседи
    private ArrayList<Integer> neighbourhood = new ArrayList<>();
    //вторые соседи
    private ArrayList<Integer> secondNeighbourhood = new ArrayList<>();


    /**
     * конструктор без параметров
     */
    public Atom() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.type = 0;
    }

    /**
     * конструктор с начальными координатами
     */
    public Atom(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = 0;
    }

    /**
     * конструктор с начальными координатами и типом атома
     */
    public Atom(double x, double y, double z, int type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    /**
     * Функция устанавливает значение координаты амтома X
     *
     * @param x - новое значение координаты
     */
    public void setAtomX(double x) {
        this.x = x;
    }

    /**
     * Функция возвращает значение координаты амтома X
     *
     * @return x - значение коодинаты X
     */
    public double getAtomX() {
        return this.x;
    }

    /**
     * Функция устанавливает значение координаты амтома Y
     *
     * @param y - новое значение координаты
     */
    public void setAtomY(double y) {
        this.y = y;
    }

    /**
     * Функция возвращает значение координаты амтома Y
     *
     * @return y - значение коодинаты Y
     */
    public double getAtomY() {
        return this.y;
    }

    /**
     * Функция устанавливает значение координаты амтома Z
     *
     * @param z - новое значение координаты
     */
    public void setAtomZ(double z) {
        this.z = z;
    }

    /**
     * Функция возвращает значение координаты амтома Z
     *
     * @return z - значение коодинаты Z
     */
    public double getAtomZ() {
        return this.z;
    }

    /**
     * Функция устанавливает значение типа амтома type
     *
     * @param type - новое типа атома
     */
    public void setAtomType(int type) {
        this.type = type;
    }

    /**
     * Функция возвращает значение типа амтома type
     *
     * @return type - значение типа атома type
     */
    public double getAtomType() {
        return this.type;
    }

    /**
     * Функция для вывода значениЙ атома
     */
    public String getAtomInfo() {
        return "@param x - " + this.x + "\r\n" +
                "@param y - " + this.y + "\r\n" +
                "@param z - " + this.z + "\r\n" +
                "@param type - " + AtomType.getTypeAtom(this.type) + "\r\n";
    }

    /**
     * Функция для расчета расстояния между атомами
     *
     * @param a - второй атом для расчета расстояния
     */
    public double getDistance(Atom a) {
        return Math.sqrt((this.x - a.x) * (this.x - a.x) +
                (this.y - a.y) * (this.y - a.y) +
                (this.z - a.z) * (this.z - a.z));
    }

    /**
     * Функция для очистки номеров соседей- атомов
     *
     * @return true при успешной очистке
     */
    public boolean clearNeighbourhood() {
        try {
            this.neighbourhood.clear();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при сбросе атомов соседей");
            return false;
        }
    }

    /**
     * Функция для очистки номеров вторых соседей атомов
     *
     * @return true при успешной очистке
     */
    public boolean clearSecondNeighbourhood() {
        try {
            this.secondNeighbourhood.clear();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при сбросе атомов соседей");
            return false;
        }
    }

    /**
     * Функция для добавления атома- соседа
     *
     * @param index номер атома в структуре
     * @return true в случае успешного добавления атома
     */
    public boolean setNeighbourhood(int index) {
        return setNeighbourhoods(neighbourhood, index);
    }

    /**
     * Функция для получения количества атомов соседей
     *
     * @return количество атомов соседей
     */
    public int getCountneighbourhood() {
        return neighbourhood.size();
    }

    /**
     * Функция для получения количества вторых соседей
     *
     * @return количество вторых соседей
     */
    public int getCountSecondNeighbourhood() {
        return secondNeighbourhood.size();
    }

    /**
     * Функция для получения атома - соседа по индексу от 0-3 (атомы Si, Ge)
     *
     * @param index номер соседа атома от 0 до 3
     * @return номер атома в структуре
     */
    public int getNeighbourhood(int index) {
        return neighbourhood.get(index);
    }

    /**
     * Функция для получения вторый соседей атома по индексу
     *
     * @param index номер соседа атома
     * @return номер атома в структуре
     */
    public int getSecondNeighbourhood(int index) {
        return secondNeighbourhood.get(index);
    }

    /**
     * Функция для стравнения атомов
     *
     * @param a атом для сравнения
     * @return true в случае полного соответствия атомов
     */
    public boolean equals(Atom a) {
        try {
            if (this.getAtomType() == a.getAtomType() &&
                    this.getAtomX() == a.getAtomX() &&
                    this.getAtomY() == a.getAtomY() &&
                    this.getAtomZ() == a.getAtomZ()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при сравнении атомов");
            return false;
        }
    }

    /**
     * Функция для добавдения вторых атомов соседей
     *
     * @param index номер атома в структуре
     * @return true в случае успешного добавления атома
     */
    public boolean setSecondNeighbourhood(int index) {
        return setNeighbourhoods(secondNeighbourhood, index);
    }

    /**
     * Функиця для добавления атома в коллекцию
     *
     * @param neighbourhoods имя коллекции в которую нужно добавить атом
     * @param index          номер атома на добавление
     * @return
     */
    private boolean setNeighbourhoods(ArrayList<Integer> neighbourhoods, int index) {
        try {
            neighbourhoods.add(index);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при задании атома соседа");
            return false;
        }
    }

}
