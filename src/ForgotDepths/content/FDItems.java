package ForgotDepths.content;

import arc.graphics.Color;
import mindustry.type.*;

public class FDItems {
    public static Item oldScrap, scrapPlate, tarnishedLead, powerCrystal, overdriveCell, purePlatinum, desolateJade, hollowIce, kineticBronze

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
        tarnishedLead = new Item("tarnished-lead", Color.valueOf("1d1c4c")){{
            hardness = 2;
            cost = 1.55f;
            healthScaling = 0.9f;
        }};
        powerCrystal = new Item("power-crystal", Color.valueOf("60e7ff")){{
            hardness = 1;
            cost = 1.35f;
            healthScaling = 0.5f;
            charge = 0.5f;
        }};
        overdriveCell = new Item("overdrive-cell", Color.valueOf("292a69")){{
            hardness = 1;
            cost = 1.8f;
            healthScaling = 0.9f;
            charge = 1.25f;
        }};
        purePlatinum = new Item("pure-platinum", Color.valueOf("93bfd7")){{
            hardness = 3;
            cost = 1.85f;
            healthScaling = 1.2f;
        }};
    }
}
