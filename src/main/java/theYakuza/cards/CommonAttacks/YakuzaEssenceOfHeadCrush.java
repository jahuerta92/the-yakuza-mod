package theYakuza.cards.CommonAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
public class YakuzaEssenceOfHeadCrush extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfHeadCrush.class.getSimpleName()); // USE THIS
                                                                                                       // ONE
                                                                                                       // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG =
                                                                // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1; // 1// COST = ${COST}

    private static final int DAMAGE = 15; // 7// DAMAGE = ${DAMAGE}

    private static final int HEAT_COST = 2;
    private static final int UPGRADED_HEAT_COST = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfHeadCrush() { // public ${NAME}() - This one and the one right under the imports are the
                                        // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        heatCost = baseHeatCost = HEAT_COST;
        magicNumber = baseMagicNumber = HEAT_COST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, HeatLevelPower.POWER_ID, heatCost));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeHeatCost(UPGRADED_HEAT_COST);
            upgradeMagicNumber(-UPGRADED_HEAT_COST);
            initializeDescription();
        }
    }
}
