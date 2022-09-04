package theYakuza.cards.RareAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import theYakuza.YakuzaMod;
import theYakuza.actions.HeatLevelCostAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.powers.HeatLevelPower;

import static theYakuza.YakuzaMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfTheDragonGod extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfTheDragonGod.class.getSimpleName()); // USE THIS
                                                                                                         // ONE
    // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_The_Dragon_God.png");// "public static final String
                                                                                          // IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 3; // COST = ${COST}

    private static final int DAMAGE = 15; // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DMG = 5; // UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}

    private static final int HEAT_COST = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfTheDragonGod() { // public ${NAME}() - This one and the one right under the imports are the
                                           // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        heatCost = baseHeatCost = HEAT_COST;
        baseMagicNumber = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int repeats = p.getPower(HeatLevelPower.POWER_ID).amount;
        if (heatCost > 0) {
            AbstractDungeon.actionManager.addToBottom(new HeatLevelCostAction(repeats));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int effect = 0;
        if (AbstractDungeon.player.hasPower(HeatLevelPower.POWER_ID)) {
            effect += AbstractDungeon.player.getPower(HeatLevelPower.POWER_ID).amount;
        }

        this.magicNumber = effect * damage;
        this.isMagicNumberModified = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int effect = 0;
        if (AbstractDungeon.player.hasPower(HeatLevelPower.POWER_ID)) {
            effect += AbstractDungeon.player.getPower(HeatLevelPower.POWER_ID).amount;
        }

        this.magicNumber = effect * damage;
        this.isMagicNumberModified = true;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
