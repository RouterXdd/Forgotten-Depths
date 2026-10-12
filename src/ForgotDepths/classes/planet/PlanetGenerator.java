package ForgotDepths.classes.planet;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.noise.Simplex;
import mindustry.content.Loadouts;
import mindustry.world.Block;

import static ForgotDepths.content.FDBlocks.*;
import static mindustry.content.Blocks.*;

public class PlanetGenerator extends mindustry.maps.generators.PlanetGenerator {
    public float heightScl = 0.75f, octaves = 6, persistence = 0.7f, heightPow = 3f, heightMult = 1.5f;
    {
        defaultLoadout = Loadouts.basicShard;
    }

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    @Override
    public void getColor(Vec3 position, Color out){
        Block block = rawHeight(position) < 0.36f ? oldScrapFloor : darksand;
        out.set(block.mapColor).a(1f - block.albedo);
    }
}
