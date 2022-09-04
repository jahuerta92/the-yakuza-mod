package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class SomeyaBadgeRelic extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = YakuzaMod.makeID("SomeyaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("someya_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("someya_badge_relic.png"));

    public SomeyaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower arg0, AbstractCreature arg1, AbstractCreature arg2) {
        if (arg0.type == PowerType.DEBUFF && arg1.isPlayer) {
            int roll = AbstractDungeon.miscRng.random(99);
            if (roll < 10) {
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(arg1, this));
                return false;
            }
        }
        return true;
    }

}
