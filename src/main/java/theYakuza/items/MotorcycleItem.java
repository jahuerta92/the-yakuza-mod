package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import theYakuza.YakuzaMod;
import theYakuza.powers.WeavePower;

public class MotorcycleItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("MotorcycleItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 2;
    private static final int UPGRADED_ATTACK_AMOUNT = 0;

    private static final int SKILL_AMOUNT = 1;
    private static final int UPGRADED_SKILL_AMOUNT = 0;

    private static final int THROW_AMOUNT = 32;
    private static final int UPGRADED_THROW_AMOUNT = 10;

    public MotorcycleItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("motorcycle_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb

        description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[5];
    }

    @Override
    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        applyCustomPowers(card);
        int currentAttackValue = attackValue;
        restoreValues();
        return atDamageGive(damage * currentAttackValue, type);
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new WeavePower(AbstractDungeon.player, AbstractDungeon.player, skillValue)));

    }

    @Override
    public void performThrownEffect() {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,
                DamageInfo.createDamageMatrix(throwValue), DamageType.NORMAL,
                AttackEffect.BLUNT_HEAVY));
    }

}
