package ForgotDepths.classes.blocks.defence;

import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.indexer;

public class PowerAmmoTurret extends AmmoTurret{
    public BulletType shootType;
    public PowerAmmoTurret(String name) {
        super(name);
        hasPower = true;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }
    @Override
    public void setBars(){
        super.setBars();
    }

    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

    public class PowerAmmoTurretBuild extends AmmoTurretBuild{

        @Override
        public void updateTile(){
            unit.ammo(power.status * unit.type().ammoCapacity);

            super.updateTile();
        }

        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case ammo -> power.status;
                case ammoCapacity -> 1;
                default -> super.sense(sensor);
            };
        }

        @Override
        public BulletType useAmmo(){
            //nothing used directly
            return shootType;
        }
        @Override
        public boolean hasAmmo(){
            //you can always rotate, but never shoot if there's no power
            return true;
        }

        @Override
        public BulletType peekAmmo(){
            return shootType;
        }
    }
}
