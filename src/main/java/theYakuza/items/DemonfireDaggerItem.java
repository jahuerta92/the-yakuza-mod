package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;

import theYakuza.YakuzaMod;

public class DemonfireDaggerItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("DemonfireDaggerItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 2;
    private static final int UPGRADED_ATTACK_AMOUNT = 1;

    private static final int SKILL_AMOUNT = 1;
    private static final int UPGRADED_SKILL_AMOUNT = 0;

    private static final int THROW_AMOUNT = 0;
    private static final int UPGRADED_THROW_AMOUNT = 0;

    private int streak;

    public DemonfireDaggerItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("demonfire_dagger_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
        streak = 0;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        if (skillValue > 0) {
            description = DESCRIPTIONS[0] + attackValue * streak + DESCRIPTIONS[1] +
                    DESCRIPTIONS[2] + skillValue * streak + DESCRIPTIONS[4] +
                    DESCRIPTIONS[5] + throwValue + DESCRIPTIONS[6];
        } else {
            description = DESCRIPTIONS[0] + attackValue * streak + DESCRIPTIONS[1] +
                    DESCRIPTIONS[2] + skillValue * streak + DESCRIPTIONS[3] +
                    DESCRIPTIONS[5] + throwValue + DESCRIPTIONS[6];
        }
    }

    public int performAttackEffect(AbstractCard card) {
        applyCustomPowers(card);
        int currentAttackValue = attackValue;
        restoreValues();
        if (AbstractDungeon.actionManager.usingCard) {
            return currentAttackValue * (streak - 1);
        } else {
            return currentAttackValue * streak;
        }

    }

    @Override
    protected int combineDurabilityAndThrow() {
        return 99 - durability + throwValue;
    }

    public void performAdditionalAttackEffect(AbstractCard card) {
        streak++;
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager
                .addToBottom(new DrawCardAction(skillValue * streak));
        streak = 0;
    }

    @Override
    public void performThrownEffect() {
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
                new DamageInfo(AbstractDungeon.player, throwValue, DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb,
                FontHelper.cardEnergyFont_L,
                Integer.toString(durability),
                236 + AbstractDungeon.player.drawX + AbstractDungeon.player.animX,
                -32 + AbstractDungeon.player.drawY + AbstractDungeon.player.animY
                        + AbstractDungeon.player.hb_h / 2.0F,
                new Color(0.2F, 1.0F, 1.0F, this.c.a),
                1.0F);
        FontHelper.renderFontCentered(sb,
                FontHelper.cardEnergyFont_L,
                Integer.toString(streak),
                236 + AbstractDungeon.player.drawX + AbstractDungeon.player.animX,
                0 + AbstractDungeon.player.drawY + AbstractDungeon.player.animY
                        + AbstractDungeon.player.hb_h / 2.0F,
                new Color(1.0F, 0.0F, 1.0F, this.c.a),
                1.0F);
    }

}
