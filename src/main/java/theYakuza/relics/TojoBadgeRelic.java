package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TojoBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("TojoBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tojo_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tojo_badge_relic.png"));

    public static final int GOLD_GAIN = 10;

    public TojoBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void onVictory() {
        flash();
        CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.actionManager.addToTop(new GainGoldAction(GOLD_GAIN));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
