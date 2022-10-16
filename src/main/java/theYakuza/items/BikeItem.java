package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import theYakuza.YakuzaMod;

public class BikeItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("BikeItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 6;
    private static final int UPGRADED_ATTACK_AMOUNT = 2;

    private static final int SKILL_AMOUNT = 7;
    private static final int UPGRADED_SKILL_AMOUNT = 2;

    private static final int THROW_AMOUNT = 8;
    private static final int UPGRADED_THROW_AMOUNT = 2;

    public BikeItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("bike_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[5];
    }

    @Override
    protected int combineDurabilityAndThrow() {
        return durability * throwValue;
    }

    @Override
    public int performAttackEffect(AbstractCard card) {
        return 0;
    }

    @Override
    public void performAdditionalAttackEffect(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                attackValue, DamageType.THORNS, AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager
                .addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, skillValue));
    }

    @Override
    public void performThrownEffect() {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(AbstractDungeon.player, throwValue, DamageType.NORMAL,
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

}
