package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShimanoBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("ShimanoBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shimano_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shimano_badge_relic.png"));

    public ShimanoBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        counter = 0;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        counter = 0;
        grayscale = true;
    }

    @Override
    public void onVictory() {
        counter = 0;
        grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == CardType.ATTACK) {
            counter++;
            grayscale = false;
        }
        if (targetCard.type == CardType.SKILL && counter > 0) {
            flash();
            grayscale = true;
            AbstractDungeon.actionManager
                    .addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, counter));
            counter = 0;
        } else {
            grayscale = true;
            counter = 0;
        }
    }

}
