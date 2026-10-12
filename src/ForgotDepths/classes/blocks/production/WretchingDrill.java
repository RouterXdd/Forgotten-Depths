package ForgotDepths.classes.blocks.production;

import ForgotDepths.classes.campain.DisabledUnit;
import arc.math.Mathf;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Drill;

public class WretchingDrill extends Drill {
    public float wretchedTimeMin, wretchedTimeMax;
    public float wretchedRechargeMin, wretchedRechargeMax;
    public float wretchPenalty;

    public Effect wretchEffect = Fx.none;

    public float effectChance = 0.05f;
    public WretchingDrill(String name) {
        super(name);
        update = true;
        hasLiquids = false;
        wretchPenalty = 0.35f;
        liquidBoostIntensity = 1f;
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("activation", (WretchingDrillBuild e) -> new Bar("bar.progress", Pal.accent, e::time));
    }
    public class WretchingDrillBuild extends DrillBuild {
        float time, wretch = 1;
        boolean wretched = true;
        @Override
        public void updateTile(){
            time += delta();
            if (time >= wretch){
                wretched = !wretched;
                time = 0;
                if (wretched){
                    wretch = Mathf.random(wretchedRechargeMin,wretchedRechargeMax);
                } else {
                    wretch = Mathf.random(wretchedTimeMin,wretchedTimeMax);
                }
            }
            if (wretched){
                efficiency *= wretchPenalty;
                if(Mathf.chanceDelta(effectChance)) {
                    wretchEffect.at(x, y);
                }
            } else {
                efficiency *= 1;
            }
            super.updateTile();
        }
        public float time(){
            return time / wretch;
        }
        @Override
        public void write(Writes write){
            super.write(write);

            write.f(time);
            write.f(wretch);
            write.bool(wretched);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            time = read.f();
            wretch = read.f();
            wretched = read.bool();
        }
    }
}
