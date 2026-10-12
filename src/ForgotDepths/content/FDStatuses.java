package ForgotDepths.content;

import mindustry.type.*;

public class FDStatuses {
    public static StatusEffect jump, boosted
            ;
    public static void load(){
            jump = new StatusEffect("jump"){{
                speedMultiplier = 100;
            }};
            boosted = new StatusEffect("boosted"){{
                speedMultiplier = 1.5f;
                healthMultiplier = 2;
                damageMultiplier = 2f;
                outline = false;
            }};
    }
}
