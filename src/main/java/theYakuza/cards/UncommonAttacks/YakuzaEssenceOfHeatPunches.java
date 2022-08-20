package theYakuza.cards.UncommonAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
public class YakuzaEssenceOfHeatPunches extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfHeatPunches.class.getSimpleName()); // USE THIS
                                                                                                         // ONE
                                                                                                         // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Heat_Punches.png");// "public static final String
                                                                                        // IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2; // 1// COST = ${COST}
    private static final int REDUCED_COST = 1;

    private static final int DAMAGE = 5; // 7// DAMAGE = ${DAMAGE}
    private static final int HEAT = 1; // 7// DAMAGE = ${DAMAGE}

    // /STAT DECLARATION/
    public YakuzaEssenceOfHeatPunches() { // public ${NAME}() - This one and the one right under the imports are the
                                          // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int repeats = 1;
        if (p.hasPower(HeatLevelPower.POWER_ID)) {
            repeats += p.getPower(HeatLevelPower.POWER_ID).amount;
        }
        for (int i = 0; i < repeats; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new HeatLevelPower(p, p, HEAT)));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(REDUCED_COST);
            initializeDescription();
        }
    }
}
