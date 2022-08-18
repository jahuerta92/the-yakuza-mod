package theYakuza.cards.CommonSkills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theYakuza.DefaultMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheDefault;
import theYakuza.powers.HeatLevelPower;

import static theYakuza.DefaultMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfTaunt extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfTaunt.class.getSimpleName()); // USE THIS ONE FOR
                                                                                                   // THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG =
                                                               // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.SKILL; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2; // COST = ${COST}

    private static final int HEAT = 2;
    private static final int BLOCK = 5;

    // /STAT DECLARATION/
    public YakuzaEssenceOfTaunt() { // public ${NAME}() - This one and the one right under the imports are the most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new HeatLevelPower(p, p, HEAT)));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(p, p, block));

        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
