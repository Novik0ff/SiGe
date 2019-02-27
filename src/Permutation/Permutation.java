package Permutation;

import Struct.*;

import java.util.Random;

public final class Permutation {
    /**
     * Функция делает попытку перестановки
     *
     * @param a      номер первого атома
     * @param b      номер второго атома
     * @param struct структура
     * @return true если перестановка энергетически выгодна, false возвращает предыдущие значения типов атомов
     */
    private static boolean permutation(int a, int b, Struct struct) {
        if (struct.getAtom(a).getAtomType() == struct.getAtom(b).getAtomType()) {
            return false;
        } else {

            //делаем расчет энергии атомов a,b и соседей каждого до перестановки
            double beforeEnergy = Energy.Tersoff.atomEnergy(struct, a) + Energy.Tersoff.atomEnergy(struct, b);

            for (int i = 0; i < struct.getAtom(a).getCountSecondNeighbourhood(); i++) {
                beforeEnergy += Energy.Tersoff.atomEnergy(struct, struct.getAtom(a).getSecondNeighbourhood(i));
            }
            for (int i = 0; i < struct.getAtom(b).getCountSecondNeighbourhood(); i++) {
                beforeEnergy += Energy.Tersoff.atomEnergy(struct, struct.getAtom(b).getSecondNeighbourhood(i));
            }

            int temp = struct.getAtom(a).getAtomType();

            //делаем перестановку
            struct.getAtom(a).setAtomType(struct.getAtom(b).getAtomType());
            struct.getAtom(b).setAtomType(temp);

            //делаем расчет энергии атомов a,b и соседей каждого после перестановки
            double afterEnergy = Energy.Tersoff.atomEnergy(struct, a) + Energy.Tersoff.atomEnergy(struct, b);

            for (int i = 0; i < struct.getAtom(a).getCountSecondNeighbourhood(); i++) {
                afterEnergy += Energy.Tersoff.atomEnergy(struct, struct.getAtom(a).getSecondNeighbourhood(i));
            }
            for (int i = 0; i < struct.getAtom(b).getCountSecondNeighbourhood(); i++) {
                afterEnergy += Energy.Tersoff.atomEnergy(struct, struct.getAtom(b).getSecondNeighbourhood(i));
            }

            if (beforeEnergy >= afterEnergy) {
                return true;
            } else {
                struct.getAtom(b).setAtomType(struct.getAtom(a).getAtomType());
                struct.getAtom(a).setAtomType(temp);
                return false;
            }
        }
    }

    /**
     * Функция совершает перестановки атомов в структуре
     *
     * @param count  - количество перестановок
     * @param struct - структура
     * @return строку (разнницу энергий до перестановок и после; количество перестановок; количество успешных перестановок)
     */
    public static String severalPermutation(int count, Struct struct) {
        //количество успешных перестановок
        int countSuccess = 0;
        //считаем энергию до перестановок
        double beforeEnergy = Energy.Tersoff.totalTersoffEnergy(struct);
        //переменная для работы со случайными числами
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            boolean check = true;
            while (check) {
                //выбираем случайный атом в структуре
                int a = Math.abs(rnd.nextInt() % struct.getStructCountAtoms());
                //выбираем случайного атома - соседа
                int b = struct.getAtom(a).getNeighbourhood(Math.abs(rnd.nextInt() % struct.getAtom(a).getCountNeighbourhood()));
                if (struct.getAtom(a).getAtomType() != struct.getAtom(b).getAtomType()) {
                    if (permutation(a, b, struct)) {
                        countSuccess++;
                    }
                    check = false;
                }
            }
        }
        //считаем энергию после перестановок
        double afterEnergy = Energy.Tersoff.totalTersoffEnergy(struct);
        //возвращем разницу энергий после перестановок


        return "Разница энергий до перестановок и после  " + (afterEnergy - beforeEnergy) +
                "\r\nКоличество перестановок  " + count +
                "\r\nКоличество успешных перестановок  " + countSuccess;
    }
}
