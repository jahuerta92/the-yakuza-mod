package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class BlackMondayBadgeRelic extends CustomRelic implements BetterOnSmithRelic {
    public static final String ID = YakuzaMod.makeID("BlackMondayBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("black_monday_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader
            .getTexture(makeRelicOutlinePath("black_monday_badge_relic.png"));

    private boolean isActive;

    public BlackMondayBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void betterOnSmith(AbstractCard arg0) {
        isActive = true;
        flash();
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && AbstractDungeon.currMapNode != null
                && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMPLETE && isActive) {
            RewardItem r = new RewardItem();
            AbstractDungeon.cardRewardScreen.open(r.cards, r, DESCRIPTIONS[1]);
            isActive = false;
        }
    }

}
