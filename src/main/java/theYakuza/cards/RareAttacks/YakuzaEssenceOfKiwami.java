package theYakuza.cards.RareAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theYakuza.YakuzaMod;
import theYakuza.actions.HeatLevelCostAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;

import static theYakuza.YakuzaMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfKiwami extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfKiwami.class.getSimpleName()); // USE THIS
                                                                                                   // ONE
                                                                                                   // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Kiwami.png");// "public static final String IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 3; // 1// COST = ${COST}

    private static final int DAMAGE = 40; // 7// DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DMG = 12; // 7// DAMAGE = ${DAMAGE}

    private static final int BLOCK = 20; // 7// DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_BLOCK = 8; // 7// DAMAGE = ${DAMAGE}

    private static final int HEAT_COST = 3;

    // /STAT DECLARATION/
    public YakuzaEssenceOfKiwami() { // public ${NAME}() - This one and the one right under the imports are the
                                     // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        heatCost = baseHeatCost = HEAT_COST;
        block = baseBlock = BLOCK;
        damage = baseDamage = DAMAGE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HeatLevelCostAction(heatCost));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
