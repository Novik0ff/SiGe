package Struct;

public final class AtomType {
    /**
     * Перечисление типов атомов
     */
    public enum atomType {
        Ge, Si
    }

    /**
     * Функция для определения типа атома по индексу в перечислении (atomType)
     *
     * @param index - индекс типа атома в перечислении
     * @return Тип атома
     */
    static String getTypeAtom(int index) {
        for (atomType atom : atomType.values()) {
            if (atom.ordinal() == index) {
                return atom.name();
            }
        }
        return "Тип атома не определен";
    }
}
