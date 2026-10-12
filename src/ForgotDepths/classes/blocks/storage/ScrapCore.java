package ForgotDepths.classes.blocks.storage;

import arc.audio.Music;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.world.blocks.storage.CoreBlock;

public class ScrapCore extends CoreBlock {
    public ScrapCore(String name) {
        super(name);
        landDuration = 1;
        landSound = launchSound = Sounds.none;
    }
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region,teamRegions[Team.sharded.id]};
    }
    public class ScrapCoreBuild extends CoreBuild {
        @Override
        public Music landMusic() {
            return null;
        }
    }
}
