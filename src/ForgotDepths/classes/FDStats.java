package ForgotDepths.classes;

import arc.*;
import arc.struct.*;
import mindustry.world.meta.*;

import java.util.*;

public class FDStats {
    public static final StatCat
        ammo = new StatCat("ammoSpc");
    public static final Stat
        ammoCap = new Stat("ammoCap", ammo),
        ammoReload = new Stat("ammoReload", ammo)
    ;
}
