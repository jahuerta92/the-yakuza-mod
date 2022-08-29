package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.YakuzaMod;
import theYakuza.actions.BetterMakeTempCardInHandAction;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makePowerPath;

public class DragonOfDojimaStylePower extends AbstractGrabPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = YakuzaMod.makeID("DragonOfDojimaStyle");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("dragon_of_dojima_style_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("dragon_of_dojima_style_power32.png"));

    public DragonOfDojimaStylePower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount > 0) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            AbstractCard tempCard = card.makeStatEquivalentCopy();
            if (tempCard.costForTurn > 0) {
                this.flash();
                tempCard.baseDamage /= 3;
                tempCard.damage = tempCard.baseDamage;
                tempCard.applyPowers();
                tempCard.purgeOnUse = true;

                tempCard.cost -= 1;
                tempCard.costForTurn -= 1;
                tempCard.isCostModified = true;

                tempCard.rawDescription = "Purge. NL " + tempCard.rawDescription;
                AbstractDungeon.actionManager.addToBottom(new BetterMakeTempCardInHandAction(tempCard, amount));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DragonOfDojimaStylePower(owner, source, amount);
    }
}
