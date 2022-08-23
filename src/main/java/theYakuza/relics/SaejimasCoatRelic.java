package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.powers.RetaliatePower;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class SaejimasCoatRelic extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = YakuzaMod.makeID("SaejimasCoatRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("saejimas_coat_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("saejimas_coat_relic.png"));

    public static final int POWER = 1;

    public SaejimasCoatRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower arg0, AbstractCreature arg1, AbstractCreature arg2) {
        if (arg0.ID.equals(RetaliatePower.POWER_ID) && arg1.isPlayer) {
            flash();
            AbstractDungeon.actionManager
                    .addToBottom(new ApplyPowerAction(arg1, arg1, new DexterityPower(arg1, POWER)));
        }
        return true;
    }

}
