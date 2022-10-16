package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MajimaBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("MajimaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("majima_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("majima_badge_relic.png"));

    private int minCounter = 0;

    public MajimaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        minCounter = counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        counter = minCounter = 0;
        grayscale = false;
    }

    @Override
    public void onVictory() {
        AbstractDungeon.getCurrRoom().addGoldToRewards(counter);

        counter = minCounter = 0;
        grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == CardType.ATTACK) {
            minCounter++;
            if (minCounter > counter) {
                flash();
                counter = minCounter;
            }
        } else if (targetCard.type == CardType.SKILL) {
            minCounter = 0;
        }
    }

}
