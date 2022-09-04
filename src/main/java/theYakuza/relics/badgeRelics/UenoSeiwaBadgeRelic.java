package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;

public class UenoSeiwaBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("UenoSeiwaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ueno_seiwa_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ueno_seiwa_badge_relic.png"));

    public UenoSeiwaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof TreasureRoom) {
            room.addCardReward(new RewardItem());
            room.addCardReward(new RewardItem());
            this.pulse = true;
            this.beginPulse();
        } else {
            this.pulse = false;
        }
    }

}
