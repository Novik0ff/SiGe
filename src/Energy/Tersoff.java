package Energy;

import Struct.Struct;

import static Struct.BondType.getBondType;
import static Energy.Constants.*;

public final class Tersoff {
    public double A, B;
    public double LM, MU;
    public double BETTA;
    public double N, C, D, H;
    public double R, S;
    public double HI;
    public double W;

    /**
     * Конструктор для зануления всех полей при создании экземпляра класса
     */
    public Tersoff() {
        this.A = 0;
        this.B = 0;
        this.LM = 0;
        this.MU = 0;
        this.BETTA = 0;
        this.N = 0;
        this.C = 0;
        this.D = 0;
        this.H = 0;
        this.R = 0;
        this.S = 0;
        this.HI = 0;
        this.W = 0;
    }

    /**
     * Функиця для расчета межатомной потенциальной энергии
     *
     * @param struct структура
     * @param index  индекс атома
     * @return межатомная потенциальная энергия для конкретного атома
     */
    public static double atomEnergy(Struct struct, int index) {
        int btij, btik;//типы связей
        int J, K;//индексы атомов соседей
        double rij, rik, rjk;//расстояния
        double fcij, fcik;//FC(r)
        double frij, faij;//Fr(r),Fa(r)
        double dztij, cosQ;//Дзета, косинус угла между rij и rik
        double g, bij;
        double atomTotalEnergy = 0;
        for (int i = 0; i < struct.getAtom(index).getCountSecondNeighbourhood(); i++) {
            J = struct.getAtom(index).getSecondNeighbourhood(i);
            //тип связи между атом и атомом соседом - J
            btij = getBondType(struct.getAtom(index), struct.getAtom(J));
            //получаем расстояние между атомами J и Index
            rij = struct.getAtom(J).getDistance(struct.getAtom(index));
            //вычисляем FC(r)
            fcij = getFC(rij, btij);
            if (Math.abs(fcij) > 0) {
                //вычисляем Fr(r)
                frij = tersoffs[btij].A * Math.exp(-tersoffs[btij].LM * rij);
                //вычисляем Fa(r)
                faij = -tersoffs[btij].B * Math.exp(-tersoffs[btij].MU * rij);
                dztij = 0;
                for (int j = 0; j < struct.getAtom(index).getCountSecondNeighbourhood(); j++) {
                    K = struct.getAtom(index).getSecondNeighbourhood(j);
                    //условия что атомы соседи разные
                    if (K != J) {
                        //тип связи между атом Index и атомом соседом - K
                        btik = getBondType(struct.getAtom(index), struct.getAtom(K));
                        //расстояние между Index и атомов соседом K
                        rik = struct.getAtom(index).getDistance(struct.getAtom(K));
                        //вычисляем FC(r)
                        fcik = getFC(rik, btik);
                        if (Math.abs(fcik) > 0) {
                            // расстояние между атомами J, K
                            rjk = struct.getAtom(J).getDistance(struct.getAtom(K));
                            //вычисляем косинус угла между rij и rik
                            cosQ = (rij * rij + rik * rik - rjk * rjk) / (2.0 * rij * rik);
                            // вычисление знаменателя в выражения для расчета g
                            double denominatorG = Math.pow(tersoffs[struct.getAtom(index).getAtomType()].H - cosQ, 2) + Math.pow(tersoffs[struct.getAtom(index).getAtomType()].D, 2);
                            // вычисление G
                            g = 1.0 + Math.pow(tersoffs[struct.getAtom(index).getAtomType()].C, 2) * ((1.0 / Math.pow(tersoffs[struct.getAtom(index).getAtomType()].D, 2) - 1.0 / denominatorG));
                            dztij += tersoffs[btik].W * fcik * g;
                        }
                    }
                }
                bij = Math.pow((1 + Math.pow(tersoffs[struct.getAtom(index).getAtomType()].BETTA * dztij, tersoffs[struct.getAtom(index).getAtomType()].N)), -0.5 / tersoffs[struct.getAtom(index).getAtomType()].N);
                atomTotalEnergy += fcij * (frij + bij * faij);
            }
        }
        return atomTotalEnergy;
    }

    /**
     * Функция для расчета полной энергии потенциала Терсоффа
     *
     * @param struct структура кристалла
     * @return энергия
     */
    public static double totalTersoffEnergy(Struct struct) {
        // переменная для подсчета всей энергии
        double totalEnergy = 0;
        //проходим все атомы в структруре
        for (int i = 0; i < struct.getStructCountAtoms(); i++) {
            totalEnergy += 0.5 * atomEnergy(struct, i);
        }
        return totalEnergy;
    }

    /**
     * Функция для расчета FC(r)
     * @param R расстояние между атомами
     * @param BT тип связи
     * @return значение FC
     */

    private static double getFC(double R, int BT) {
        if (R <= tersoffs[BT].R) {
            return 1.0;
        } else if (R > tersoffs[BT].R && R < tersoffs[BT].S) {
            return 0.5 + Math.cos(Math.PI * (R - tersoffs[BT].R) / (tersoffs[BT].S - tersoffs[BT].R));
        } else if (R >= tersoffs[BT].S) {
            return 0.0;
        }
        return 0.0;
    }
}