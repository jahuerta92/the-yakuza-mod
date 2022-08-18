package theYakuza.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import theYakuza.cards.CommonSkills.YakuzaEssenceOfRepair;
import theYakuza.cards.RareSkills.YakuzaEssenceOfSwitchGrab;
import theYakuza.cards.UncommonAttacks.YakuzaEssenceOfWeaponFinish;
import theYakuza.cards.UncommonAttacks.YakuzaEssenceOfWeaponMastery;
import theYakuza.cards.UncommonSkills.YakuzaEssenceOfFinesse;
import theYakuza.cards.UncommonSkills.YakuzaEssenceOfWeaponCounter;
import theYakuza.powers.EssenceOfPolishPower;
import theYakuza.powers.EssenceOfRecyclingPower;

public abstract class AbstractItem extends AbstractStance {

    public int durability;

    public int throwValue;
    public int baseThrowValue;
    public int skillValue;
    public int baseSkillValue;
    public int attackValue;
    public int baseAttackValue;

    public boolean upgraded;

    public Hitbox hb;

    protected static final float FONT_SCALE;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;

    private static final String DOUBLE_ATTACK_ACTIVATION_CARD_ID = YakuzaEssenceOfWeaponMastery.ID;
    private static final String DOUBLE_SKILL_ACTIVATION_CARD_ID = YakuzaEssenceOfFinesse.ID;

    public AbstractItem() {
        super();
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        durability = 0;
        throwValue = baseThrowValue = 0;
        skillValue = baseSkillValue = 0;
        attackValue = baseAttackValue = 0;
    }

    static {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
        FONT_SCALE = 0.7F;
    }

    @Override
    public void updateDescription() {
    }

    public int performAttackEffect(AbstractCard card) {
        applyCustomPowers();
        int currentAttackValue = attackValue;
        restoreValues();
        return currentAttackValue;
    }

    public void performAdditionalAttackEffect(AbstractCard card) {

    }

    public void performSkillEffect(AbstractCard card) {
    }

    public void performThrownEffect() {
    }

    public void repair(int amount) {
        durability += amount;
    }

    @Override
    public void onExitStance() {
        if (durability > 0) {
            performThrownEffect();
        }
    }

    @Override
    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        int multiplier = 1;
        if (card.cardID.contains(DOUBLE_ATTACK_ACTIVATION_CARD_ID)) {
            multiplier = 2;
        }
        int damage_mod = performAttackEffect(card) * multiplier;
        return atDamageGive(damage, type) + damage_mod;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        applyCustomPowers();
        if (consumes_durability(card)) {
            if (card.type == CardType.SKILL) {
                durability -= 1;
                performSkillEffect(card);
                if (card.cardID.contains(DOUBLE_SKILL_ACTIVATION_CARD_ID)) {
                    performSkillEffect(card);
                }
            } else if (card.type == CardType.ATTACK) {
                durability -= 1;
                performAdditionalAttackEffect(card);
                if (card.cardID.contains(DOUBLE_ATTACK_ACTIVATION_CARD_ID)) {
                    performAdditionalAttackEffect(card);
                }

            }

            if (durability == 0) {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
                if (AbstractDungeon.player.hasPower(EssenceOfRecyclingPower.POWER_ID)) {
                    EssenceOfRecyclingPower pow = (EssenceOfRecyclingPower) AbstractDungeon.player
                            .getPower(EssenceOfRecyclingPower.POWER_ID);
                    pow.onItemBreak();
                }
            }
        }
        restoreValues();

    }

    @Override
    public void atStartOfTurn() {
        applyCustomPowers();
        updateDescription();
        restoreValues();
    }

    protected void applyCustomPowers() {
        AbstractPlayer owner = AbstractDungeon.player;
        for (AbstractPower p : owner.powers) {
            if (p.ID.equals(EssenceOfPolishPower.POWER_ID)) {
                int increase = owner.getPower(EssenceOfPolishPower.POWER_ID).amount;

                skillValue += increase;
                attackValue += increase;
            }
        }
        updateDescription();
    }

    protected void restoreValues() {
        skillValue = baseSkillValue;
        attackValue = baseAttackValue;
        throwValue = baseThrowValue;
    }

    private boolean consumes_durability(AbstractCard card) {
        ArrayList<String> allowed_ids = new ArrayList<String>();
        allowed_ids.add("YakuzaGrab");
        allowed_ids.add(YakuzaEssenceOfWeaponFinish.ID);
        allowed_ids.add(YakuzaEssenceOfWeaponCounter.ID);
        allowed_ids.add(YakuzaEssenceOfSwitchGrab.ID);
        allowed_ids.add(YakuzaEssenceOfRepair.ID);

        boolean check = true;

        for (String e : allowed_ids) {
            check = check && !card.cardID.contains(e);
        }

        return check;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.c);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img,
                    AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX,
                    AbstractDungeon.player.drawY - 256.0F + AbstractDungeon.player.animY
                            + AbstractDungeon.player.hb_h / 2.0F,
                    256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false,
                    false);
            sb.setBlendFunction(770, 771);

        }
        renderText(sb);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb,
                FontHelper.cardEnergyFont_L,
                Integer.toString(durability),
                236 + AbstractDungeon.player.drawX + AbstractDungeon.player.animX,
                -32 + AbstractDungeon.player.drawY + AbstractDungeon.player.animY
                        + AbstractDungeon.player.hb_h / 2.0F,
                new Color(0.2F, 1.0F, 1.0F, this.c.a),
                1.0F);
    }
}
