package ForgotDepths.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.game.Objectives.*;

import static ForgotDepths.content.FDBlocks.*;
import static ForgotDepths.content.FDItems.*;
import static ForgotDepths.content.FDPlanets.end;
import static ForgotDepths.content.FDSectors.*;
import static ForgotDepths.content.FDUnits.*;
import static mindustry.content.SectorPresets.origin;
import static mindustry.content.TechTree.*;

public class FDTechTree {

    public static void load() {
        end.techTree = nodeRoot("<???>", scrapCore, () -> {

            nodeProduce(oldScrap, () -> {
                nodeProduce(scrapPlate, () -> {

                });
            });
            node(scrapShifter, Seq.with(new OnSector(resurrection)), () -> {
                node(scrapRouter, () -> {
                    node(scrapOverpass, () -> {

                    });
                });
            });
            node(scrapper, Seq.with(new OnSector(resurrection)), () -> {

            });
            node(pressurizer, Seq.with(new OnSector(debris)), () -> {

            });
            node(trueScrapWall, () -> {

            });
            node(turret, Seq.with(new OnSector(resurrection)), () -> {
                node(ranger, () -> {

                });
            });
            node(resurrection, () -> {
                node(debris, Seq.with(new SectorComplete(resurrection)), () -> {
                    node(silentField, Seq.with(new SectorComplete(debris)), () -> {
                        node(dreadousTrench, Seq.with(new SectorComplete(silentField)), () -> {

                        });
                    });
                });
            });
            node(abandoned, () -> {
                node(armedBox, Seq.with(new SectorComplete(resurrection)), () -> {
                    node(springling, Seq.with(new SectorComplete(resurrection)), () -> {
                        node(roller, Seq.with(new SectorComplete(debris)), () -> {
                            node(dreadnought, Seq.with(new SectorComplete(dreadousTrench)), () -> {

                            });
                        });
                    });
                });
            });
        });
    }
}
