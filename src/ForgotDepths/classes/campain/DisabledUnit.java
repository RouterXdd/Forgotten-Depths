package ForgotDepths.classes.campain;

import arc.util.*;
import ForgotDepths.content.*;
import mindustry.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;

public class DisabledUnit extends Block {

    public float activateDamage = 280;
    public float activateTime = 75;
    public Effect activateEffect = Fx.greenBomb;
    public UnitType unitFr = FDUnits.dreadnought;
    public DisabledUnit(String name) {
        super(name);
        destructible = true;
        rotate = true;
        solid = true;
        update = true;
        createRubble = false;
        destroySound = Sounds.none;
        buildType = DisabledUnitBuild::new;
    }
    @Override
    public void setBars(){
        super.setBars();
                addBar("activation", (DisabledUnitBuild e) -> new Bar("bar.activation", Pal.remove, e::fraction));
    }
    public class DisabledUnitBuild extends Building {
        public float activateProgress;
        boolean activated = false;
        public @Nullable Unit unit;
        public float fraction(){
            return activateProgress / activateTime;
        }
        @Override
        public void updateTile(){
            if (activated) activateProgress += edelta();
            if (health() < maxHealth() - activateDamage) {
                if (activateProgress >= activateTime) {
                    kill();
                }
                if (!activated){
                    activateEffect.at(x, y);
                    activated = !activated;
                }
            }
        }
        @Override
        public void afterDestroyed(){
            unit = unitFr.create(team);
            unit.set(x, y);
            unit.rotation = rotation * 90f;
            unit.add();
        }
    }
}
