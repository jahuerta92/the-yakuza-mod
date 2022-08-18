package theYakuza.cards.UncommonSkills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import theYakuza.DefaultMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheDefault;

import static theYakuza.DefaultMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfSteel extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfSteel.class.getSimpleName()); // USE THIS
                                                                                                   // ONE
                                                                                                   // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG =
                                                                // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.SKILL; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1; // 1// COST = ${COST}

    private static final int MAGIC = 2;
    private static final int UPGRADED_MAGIC = 1;

    private static final int HEAT_COST = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfSteel() { // public ${NAME}() - This one and the one right under the imports are the
                                    // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        heatCost = baseHeatCost = HEAT_COST;
        magicNumber = baseMagicNumber = MAGIC;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new MetallicizePower(p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new PlatedArmorPower(p, magicNumber)));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
