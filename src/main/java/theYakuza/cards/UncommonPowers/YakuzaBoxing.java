package theYakuza.cards.UncommonPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theYakuza.DefaultMod;
import theYakuza.actions.MinigameAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheDefault;
import theYakuza.powers.BoxingPower;

import static theYakuza.DefaultMod.makeCardPath;

public class YakuzaBoxing extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(YakuzaBoxing.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");// "public static final String IMG =
                                                               // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.POWER; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1; // COST = ${COST}
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}

    private static final int TEMP_STRENGTH = 1; // DAMAGE = ${DAMAGE}

    // /STAT DECLARATION/

    public YakuzaBoxing() { // public ${NAME}() - This one and the one right under the imports are the most
                            // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MinigameAction(p));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new BoxingPower(p, p, TEMP_STRENGTH)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
