package Struct;

/**
 * @author Novikov 2019
 * Класс для описания объекта - атом
 */
public class Atom {
    //координаты x,y,z
    private double x, y, z;
    //тип атома
    private int type;

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
}
