package ForgotDepths.classes.blocks.environment;

import ForgotDepths.graphics.FDDraw;
import ForgotDepths.graphics.GraphicUtils;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Geometry;
import arc.util.Nullable;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.TileBitmask;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.liquid.LiquidRouter;

public class BitmaskedFloor extends Floor {

    public TextureRegion[] edgeRegions;
    public BitmaskedFloor(String name, int variants) {
        super(name, variants);
    }
    public void load() {
        super.load();
        edgeRegions = GraphicUtils.getRegions(Core.atlas.find(name + "-autotile"), 12, 4);
    }
    public void drawMain(Tile tile){
        int bits = 0;

        TextureRegion[] regions = edgeRegions;

        for(int i = 0; i < 8; i++){
            Tile other = tile.nearby(Geometry.d8[i]);
            if(checkAutotileSame(tile, other)){
                bits |= (1 << i);
            }
        }

        int bit = TileBitmask.values[bits];
        TextureRegion region = bit == 13 && autotileMidVariants > 1 ? autotileMidRegions[variant(tile.x, tile.y, autotileMidRegions.length)] : regions[bit];

        Draw.rect(region, tile.worldx(), tile.worldy());
    }
}

