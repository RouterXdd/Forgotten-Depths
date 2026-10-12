package ForgotDepths.classes.blocks.defence;

import ForgotDepths.content.FDFX;
import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.*;

public class AmmoTurret extends Turret {
    public int maxAmmo = 10;
    public float reloadTime = 10 * 60f;
    public float noBuildRange = 3f * 8f;
    public TextureRegion iconRegion;
    public AmmoTurret(String name) {
        super(name);
        outlineIcon = false;
        drawer = new DrawTurret("scrap-");
    }
    @Override
    public void setStats() {
        super.setStats();
        if (maxAmmo >=1 ) {
            stats.add(Stat.ammoCapacity, maxAmmo);
            stats.add(Stat.productionTime, reloadTime / 60f, StatUnit.seconds);
        }
    }
    @Override
    public void load() {
        super.load();
        iconRegion = Core.atlas.find(name + "-icon");
    }
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{iconRegion};
    }
    @Override
    public void setBars(){
        super.setBars();
        if (maxAmmo >=1 ) {
            addBar("ammo", (AmmoTurretBuild e) -> new Bar("bar.ammo", Pal.techBlue, e::amount));
        }
    }
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        boolean tooClose = false;
        if (tile != null) {
            tooClose = indexer.eachBlock(team, tile.worldx() + offset, tile.worldy() + offset, noBuildRange, b -> {
                if (b.block instanceof AmmoTurret) return true;
                if (b.block instanceof ConstructBlock) {
                    if (b instanceof ConstructBlock.ConstructBuild g) {
                        return g.current instanceof AmmoTurret && g.previous instanceof AmmoTurret;
                    }
                }
                return false;
            }, b -> {
                Drawf.dashLine(Pal.remove, tile.worldx() + offset, tile.worldy() + offset, b.x, b.y);
            });
        }
        return super.canPlaceOn(tile, team, rotation) && !tooClose;
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, noBuildRange, Pal.remove);
        if(!canPlaceOn(world.tile(x, y), player.team(), rotation)  ){
            drawPlaceText(Core.bundle.get("message.nobuild"), x, y, valid);
        }
    }
    public class AmmoTurretBuild extends TurretBuild {
        float curAmmo = maxAmmo;
        boolean active = true;
        float timer;
        public float amount(){
            return curAmmo / maxAmmo;
        }
        @Override
        public void updateTile(){
            if (maxAmmo >= 1) {
                if (curAmmo <= 0 && active) {
                    active = false;
                    timer = reloadTime;
                }
                timer -= edelta();
                if (!active) {
                    efficiency *= 0;
                }
                if (active) efficiency *= 1;
                if (timer <= 0 && !active) {
                    curAmmo = maxAmmo;
                    active = true;
                    FDFX.reload.at(tile.drawx(), tile.drawy(), size);
                }
            }
            super.updateTile();
        }
        @Override
        protected void shoot(BulletType type){
            super.shoot(type);
            if (curAmmo >= 1) {
                curAmmo -= 1;
            }
        }
        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(curAmmo);
            write.f(timer);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            curAmmo = read.f();
            timer = read.f();
        }
    }
}
