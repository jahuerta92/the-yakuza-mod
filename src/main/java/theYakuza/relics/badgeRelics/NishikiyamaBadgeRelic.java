package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class NishikiyamaBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("NishikiyamaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("nishikiyama_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader
            .getTexture(makeRelicOutlinePath("nishikiyama_badge_relic.png"));

    public NishikiyamaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        flash();
        // openOnce = true;
        // RewardItem r = (RewardItem) new KomakiItem();
        // AbstractDungeon.cardRewardScreen.open(r.cards, r, DESCRIPTIONS[1]);
    }

    // @Override
    // public void update() {
    // super.update();
    // if (openOnce && !AbstractDungeon.isScreenUp) {
    // AbstractDungeon.combatRewardScreen.reopen();
    // openOnce = false;
    // }
    // }

}
