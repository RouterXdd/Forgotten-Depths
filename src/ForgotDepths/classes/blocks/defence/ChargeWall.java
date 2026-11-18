package ForgotDepths.classes.blocks.defence;

import ForgotDepths.FDMain;
import ForgotDepths.classes.blocks.production.WretchingDrill;
import ForgotDepths.graphics.FDPal;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Lightning;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.TileBitmask;
import mindustry.world.blocks.defense.Wall;

public class ChargeWall extends Wall {

    public TextureRegion glowRegion;
    public Color glowColor;
    public int glowAmount;
    public float chargeReq, chargeShift;
    public float lightningCDamage;
    public Color lightningCColor;
    public int lightningCAmount, lightningCLength;

    public ChargeWall(String name) {
        super(name);
        glowColor = Color.valueOf("83fff8");
        glowAmount = 2;
        lightningCColor = FDPal.shockColor;
        chargeReq = 40;
        chargeShift = 1;
        lightningCDamage = 20;
        lightningCAmount = 4;
        lightningCLength = 6;
    }
    @Override
    public void load(){
        super.load();

        glowRegion = Core.atlas.find(name + "-glow");
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("charge", (ChargeWallBuild e) -> new Bar(() -> Core.bundle.format("bar.charge", Strings.fixed((e.charge / chargeReq) * 100, 0)),() -> FDPal.shockColor, e::charge));
    }

    public class ChargeWallBuild extends Building {
        float charge = 0;
        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);
            charge += bullet.damage() / 2f;
            if (charge >= chargeReq) {
                for (int i = 0; i < lightningCAmount; i++) {
                    Lightning.create(team, lightningCColor, lightningCDamage, x, y, Mathf.random(0, 360), lightningCLength);
                    lightningSound.at(tile, Mathf.random(0.9f, 1.1f));
                }
                charge = 0;
            }

            return true;
        }
        public float charge(){
            return charge / chargeReq;
        }
        @Override
        public void draw(){
            super.draw();
            Draw.reset();
            if (charge() > 0) {
                for (int i = 0; i < glowAmount; i++) {
                    Draw.color(glowColor);
                    Draw.alpha(charge() / 1.5f);
                    Draw.rect(glowRegion, x + (Mathf.random(-chargeShift, chargeShift) * charge()), y + (Mathf.random(-chargeShift, chargeShift) * charge()));
                }
            }
        }
        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(charge);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            charge = read.f();
        }
    }
}
