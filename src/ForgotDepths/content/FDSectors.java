package ForgotDepths.content;

import mindustry.type.SectorPreset;

import static ForgotDepths.content.FDPlanets.end;

public class FDSectors {
    public static SectorPreset resurrection, debris, silentField, dreadousTrench
            ;
    public static void load(){
        resurrection = new SectorPreset("resurrection",end,23){{
            difficulty = 3;
            captureWave = 10;
            alwaysUnlocked = true;
        }};
        debris = new SectorPreset("debris",end,222){{
            difficulty = 4;
            captureWave = 18;
        }};
        silentField = new SectorPreset("silent-field",end,86){{
            difficulty = 5;
            captureWave = 55;
        }};
        dreadousTrench = new SectorPreset("dreadous-trench",end,214){{
            difficulty = 6;
            captureWave = 25;
        }};
    }
}
