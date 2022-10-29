package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.rewards.KomakiItem;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class TojoBadgeRelicV2 extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("TojoBadgeRelicV2");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tojo_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tojo_badge_relic.png"));

    public static final int TEMP_HP = 2;

    public TojoBadgeRelicV2() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        BadgeRelicCollection.badgeCollection.reset();
    }

    // Description
    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            this.pulse = true;
            this.beginPulse();
        } else {
            this.pulse = false;
        }

    }

    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite
                || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            this.flash();
            this.pulse = false;
            String relicId = BadgeRelicCollection.badgeCollection.getRandomUnseenBadgeKey();
            if (relicId.equals(NishikiyamaBadgeRelic.ID)) {
                AbstractDungeon.getCurrRoom().rewards.add(new KomakiItem());
            }
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RelicLibrary.getRelic(relicId)));
        }

    }

}
