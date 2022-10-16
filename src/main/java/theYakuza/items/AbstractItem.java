package theYakuza.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;

import theYakuza.cards.CommonSkills.YakuzaEssenceOfRepair;
import theYakuza.cards.RareAttacks.YakuzaEssenceOfIronFists;
import theYakuza.cards.RareSkills.YakuzaEssenceOfSwitchGrab;
import theYakuza.cards.UncommonAttacks.YakuzaEssenceOfWeaponFinish;
import theYakuza.cards.UncommonAttacks.YakuzaEssenceOfWeaponMastery;
import theYakuza.cards.UncommonSkills.YakuzaEssenceOfFinesse;
import theYakuza.cards.UncommonSkills.YakuzaEssenceOfWeaponCounter;
import theYakuza.powers.DuctTapePower;
import theYakuza.powers.EssenceOfPolishPower;
import theYakuza.powers.EssenceOfRecyclingPower;
import theYakuza.relics.ThrowingGlovesRelic;
import theYakuza.relics.badgeRelics.ArakawaBadgeRelic;

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

    protected static final String DOUBLE_ATTACK_ACTIVATION_CARD_ID = YakuzaEssenceOfWeaponMastery.ID;
    protected static final String DOUBLE_SKILL_ACTIVATION_CARD_ID = YakuzaEssenceOfFinesse.ID;

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
        applyCustomPowers(card);
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
        if (durability + amount >= 100) {
            durability = 100;
        } else {
            durability += amount;
        }
    }

    @Override
    public void onExitStance() {
        boolean hasRelic = AbstractDungeon.player.hasRelic(ArakawaBadgeRelic.ID);
        if (durability > 0 || hasRelic) {
            if (hasRelic && durability == 0) {
                AbstractRelic r = AbstractDungeon.player.getRelic(ArakawaBadgeRelic.ID);
                r.flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                durability++;
            }
            applyCustomPowers();
            performThrownEffect();
            restoreValues();
        }
    }

    @Override
    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        int damage_mod = performAttackEffect(card);
        return atDamageGive(damage, type) + damage_mod;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        applyCustomPowers(card);
        if (consumesDurability(card)) {
            if (card.type == CardType.SKILL) {
                performSkillEffect(card);
                durability -= 1;
            } else if (card.type == CardType.ATTACK) {
                performAdditionalAttackEffect(card);
                durability -= 1;
            }

            if (durability == 0 && AbstractDungeon.player.hasPower(DuctTapePower.POWER_ID)) {
                durability++;
            }

            if (durability == 0) {

                CardCrawlGame.sound.play("BLOCK_BREAK", -0.3F);
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
                if (AbstractDungeon.player.hasPower(EssenceOfRecyclingPower.POWER_ID)) {
                    EssenceOfRecyclingPower pow = (EssenceOfRecyclingPower) AbstractDungeon.player
                            .getPower(EssenceOfRecyclingPower.POWER_ID);
                    pow.onItemBreak();
                }
            }
        }

        restoreValues();

        applyCustomPowers();
        updateDescription();
        restoreValues();

    }

    protected int combineDurabilityAndThrow() {
        return durability + throwValue;
    }

    public void applyCustomPowers() {
        AbstractPlayer owner = AbstractDungeon.player;
        for (AbstractPower p : owner.powers) {
            if (p.ID.equals(EssenceOfPolishPower.POWER_ID)) {
                int increase = owner.getPower(EssenceOfPolishPower.POWER_ID).amount;

                skillValue += increase;
                attackValue += increase;
                throwValue += increase;
            }
        }

        throwValue = combineDurabilityAndThrow();

        for (AbstractRelic r : owner.relics) {
            if (r.relicId.equals(ThrowingGlovesRelic.ID)) {
                throwValue *= 2;
            }
        }

        updateDescription();
    }

    public void applyCustomPowers(AbstractCard card) {
        applyCustomPowers();

        if (card.cardID.contains(DOUBLE_SKILL_ACTIVATION_CARD_ID)) {
            int bonus = 2;
            if (card.upgraded) {
                bonus += 1;
            }
            skillValue *= bonus;
        }

        if (card.cardID.contains(DOUBLE_ATTACK_ACTIVATION_CARD_ID)) {
            int bonus = 2;
            if (card.upgraded) {
                bonus += 1;
            }
            attackValue *= bonus;
        }

        updateDescription();
    }

    protected void restoreValues() {
        skillValue = baseSkillValue;
        attackValue = baseAttackValue;
        throwValue = baseThrowValue;

    }

    private boolean consumesDurability(AbstractCard card) {
        ArrayList<String> allowed_ids = new ArrayList<String>();
        allowed_ids.add("YakuzaGrab");
        allowed_ids.add(YakuzaEssenceOfWeaponFinish.ID);
        allowed_ids.add(YakuzaEssenceOfWeaponCounter.ID);
        allowed_ids.add(YakuzaEssenceOfSwitchGrab.ID);
        allowed_ids.add(YakuzaEssenceOfRepair.ID);
        allowed_ids.add(YakuzaEssenceOfIronFists.ID);

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
            // sb.setBlendFunction(770, 1);
            sb.draw(this.img,
                    AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX,
                    AbstractDungeon.player.drawY - 256.0F + AbstractDungeon.player.animY
                            + AbstractDungeon.player.hb_h / 2.0F,
                    256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false,
                    false);
            // sb.setBlendFunction(770, 771);

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
