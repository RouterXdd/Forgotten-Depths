package ForgotDepths.content;

import ForgotDepths.graphics.FDPal;
import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.*;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static mindustry.Vars.tilesize;

public class FDFX {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Effect
      reload = new Effect( 35, e -> {
        color(FDPal.playerColor);
        Lines.stroke(e.fslope() * 2f);
        Lines.square(e.x, e.y, e.fin() * e.rotation * tilesize / 2);
        reset();
    });
}
