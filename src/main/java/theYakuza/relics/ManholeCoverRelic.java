package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.cards.TemporaryCards.YakuzaGrabBrokenPipe;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ManholeCoverRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("ManholeCoverRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("manhole_cover_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("manhole_cover_relic.png"));

    public static final int HEAT_LEVELS = 2;

    public ManholeCoverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new YakuzaGrabBrokenPipe()));
    }

}
