package main.FIPE.pojo;

import java.util.Objects;

public enum BrandsEnum {
    AUDI(6),
    BMW(7), IMP_BMW(7),
    BYD(238),
    CAOA_CHERRY(161),
    CITROEN(13), IMP_CITROEN(13),
    FIAT(21), IMP_FIAT(21),
    FORD(22), IMP_FORD(22),
    GM(23),IMP_GM(23),
    CHEVROLET(23), IMP_CHEVROLET(23), IMP_CHEV(23), CHEV(23),
    HONDA(25), IMP_HONDA(25),
    HYUNDAI(26),
    JAC(177),
    JEEP(29),
    KIA(31), IMP_KIA(31),
    LR(33),IMP_LR(33),
    M_BENZ(39), IMP_M_BENZ(39),
    MITSUBISHI(41),
    NISSAN(43),IMP_NISSAN(43),
    PEUGEOT(44), IMP_PEUGEOT(44),
    RENAULT(48), IMP_RENAULT(48),
    SHINERAY(183),
    TOYOTA(56),
    TROLLER(57),
    VOLVO(58),
    VW(59), IMP_VW(59),

    UNDEFINED(0)
    ;

    private final Integer id;

    BrandsEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public static BrandsEnum fromString(String enumName) {
        if (Objects.nonNull(enumName) && !enumName.isBlank()) {
            if (enumName.startsWith("IMP ") || enumName.contains(" ")){
                enumName = enumName.replace(" ","_");
            }
            try {
                return BrandsEnum.valueOf(enumName);
            } catch (IllegalArgumentException ex) {
                for (int i = 0; i < BrandsEnum.values().length; i++) {
                    BrandsEnum current = BrandsEnum.values()[i];
                    if (current.name().contains(enumName)) {
                         return current;
                     }
                }
            }
        }
        return UNDEFINED;
    }

}
