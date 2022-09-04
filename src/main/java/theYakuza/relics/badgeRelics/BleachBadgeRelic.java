package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BleachBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("BleachBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bleach_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bleach_badge_relic.png"));

    public BleachBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        for (AbstractCard c : AbstractDungeon.colorlessCardPool.group) {
            if (c.type != CardType.STATUS && c.color != CardColor.CURSE) {
                AbstractDungeon.commonCardPool.addToRandomSpot(c);
            }
        }
    }

    @Override
    public void onUnequip() {
        for (AbstractCard c : AbstractDungeon.colorlessCardPool.group) {
            if (c.type != CardType.STATUS && c.color != CardColor.CURSE) {
                AbstractDungeon.commonCardPool.removeCard(c);
            }
        }
    }

}
