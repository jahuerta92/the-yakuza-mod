package theYakuza.cards.UncommonSkills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import theYakuza.YakuzaMod;
import theYakuza.actions.HeatLevelCostAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.powers.WeavePower;

import static theYakuza.YakuzaMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfWeave extends AbstractDynamicCard {
    // TEXT DECLARATION
    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfWeave.class.getSimpleName()); // USE THIS
                                                                                                  // ONE
                                                                                                  // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Weave.png");// "public static final String IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.SKILL; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 1; // 1// COST = ${COST}
    private static final int HEAT_COST = 1;
    private static final int DRAW = 1;
    private static final int POWER = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfWeave() { // public ${NAME}() - This one and the one right under the imports are the
                                    // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        heatCost = baseHeatCost = HEAT_COST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HeatLevelCostAction(heatCost));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new WeavePower(p, p, POWER)));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(DRAW);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
