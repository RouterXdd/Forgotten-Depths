package ForgotDepths.content;

import arc.graphics.Color;
import mindustry.type.*;

public class FDItems {
    public static Item oldScrap, scrapPlate, tarnishedLead, powerCrystal, purePlatinum, desolateJade, hollowIce, kineticBronze

            ;
    public static void load(){
        oldScrap = new Item("old-scrap", Color.valueOf("49494d")){{
            hardness = 1;
            cost = 1.2f;
            healthScaling = 0.75f;
        }};
        scrapPlate = new Item("scrap-plate", Color.valueOf("1f1e25")){{
            hardness = 1;
            cost = 1.5f;
            healthScaling = 3f;
        }};
    }
}
