package ForgotDepths;

import ForgotDepths.classes.ui.DemoCompletionDialog;
import ForgotDepths.content.*;
import ForgotDepths.graphics.FDMenuRenderer;
import ForgotDepths.graphics.FDPal;
import arc.Events;
import arc.graphics.Texture;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.mod.*;
import mindustry.ui.fragments.MenuFragment;

import java.util.concurrent.atomic.AtomicBoolean;

import static ForgotDepths.content.FDPlanets.end;
import static arc.Core.bundle;
import static mindustry.Vars.*;

public class FDMain extends Mod{
    public static final String modName = "fd";
    public DemoCompletionDialog demoDialog;

    public FDMain(){
        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            demoDialog = new DemoCompletionDialog();
            try {
                Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new FDMenuRenderer());
            } catch (Exception var2) {
                Log.err("Failed to replace renderer", var2);
            }
        });
        Events.on(EventType.SectorCaptureEvent.class, e -> {
            if(e.sector.preset == null || e.sector.planet != end) return;
            if(!net.client() && e.sector.preset != null && e.sector.preset == FDSectors.dreadousTrench && e.initialCapture){
                Time.run(60f * 2f, () -> {
                    if (FDVars.demo) demoDialog.show(end);
                });
            }
        });
    }
    @Override
    public void init() {
        Mods.LoadedMod mod = mods.locateMod(modName);
        /*
        Part of april fools release
        mod.meta.displayName = "Forgotten Depths";
        mod.meta.description = mod.meta.subtitle = "<???>";
        mod.iconTexture = new Texture(mod.root.child("icon-true.png"));
        */
        if (FDVars.demo){
            mod.meta.displayName = "Forgotten Depths (DEMO)";
        }
        mod.meta.description = bundle.get("desc");
        mod.meta.subtitle = bundle.get("subtitle");
    }

    @Override
    public void loadContent(){
        FDStatuses.load();
        FDWeather.load();
        FDItems.load();
        FDUnits.load();
        FDBlocks.load();
        FDPlanets.load();
        FDSectors.load();
        FDTechTree.load();
    }

}
