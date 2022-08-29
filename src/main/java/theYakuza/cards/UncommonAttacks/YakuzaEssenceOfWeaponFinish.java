package theYakuza.cards.UncommonAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import theYakuza.YakuzaMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.items.AbstractItem;
import theYakuza.powers.HeatLevelPower;

import static theYakuza.YakuzaMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfWeaponFinish extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfWeaponFinish.class.getSimpleName()); // USE THIS
                                                                                                         // ONE
                                                                                                         // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Weapon_Finish.png");// "public static final String
                                                                                         // IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 2; // 1// COST = ${COST}

    private static final int DAMAGE = 18; // 7// DAMAGE = ${DAMAGE}
    private static final int UPAGRADE_DAMAGE_PLUS = 4;
    private static final int HEAT_COST = 1;

    // /STAT DECLARATION/
    public YakuzaEssenceOfWeaponFinish() { // public ${NAME}() - This one and the one right under the imports are the
                                           // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        heatCost = baseHeatCost = HEAT_COST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, HeatLevelPower.POWER_ID, heatCost));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPAGRADE_DAMAGE_PLUS);
            initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        if (heatCost > 0) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            if (p.hasPower(HeatLevelPower.POWER_ID)
                    && p.getPower(HeatLevelPower.POWER_ID).amount >= heatCost
                    && p.stance instanceof AbstractItem) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            super.triggerOnGlowCheck();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (heatCost > 0 && !(p.stance instanceof AbstractItem)) {
            this.cantUseMessage = "No item equipped.";
            return false;
        } else {
            return canUse;
        }
    }

}
