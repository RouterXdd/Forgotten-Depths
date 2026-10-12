package ForgotDepths;

import ForgotDepths.content.*;
import ForgotDepths.graphics.FDMenuRenderer;
import arc.Events;
import arc.graphics.Texture;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;
import mindustry.ui.fragments.MenuFragment;

import static arc.Core.bundle;
import static mindustry.Vars.mods;

public class FDMain extends Mod{
    public static final String modName = "fd";

    public FDMain(){
        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            try {
                Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new FDMenuRenderer());
            } catch (Exception var2) {
                Log.err("Failed to replace renderer", var2);
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
