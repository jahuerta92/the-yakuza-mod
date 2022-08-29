package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class MajimasEyepatchRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("MajimasEyepatchRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("majimas_eyepatch_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("majimas_eyepatch_relic.png"));

    public static final int POWER = 1;

    public MajimasEyepatchRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHpLast(int arg1) {
        AbstractPlayer p = AbstractDungeon.player;
        if (arg1 == 0 && p.currentBlock == 0) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,
                    p, new StrengthPower(p, POWER)));
        }
        return arg1;
    }

}
