package theYakuza.cards.UncommonPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theYakuza.YakuzaMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.powers.EssenceOfBurningHeatPower;

import static theYakuza.YakuzaMod.makeCardPath;
import basemod.AutoAdd;

@AutoAdd.Ignore
public class YakuzaEssenceOfBurningHeat extends AbstractDynamicCard {

    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfBurningHeat.class.getSimpleName());
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Burning_Heat.png");// "public static final String
                                                                                        // IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.POWER; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 2; // COST = ${COST}

    private static final int MAGIC = 4; // DAMAGE = ${DAMAGE}
    private static final int UPGRADED_MAGIC = 3; // DAMAGE = ${DAMAGE}

    // /STAT DECLARATION/

    public YakuzaEssenceOfBurningHeat() { // public ${NAME}() - This one and the one right under the imports are the
                                          // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new EssenceOfBurningHeatPower(p, p, magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
