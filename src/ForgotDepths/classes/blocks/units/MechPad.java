package ForgotDepths.classes.blocks.units;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.storage.CoreBlock;

public class MechPad extends Block {
    public UnitType unitType;
    public float craftTime;
    public float cooldown;
    public float spawnRot;
    public float spawnForce;
    protected TextureRegion arrowRegion;

    public MechPad(String name) {
        super(name);
        this.unitType = UnitTypes.dagger;
        this.craftTime = 100.0F;
        this.cooldown = 0.1F;
        this.spawnRot = 90.0F;
        this.spawnForce = 3.0F;
        this.update = this.configurable = true;
        this.hasItems = this.solid = false;
        this.ambientSound = Sounds.unitCreate;
        this.ambientSoundVolume = 0.08F;
    }

    public void setStats() {
        super.setStats();
    }

    public void load() {
        super.load();
        this.arrowRegion = Core.atlas.find("transfer-arrow");
    }

    public boolean canReplace(Block other) {
        return other.alwaysReplace;
    }

    public class MechPadBuild extends Building implements ControlBlock {
        @Nullable
        protected BlockUnitc thisU;
        protected float time;
        protected float heat;
        protected boolean revert;

        public MechPadBuild() {
        }

        public boolean canControl() {
            return false;
        }

        public boolean inRange(Player player) {
            return player.unit() != null && !player.unit().dead && Math.abs(player.unit().x - this.x) <= 20.0F && Math.abs(player.unit().y - this.y) <= 20.0F;
        }

        public void drawSelect() {
            Draw.color(this.isValid() ? (this.inRange(Vars.player) ? Color.orange : Pal.accent) : Pal.darkMetal);
            float length = (float)(8 * MechPad.this.size) / 2.0F + 3.0F + Mathf.absin(Time.time, 5.0F, 2.0F);
            Draw.rect(MechPad.this.arrowRegion, this.x + length, this.y, 180.0F);
            Draw.rect(MechPad.this.arrowRegion, this.x, this.y + length, 270.0F);
            Draw.rect(MechPad.this.arrowRegion, this.x + -1.0F * length, this.y, 360.0F);
            Draw.rect(MechPad.this.arrowRegion, this.x, this.y + -1.0F * length, 450.0F);
            Draw.color();
        }

        public boolean shouldShowConfigure(Player player) {
            return this.isValid() && this.inRange(player);
        }

        public Unit unit() {
            if (this.thisU == null) {
                this.thisU = (BlockUnitc)UnitTypes.block.create(this.team);
                this.thisU.tile((Building)this.self());
            }

            return (Unit)this.thisU;
        }

        public boolean configTapped() {
            if (this.isValid() && this.inRange(Vars.player)) {
                this.configure((Object)null);
                return false;
            } else {
                return false;
            }
        }

        public void configured(@Nullable Unit unit, @Nullable Object value) {
            if (unit != null && unit.isPlayer() && !(unit instanceof BlockUnitc)) {
                this.time = 0.0F;
                this.revert = unit.type == MechPad.this.unitType;
                if (!Vars.net.client()) {
                    unit.getPlayer().unit(this.unit());
                }
            }

        }

        public boolean shouldAmbientSound() {
            return this.inProgress();
        }

        public void updateTile() {
            if (this.inProgress()) {
                this.time += this.edelta() * (float)(this.isValid() ? 1 : 0) * Vars.state.rules.unitBuildSpeedMultiplier;
                if (this.time >= MechPad.this.craftTime) {
                    this.finishUnit();
                }
            }

            this.heat = Mathf.lerpDelta(this.heat, this.inProgress() ? 1.0F : 0.0F, MechPad.this.cooldown);
        }

        public UnitType getResultUnit() {
            return this.revert ? this.bestCoreUnit() : MechPad.this.unitType;
        }

        public UnitType bestCoreUnit() {
            return ((CoreBlock)this.thisU.getPlayer().bestCore().block).unitType;
        }

