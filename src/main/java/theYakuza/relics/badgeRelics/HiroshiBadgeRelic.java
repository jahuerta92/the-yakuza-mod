package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class HiroshiBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("HiroshiBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hiroshi_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hiroshi_badge_relic.png"));

    private float MODIFIER_AMT = 0.1F;

    public HiroshiBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom &&
                !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) &&
                !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)) {
            flash();
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.currentHealth > (int) ((float) m.maxHealth * (1.0F - this.MODIFIER_AMT))) {
                    m.currentHealth = (int) ((float) m.maxHealth * (1.0F - this.MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }
        }
    }

}
