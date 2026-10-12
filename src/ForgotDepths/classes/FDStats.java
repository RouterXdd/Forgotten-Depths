package ForgotDepths.classes;

import arc.*;
import arc.struct.*;
import mindustry.world.meta.*;

import java.util.*;

public class FDStats implements Comparable<Stat> {
    public static final Seq<FDStats> allStat = new Seq<>();

    //public static final Stat


    ;

    public final StatCat category;
    public final String name;
    public final int id;

    public FDStats(String name, StatCat category){
        this.category = category;
        this.name = name;
        id = allStat.size;
        allStat.add(this);
    }

    public FDStats(String name){
        this(name, StatCat.general);
    }

    public String localized(){
        return Core.bundle.get("stat." + name.toLowerCase(Locale.ROOT));
    }

    @Override
    public int compareTo(Stat o){
        return id - o.id;
    }
}
