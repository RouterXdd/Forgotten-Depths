package ForgotDepths.classes.entities.units;

import arc.graphics.Color;
import mindustry.type.UnitType;

public class ScrapUnitType extends UnitType {
    public ScrapUnitType(String name) {
        super(name);
        outlineColor = mechLegColor = Color.valueOf("171719");
        researchCostMultiplier = 0;
    }
}
