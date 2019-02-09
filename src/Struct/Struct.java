package Struct;

import java.util.ArrayList;
import java.util.Random;

public class Struct {
    private ArrayList<Atom> atomsCollection;

    /**
     * Функция для создания структуры кристалла в форме куба
     *
     * @param countCells - длина стороны куба в размерах решетки
     * @param paramCell  - параметр решетки
     * @return true если структура успешно создалась
     */
    public boolean сreateCubicStructure(int countCells, double paramCell) {
        try {
            this.atomsCollection = new ArrayList<Atom>();
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
                    if (setRandomDistribution(concentrationGe)) {
                        return true;
                    } else {
                        System.out.println("\r\nОшибка при задании рандомного распределения атомов в структуре\r\n");
                        return false;
                    }
                }
                //если явления сегрегации
                if (distribution == Distribution.distribution.Segregation.ordinal()) {

                }
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Ошибка при ");
            return false;
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
            //если нужно еще задать количество атомов Ge больш0
            while (countTemp > 0) {
                int Temp = Math.abs(random.nextInt()) % atomsCollection.size();
                if (atomsCollection.get(Temp).getAtomType() != AtomType.atomType.Ge.ordinal()) {
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
}
