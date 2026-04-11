package org.example.businessLogic.bdToEntityMappers;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import org.example.businessLogic.details.irreplaceable.bodyType.SedanBody;
import org.example.businessLogic.details.irreplaceable.bodyType.UniversalBody;
import org.example.businessLogic.details.irreplaceable.driveType.BackDrive;
import org.example.businessLogic.details.irreplaceable.driveType.FrontDrive;
import org.example.businessLogic.details.irreplaceable.driveType.FullDrive;
import org.example.businessLogic.details.irreplaceable.fuelType.DieselFuel;
import org.example.businessLogic.details.irreplaceable.fuelType.ElectricFuel;
import org.example.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import org.example.businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import org.example.businessLogic.details.irreplaceable.gearBoxType.MechanicGearBox;
import org.example.businessLogic.details.replaceable.*;

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
