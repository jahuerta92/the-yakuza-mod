package theYakuza.items;

import static theYakuza.DefaultMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import theYakuza.DefaultMod;

public class SakeItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = DefaultMod.makeID("SakeItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 1;
    private static final int UPGRADED_ATTACK_AMOUNT = 0;

    private static final int SKILL_AMOUNT = 1;
    private static final int UPGRADED_SKILL_AMOUNT = 0;

    private static final int THROW_AMOUNT = 2;
    private static final int UPGRADED_THROW_AMOUNT = 1;

    public SakeItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("default_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        if (throwValue > 1) {
            description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                    DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                    DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[6];
        } else {
            description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                    DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                    DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[5];
        }

    }

    @Override
    public int performAttackEffect(AbstractCard card) {
        return 0;
    }

    @Override
    public void performAdditionalAttackEffect(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, attackValue)));
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DexterityPower(AbstractDungeon.player, skillValue)));

    }

    @Override
    public void performThrownEffect() {
        AbstractDungeon.actionManager
                .addToBottom(new DrawCardAction(AbstractDungeon.player, throwValue));
    }

}
