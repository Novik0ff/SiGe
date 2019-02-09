package Struct;

public class Distribution {
    /**
     * Перечисление типов атомов
     */
    public enum distribution {
        Random, Segregation
    }

    /**
     * Функция для определения типа распеределения по индексу в перечислении (distribution)
     *
     * @param index - индекс типа распределения в перечислении
     * @return тип распределения в структуре
     */
    static String getTypeAtom(int index) {
        for (distribution dist : distribution.values()) {
            if (dist.ordinal() == index) {
                return dist.name();
            }
        }
        return "Тип атома не определен";
    }
}

