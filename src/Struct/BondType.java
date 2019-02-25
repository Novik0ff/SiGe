package Struct;

public final class BondType {
    enum bondType{GeGe,SiSi,SiGe}
    public final static String getBondType(int index) {
        for (bondType bond : bondType.values()) {
            if (bond.ordinal() == index) {
                return bond.name();
            }
        }
        return "Тип связи не определен";
    }

    /**
     * Функция для получения типа атома
     * @param a первый атом
     * @param b второй атом
     * @return тип свзяди между атомами
     */
    public final static int getBondType(Atom a, Atom b){
        try{
            // если оба атома - Ge
            if(a.getAtomType() == AtomType.atomType.Ge.ordinal() && b.getAtomType() == AtomType.atomType.Ge.ordinal()){
                return bondType.GeGe.ordinal();
            }
            // если оба атома - Si
            if(a.getAtomType() == AtomType.atomType.Si.ordinal() && b.getAtomType() == AtomType.atomType.Si.ordinal()){
                return bondType.SiSi.ordinal();
            }
            // если один атома - Ge, а второй атом - Si
            if(a.getAtomType() == AtomType.atomType.Si.ordinal() && b.getAtomType() == AtomType.atomType.Ge.ordinal() ||
                    a.getAtomType() == AtomType.atomType.Ge.ordinal() && b.getAtomType() == AtomType.atomType.Si.ordinal()){
                return bondType.SiGe.ordinal();
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("Не удалось получить тип связи");
         return 0;
        }
        return 0;
    }
}
