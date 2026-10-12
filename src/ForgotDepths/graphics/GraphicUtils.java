package ForgotDepths.graphics;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;

public class GraphicUtils {

    public static TextureRegion[] getRegions(TextureRegion region, int w, int h) {
        int size = w * h;
        TextureRegion[] regions = new TextureRegion[size];
        float tileW = (region.u2 - region.u) / (float)w;
        float tileH = (region.v2 - region.v) / (float)h;

        for(int i = 0; i < size; ++i) {
            float tileX = (float)(i % w) / (float)w;
            float tileY = (float)(i / w) / (float)h;
            TextureRegion reg = new TextureRegion(region);
            reg.u = Mathf.map(tileX, 0.0F, 1.0F, reg.u, reg.u2) + tileW * 0.02F;
            reg.v = Mathf.map(tileY, 0.0F, 1.0F, reg.v, reg.v2) + tileH * 0.02F;
            reg.u2 = reg.u + tileW * 0.97F;
            reg.v2 = reg.v + tileH * 0.97F;
            reg.width = reg.height = 32;
            regions[i] = reg;
        }

        return regions;
    }
}
