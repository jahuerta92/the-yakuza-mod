package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.DefaultMod;
import theYakuza.util.TextureLoader;

import static theYakuza.DefaultMod.makeRelicOutlinePath;
import static theYakuza.DefaultMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TojoBadgeRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("TojoBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tojo_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tojo_badge_relic.png"));

    public static final int GOLD_GAIN = 10;

    public TojoBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void onVictory() {
        flash();
        AbstractDungeon.actionManager.addToTop(new GainGoldAction(GOLD_GAIN));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
