package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;

public class TojoBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("TojoBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tojo_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tojo_badge_relic.png"));

    public static final int GOLD_GAIN = 5;
    public static final int TEMP_HP = 4;

    public TojoBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        int baseReward = GOLD_GAIN;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == EnemyType.ELITE || m.type == EnemyType.BOSS) {
                baseReward *= 4;
                break;
            }
        }
        CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gainGold(baseReward);
        AbstractDungeon.actionManager
                .addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, TEMP_HP));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