        public boolean inProgress() {
            return this.thisU != null && this.isControlled();
        }

        public void finishUnit() {
            Player thisP = this.thisU.getPlayer();
            if (thisP != null) {
                Fx.spawn.at((Position)this.self());
                if (!Vars.net.client()) {
                    Unit unit = this.getResultUnit().create(this.team);
                    unit.set((Position)this.self());
                    unit.rotation = MechPad.this.spawnRot;
                    unit.impulse(0.0F, MechPad.this.spawnForce);
                    unit.set(this.getResultUnit(), thisP);
                    unit.spawnedByCore = true;
                    unit.add();
                }

                if (Vars.state.isCampaign() && thisP == Vars.player) {
                    this.getResultUnit().unlock();
                }

                this.consume();
                this.time = 0.0F;
                this.revert = false;
            }
        }

        public void draw() {
            super.draw();
            if (this.inProgress()) {
                float progress = Mathf.clamp(this.time / MechPad.this.craftTime);
                Draw.color(Pal.darkMetal);
                Lines.stroke(2.0F * this.heat);
                Fill.poly(this.x, this.y, 4, 10.0F * this.heat);
                Draw.reset();
                TextureRegion region = this.getResultUnit().fullIcon;
                Draw.color(0.0F, 0.0F, 0.0F, 0.4F * progress);
                Draw.rect("circle-shadow", this.x, this.y, (float)region.width / 3.0F, (float)region.width / 3.0F);
                Draw.color();
                Draw.draw(35.0F, () -> {
                    try {
                        Drawf.construct(this.x, this.y, region, 0.0F, progress, Vars.state.rules.unitBuildSpeedMultiplier, this.time);
                        Lines.stroke(this.heat, Pal.accentBack);
                        float pos = Mathf.sin(this.time, 6.0F, 8.0F);
                        Lines.lineAngleCenter(this.x + pos, this.y, 90.0F, 16.0F - Math.abs(pos) * 2.0F);
                        Draw.color();
                    } catch (Throwable var4) {
                    }

                });
                Lines.stroke(1.5F * this.heat);
                Draw.color(Pal.accentBack);
                Lines.poly(this.x, this.y, 4, 8.0F * this.heat);
                float oy = -7.0F;
                float len = 6.0F * this.heat;
                Lines.stroke(5.0F);
                Draw.color(Pal.darkMetal);
                Lines.line(this.x - len, this.y + oy, this.x + len, this.y + oy, false);
                Fill.tri(this.x + len, this.y + oy - Lines.getStroke() / 2.0F, this.x + len, this.y + oy + Lines.getStroke() / 2.0F, this.x + len + Lines.getStroke() * this.heat, this.y + oy);
                Fill.tri(this.x + len * -1.0F, this.y + oy - Lines.getStroke() / 2.0F, this.x + len * -1.0F, this.y + oy + Lines.getStroke() / 2.0F, this.x + (len + Lines.getStroke() * this.heat) * -1.0F, this.y + oy);
                Lines.stroke(3.0F);
                Draw.color(Pal.accent);
                Lines.line(this.x - len, this.y + oy, this.x - len + len * 2.0F * progress, this.y + oy, false);
                Fill.tri(this.x + len, this.y + oy - Lines.getStroke() / 2.0F, this.x + len, this.y + oy + Lines.getStroke() / 2.0F, this.x + len + Lines.getStroke() * this.heat, this.y + oy);
                Fill.tri(this.x + len * -1.0F, this.y + oy - Lines.getStroke() / 2.0F, this.x + len * -1.0F, this.y + oy + Lines.getStroke() / 2.0F, this.x + (len + Lines.getStroke() * this.heat) * -1.0F, this.y + oy);
                Draw.reset();
            }
        }

        public void read(Reads read, byte revision) {
            super.read(read, revision);
            this.time = read.f();
            this.revert = read.bool();
        }

        public void write(Writes write) {
            super.write(write);
            write.f(this.time);
            write.bool(this.revert);
        }
    }
}

