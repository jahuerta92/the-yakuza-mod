package theYakuza.cards.UncommonSkills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
public class YakuzaEssenceOfWeaponCounter extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfWeaponCounter.class.getSimpleName()); // USE THIS
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

    private static final int COST = 2; // 1// COST = ${COST}

    private static final int BLOCK = 14; // 7// DAMAGE = ${DAMAGE}
    private static final int UPGRADE_BLOCK_PLUS = 3;
    private static final int HEAT_COST = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfWeaponCounter() { // public ${NAME}() - This one and the one right under the imports are the
                                            // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        heatCost = baseHeatCost = HEAT_COST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, HeatLevelPower.POWER_ID, heatCost));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_PLUS);
            initializeDescription();
        }
    }
}
