package ForgotDepths.classes.entities.abilities;

import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class ConstantStatusAbility extends Ability {
    public StatusEffect effect;
    public float duration, reload;

    protected float timer;

    public ConstantStatusAbility(StatusEffect effect, float duration, float reload){
        this.duration = duration;
        this.reload = reload;
        this.effect = effect;
        display = false;
    }

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.add(abilityStat("firingrate", Strings.autoFixed(60f / reload, 2)));
        t.row();
        t.add((effect.hasEmoji() ? effect.emoji() : "") + "[stat]" + effect.localizedName);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload ){
            unit.apply(effect, duration);
            timer = 0f;
        }
    }
}
