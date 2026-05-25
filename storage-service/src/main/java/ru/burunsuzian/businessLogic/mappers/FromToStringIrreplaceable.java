package ru.burunsuzian.businessLogic.mappers;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.SedanBody;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.UniversalBody;
import ru.burunsuzian.businessLogic.details.irreplaceable.driveType.BackDrive;
import ru.burunsuzian.businessLogic.details.irreplaceable.driveType.FrontDrive;
import ru.burunsuzian.businessLogic.details.irreplaceable.driveType.FullDrive;
import ru.burunsuzian.businessLogic.details.irreplaceable.fuelType.DieselFuel;
import ru.burunsuzian.businessLogic.details.irreplaceable.fuelType.ElectricFuel;
import ru.burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.burunsuzian.businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import ru.burunsuzian.businessLogic.details.irreplaceable.gearBoxType.MechanicGearBox;

import java.util.HashMap;
import java.util.Map;

public class FromToStringIrreplaceable {
    private static Map<String, Irreplaceable> stringToIrreplaceableMap = new HashMap<>() {
        {
            put("CoupeBody", new CoupeBody());
            put("SedanBody", new SedanBody());
            put("UniversalBody", new UniversalBody());
            put("FrontDrive", new FrontDrive());
            put("BackDrive", new BackDrive());
            put("FullDrive", new FullDrive());
            put("DieselFuel", new DieselFuel());
            put("ElectricFuel", new ElectricFuel());
            put("PetrolFuel", new PetrolFuel());
            put("AutomaticGearBox", new AutomaticGearBox());
            put("MechanicGearBox", new MechanicGearBox());
        }
    };
    public static Irreplaceable convert(String detail) {
        if (detail == null) {
            throw new IllegalArgumentException("detail string cannot be null");
        }

        Irreplaceable getDetail = stringToIrreplaceableMap.get(detail);
        if (getDetail == null) {
            throw new IllegalArgumentException("Invalid detail string: " + getDetail);
        }
        return getDetail;
    }

}
