package ForgotDepths.content;

import arc.graphics.Color;
import mindustry.graphics.Pal;
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
            outlineColor = Pal.darkestestGray;
        }};
        debris = new SectorPreset("debris",end,222){{
            difficulty = 4;
            captureWave = 18;
            outlineColor = Pal.darkestestGray;
        }};
        silentField = new SectorPreset("silent-field",end,86){{
            difficulty = 5;
            captureWave = 55;
            outlineColor = Pal.darkestestGray;
        }};
        dreadousTrench = new SectorPreset("dreadous-trench",end,214){{
            difficulty = 6;
            captureWave = 25;
            outlineColor = Pal.darkestestGray;
        }};
    }
}
