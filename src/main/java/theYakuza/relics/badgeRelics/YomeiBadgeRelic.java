package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YomeiBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("YomeiBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("yomei_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("yomei_badge_relic.png"));

    private static final int BLOCK = 4;
    private static final int COUNT = 3;

    public YomeiBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        counter = COUNT;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        counter = COUNT;
        grayscale = false;
    }

    @Override
    public void atTurnStart() {
        if (counter > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager
                    .addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
            counter--;
            if (counter == 0) {
                grayscale = true;
            }
        }

    }
}
