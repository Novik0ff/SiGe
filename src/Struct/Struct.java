package Struct;

import java.util.*;

public class Struct {
    private ArrayList<Atom> atomsCollection; // коллекция атомов в структуре
    private double paramCell; // параметр решетки
    private int distribution; // тип распределения атомов в решетке
    private int countGe; // количество атомов Ge
    private int countSi; // количество атомов Si
    private double maxX, maxY, maxZ; //максимальные координаты X,Y,Z
    private int countGroup; //количество атомов в группе в явлении сегрегации

    /**
     * Функция для создания структуры кристалла в форме куба
     *
     * @param countCells - длина стороны куба в размерах решетки
     * @param paramCell  - параметр решетки
     * @return true если структура успешно создалась
     */
    public boolean сreateCubicStructure(int countCells, double paramCell) {
        try {
            this.paramCell = paramCell;// задаем параметр решетки
            this.atomsCollection = new ArrayList<Atom>();
            //определяем максимальные координаты
            maxX = 0.75 * paramCell + (countCells - 1) * paramCell;
            maxY = 0.75 * paramCell + (countCells - 1) * paramCell;
            maxZ = 0.75 * paramCell + (countCells - 1) * paramCell;

            for (int i = 0; i < countCells; i++) {
                for (int j = 0; j < countCells; j++) {
                    for (int k = 0; k < countCells; k++) {
                        if (i == 0 && j == 0 && k == 0) {
                            //Основная ГЦК - решетка
                            this.atomsCollection.add(new Atom(0.0, 0.0, 0.0));
                            this.atomsCollection.add(new Atom(0.0 * paramCell, 0.5 * paramCell, 0.5 * paramCell));
                            this.atomsCollection.add(new Atom(0.5 * paramCell, 0.0 * paramCell, 0.5 * paramCell));
                            this.atomsCollection.add(new Atom(0.5 * paramCell, 0.5 * paramCell, 0.0 * paramCell));
                            //Сдвинутая ГЦК - решетка
                            this.atomsCollection.add(new Atom(0.25 * paramCell, 0.25 * paramCell, 0.25 * paramCell));
                            this.atomsCollection.add(new Atom(0.25 * paramCell, 0.75 * paramCell, 0.75 * paramCell));
                            this.atomsCollection.add(new Atom(0.75 * paramCell, 0.25 * paramCell, 0.75 * paramCell));
                            this.atomsCollection.add(new Atom(0.75 * paramCell, 0.75 * paramCell, 0.25 * paramCell));

                        } else {
                            for (int l = 0; l < 8; l++) {
                                this.atomsCollection.add(new Atom(this.atomsCollection.get(l).getAtomX() + paramCell * i,
                                        this.atomsCollection.get(l).getAtomY() + paramCell * j,
                                        this.atomsCollection.get(l).getAtomZ() + paramCell * k));

                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Функция для задания типов атомов в структуре
     *
     * @param concentrationGe концентрация Ge в структуре
     * @param distribution    тип распределения
     * @return true если задания типов атомов в структуре было произведено успешно
     */
    public boolean setAtomsTypes(double concentrationGe, int distribution) {
        try {
            //Все атомы в структуре переопределяем как атомы Si
            for (Atom atom : atomsCollection) {
                atom.setAtomType(AtomType.atomType.Si.ordinal());
            }
            try {
                //если распределение рандомное
                if (distribution == Distribution.distribution.Random.ordinal()) {
                    //рандомное распределение типов атомов в структуре
                    this.distribution = Distribution.distribution.Random.ordinal();
                    if (setRandomDistribution(concentrationGe)) {
                        System.out.println("Успешно заданы типы атомов в структуре");
                        return true;
                    } else {
                        System.out.println("\r\nОшибка при задании рандомного распределения атомов в структуре\r\n");
                        return false;
                    }
                }
                //если явления сегрегации
                if (distribution == Distribution.distribution.Segregation.ordinal()) {
                    //явление сегрегации в структуре
                    this.distribution = Distribution.distribution.Segregation.ordinal();
                    try {
                        this.setAtomsNeighbourhood(paramCell / 2);
                        System.out.println("Задайте количество атомов в группе");
                        this.countGroup = new Scanner(System.in).nextInt();
                        if (setSegregationDistribution(concentrationGe, countGroup)) {
                            System.out.println("Успешно заданы типы атомов в структуре");
                            return true;
                        } else {
                            System.out.println("\r\nОшибка при задании распределения атомов в структуре с явлением сегрегации\r\n");
                            return false;
                        }
                    } catch (Exception ex) {
                        System.out.println("\r\nОшибка при задании распределения атомов в структуре с явлением сегрегации\r\n");
                        return false;
                    }
                }
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Ошибка при задании типов атомов в структуре");
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при задании типов атомов в структуре");
            return false;
        }
    }

    /**
     * Функция задает типы атомов в структуре с явлением сегрегации
     *
     * @param concentrationGe концентрация атомов Ge в структуре
     * @param countGroup      количетсво атомов в группе при моделированиии явления сегрегации
     * @return true если все прошло успешно
     */

    private boolean setSegregationDistribution(double concentrationGe, int countGroup) {
        if (atomsCollection.size() < countGroup) {
            return false;
        } else {
            //задаем все атомы в структуре атомами Si
            for (int i = 0; i < atomsCollection.size(); i++) {
                atomsCollection.get(i).setAtomType(AtomType.atomType.Si.ordinal());
            }
            //расчитываем количество узлов где в структуре будут скопления атомов Ge
            int nodes = (int) (atomsCollection.size() * concentrationGe / countGroup);
            //задаем группы скоплений атомов
            for (int i = 0; i < nodes; i++) {
                Segregation(countGroup);
            }
            //если атомов Ge задали меньшее количество
            if ((int) (atomsCollection.size() * concentrationGe) > nodes * countGroup) {
                Segregation((int) (atomsCollection.size() * concentrationGe) - nodes * countGroup);
            }

            return true;
        }
    }

    /**
     * Функция задает скопления атомов Ge в указанном количестве
     * @param countGroup количество атомов в группе
     */
    private void Segregation(int countGroup) {
        int temp = countGroup;
        Random rnd = new Random();
        while (temp > 0) {
            //если случайный атом - Si
            int rndValue = Math.abs(rnd.nextInt() % atomsCollection.size());
            if (atomsCollection.get(rndValue).getAtomType() == AtomType.atomType.Si.ordinal()) {
                //делаем найденый атом - атомом Ge
                atomsCollection.get(rndValue).setAtomType(AtomType.atomType.Ge.ordinal());
                temp--;
                //создаем коллекцию куда будем записываать атомы группы
                ArrayList<Integer> groupAtoms = new ArrayList<>();
                //добавили наш найденный атом - узел группы
                groupAtoms.add(rndValue);
                while (temp > 0) {
                    //выбираем случайный атом в группе
                    int tempAtom = Math.abs(rnd.nextInt() % groupAtoms.size());
                    //находим его случайного соседа
                    int tempNeighbourhood = Math.abs(rnd.nextInt() % atomsCollection.get(groupAtoms.get(tempAtom)).getCountNeighbourhood());
                    //проверяем если он атом Si, то меняем его на атом Ge
                    if (atomsCollection.get(atomsCollection.get(groupAtoms.get(tempAtom)).getNeighbourhood(tempNeighbourhood)).getAtomType() == AtomType.atomType.Si.ordinal()) {
                        atomsCollection.get(atomsCollection.get(groupAtoms.get(tempAtom)).getNeighbourhood(tempNeighbourhood)).setAtomType(AtomType.atomType.Ge.ordinal());
                        temp--;
                    }
                    boolean check = true;
                    //проверка на уникальность атома
                    for (int i = 0; i < groupAtoms.size(); i++) {
                        if(groupAtoms.get(i) == atomsCollection.get(groupAtoms.get(tempAtom)).getNeighbourhood(tempNeighbourhood)){
                            check = false;
                        }
                    }
                    if(check){
                        //также добавляем его в коллекцию, чтобы по нему тоже велся поиск новых атомов, при условии что такого этома в группе не было
                        groupAtoms.add(atomsCollection.get(groupAtoms.get(tempAtom)).getNeighbourhood(tempNeighbourhood));
                    }
                }
            }
        }
    }

    /**
     * Функция для задания рандомного распределения в структуре кристалла
     *
     * @param concentrationGe концентрация атомов Ge в структуре
     * @return true если задание типов атомов было прошло успешно
     */
    private boolean setRandomDistribution(double concentrationGe) {
        try {
            Random random = new Random(); // переменная для рандома
            int countTemp = (int) (this.atomsCollection.size() * concentrationGe); // вычисляем количество атомов Ge
            this.countGe = countTemp;// задаем количетво атомов Ge в структуре
            this.countSi = atomsCollection.size() - this.countGe;// задаем количетво атомов Si в структуре
            //если нужно еще задать количество атомов Ge больш0
            while (countTemp > 0) {
                //Выбираем случайный атом
                int Temp = Math.abs(random.nextInt()) % atomsCollection.size();
                //проверяем если он не является атомом Ge
                if (atomsCollection.get(Temp).getAtomType() != AtomType.atomType.Ge.ordinal()) {
                    //то делаем его атомом Ge
                    atomsCollection.get(Temp).setAtomType(AtomType.atomType.Ge.ordinal());
                    countTemp--;
                }
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Функция для получения количества атомов в структуре
     *
     * @return количество атомов в структуре
     */
    public int getStructCountAtoms() {
        return this.atomsCollection.size();
    }

    /**
     * Функция для получения атома структуры по номеру
     *
     * @param index номер атома в коллекции
     * @return атом с указанным индексом
     */
    public Atom getAtom(int index) {
        return atomsCollection.get(index);
    }

    /**
     * Функция для поиска соседей атомов
     *
     * @param distance радиус для поиска соседей
     * @return
     */
    public boolean setAtomsNeighbourhood(double distance) {
        try {
            for (int i = 0; i < atomsCollection.size(); i++) {
                if (atomsCollection.get(i).clearNeighbourhood())/*сбрасываем соседей у каждого атома*/ {
                    for (int j = 0; j < atomsCollection.size(); j++) {
                        if (!atomsCollection.get(i).equals(atomsCollection.get(j)) && atomsCollection.get(i).getDistance(atomsCollection.get(j)) < distance)
                            /*если атомы разные и находятся в указанном радиусе*/ {
                            atomsCollection.get(i).setNeighbourhood(j);
                        }
                    }
                }
            }
            System.out.println("Успешно заданы атомы соседи для каждого атома в структуре");
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Сбой при задании атомов соседей для каждого атома в структуре");
            return false;
        }
    }

    /**
     * Функция для поиска вторых соседей атомов
     *
     * @param distance радиус для поиска вторых соседей
     * @return
     */
    public boolean setAtomsSecondNeighbourhood(double distance) {
        try {
            for (int i = 0; i < atomsCollection.size(); i++) {
                if (atomsCollection.get(i).clearSecondNeighbourhood())/*сбрасываем вторых соседей у каждого атома*/ {
                    for (int j = 0; j < atomsCollection.size(); j++) {
                        if (!atomsCollection.get(i).equals(atomsCollection.get(j))
                                && atomsCollection.get(i).getDistance(atomsCollection.get(j)) < distance
                                && atomsCollection.get(i).getCountSecondNeighbourhood() < 50)
                            /*если атомы разные и находятся в указанном радиусе и меньше 50 атомов*/ {
                            atomsCollection.get(i).setSecondNeighbourhood(j);
                        }
                    }
                }
            }
            System.out.println("Успешно заданы вторые соседи для каждого атома в структуре");
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Сбой при задании вторых соседей для каждого атома в структуре");
            return false;
        }
    }

    public String getStructInfo() {
        return "\r\nStruct Info \r\n@paramCell - " + this.paramCell + "\r\n"
                + "@distribution - " + Distribution.getTypeAtom(this.distribution) + "\r\n" +
                "@countGe - " + countGe + "\r\n"
                + "@countSi - " + countSi + "\r\n";
    }

    /**
     * Функция для получения max координаты X
     *
     * @return max X
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Функция для получения max координаты Y
     *
     * @return max Y
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Функция для получения max координаты Z
     *
     * @return max Z
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * Функция возвращает параметр решетки
     *
     * @return парамент решетки
     */
    public double getParamCell() {
        return this.paramCell;
    }

    /**
     * Функция для пересчета количества атомом Ge, Si в структуре
     */
    public void calculateCountGeSi() {
        //сбрасываем прежние значения количества атомов в структуре
        this.countGe = 0;
        this.countSi = 0;
        for (int i = 0; i < atomsCollection.size(); i++) {
            if (atomsCollection.get(i).getAtomType() == AtomType.atomType.Ge.ordinal()) {
                this.countGe++;
            }
            if (atomsCollection.get(i).getAtomType() == AtomType.atomType.Si.ordinal()) {
                this.countSi++;
            }
        }
    }
}
