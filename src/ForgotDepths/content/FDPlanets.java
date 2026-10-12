package ForgotDepths.content;

import ForgotDepths.classes.planet.PlanetGenerator;
import ForgotDepths.graphics.FDPal;
import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;


public class FDPlanets {
    public static Planet end
            ;
    public static void load(){
        end = new Planet("end", Planets.sun, 1f, 3){{
            generator = new PlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(FDPal.infectedColor).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.43f),
                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(FDPal.infectedColor, 0.7f).a(0.75f), 2, 0.45f, 1f, 0.58f)
            );
            orbitRadius = 300f;
            drawOrbit = false;
            allowLaunchToNumbered = false;
            sectorSeed = 3;
            allowWaves = true;
            clearSectorOnLose = true;
            defaultCore = FDBlocks.scrapCore;
            updateLighting = false;
            ruleSetter = r -> {
                r.defaultTeam = Team.blue;
                r.placeRangeCheck = false;
                r.hideSpawns = false;
                r.lighting = true;
                r.ambientLight = Color.valueOf("02020a57");
            };
            iconColor = FDPal.infectedColor;
            atmosphereColor = FDPal.infectedColor;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 23;
            alwaysUnlocked = true;
        }};
    }
}
