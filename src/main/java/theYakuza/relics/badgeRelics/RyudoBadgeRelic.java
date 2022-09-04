package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.cards.YakuzaCardCollections;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RyudoBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("RyudoBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ryudo_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ryudo_badge_relic.png"));

    public RyudoBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUsePotion() {
        flash();
        AbstractCard c = YakuzaCardCollections.MinigameCards.getRandomCard(
                AbstractDungeon.cardRandomRng,
                CardRarity.COMMON);
        AbstractDungeon.actionManager
                .addToBottom(new MakeTempCardInHandAction(c));
    }

}
