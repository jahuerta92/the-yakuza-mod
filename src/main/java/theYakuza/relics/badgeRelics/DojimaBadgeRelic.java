package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class DojimaBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("DojimaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("dojima_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("dojima_badge_relic.png"));

    private static final int GOLD_GAIN = 5;

    public DojimaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        flash();
        int baseReward = GOLD_GAIN;
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            baseReward *= 3;
        }
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            baseReward *= 6;
        }
        AbstractDungeon.getCurrRoom().addGoldToRewards(baseReward);
    }
}
