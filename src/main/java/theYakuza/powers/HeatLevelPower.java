package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.YakuzaMod;
import theYakuza.relics.DragonScaleRelic;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makePowerPath;

public class HeatLevelPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = YakuzaMod.makeID("HeatLevel");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("heat_level_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("heat_level_power32.png"));

    private static final int ORIGINAL_HEAT_CAP = 3;
    private static final int DAMAGE_EFFECTS = 2;
    private static final int BLOCK_EFFECTS = 1;

    public static int getHeatCap() {
        int raisedCap = 0;
        if (AbstractDungeon.player.hasPower(ChairmanPower.POWER_ID)) {
            raisedCap += AbstractDungeon.player.getPower(ChairmanPower.POWER_ID).amount;
        }
        return ORIGINAL_HEAT_CAP + raisedCap;
    }

    public static int getHeatCap(boolean isMonster) {
        int raisedCap = 0;
        if (AbstractDungeon.player.hasPower(ChairmanPower.POWER_ID) && !isMonster) {
            raisedCap += AbstractDungeon.player.getPower(ChairmanPower.POWER_ID).amount;
        }

        return ORIGINAL_HEAT_CAP + raisedCap;
    }

    public static int getDamageEffects() {
        int modifier = DAMAGE_EFFECTS;
        if (AbstractDungeon.player.hasPower(ExtremeHeatModePower.POWER_ID)) {
            modifier += AbstractDungeon.player.getPower(ExtremeHeatModePower.POWER_ID).amount;
        }
        return modifier;
    }

    public static int getBlockEffects() {
        int modifier = BLOCK_EFFECTS;
        if (AbstractDungeon.player.hasPower(ExtremeHeatModePower.POWER_ID)) {
            modifier += AbstractDungeon.player.getPower(ExtremeHeatModePower.POWER_ID).amount;
        }
        return modifier;
    }

    public HeatLevelPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type) {
        return damage + this.amount * getDamageEffects();
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount + this.amount * getBlockEffects();
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount + stackAmount <= getHeatCap(!owner.isPlayer)) {
            this.amount += stackAmount;
        } else {
            this.amount = getHeatCap(!owner.isPlayer);
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (AbstractDungeon.player.hasPower(FuryOfTheAzureDragonPower.POWER_ID) && owner.isPlayer) {
            reduceAmount = 0;
        }

        if (this.amount - reduceAmount <= 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, HeatLevelPower.POWER_ID, this.amount));
        } else {
            this.amount -= reduceAmount;
        }
    }

    // @Override
    // public int onLoseHp(int damageAmount) {
    // if (!AbstractDungeon.player.hasRelic(DragonScaleRelic.ID) || !owner.isPlayer)
    // {
    // this.reducePower(1);
    // }
    // return damageAmount;
    // }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            if (!AbstractDungeon.player.hasRelic(DragonScaleRelic.ID) || !owner.isPlayer) {
                this.flash();
                this.reducePower(1);

            }
        }

    }
    // @Override
    // public int onAttacked(DamageInfo info, int damageAmount) {
    // if (owner.currentBlock < damageAmount) {
    // if (!AbstractDungeon.player.hasRelic(DragonScaleRelic.ID) || !owner.isPlayer)
    // {
    // this.reducePower(1);
    // }
    // }
    // return damageAmount;
    // }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (AbstractDungeon.player.hasPower(ExtremeHeatModeDecayPower.POWER_ID) && owner.isPlayer) {
            int reduceAmount = AbstractDungeon.player.getPower(ExtremeHeatModeDecayPower.POWER_ID).amount;
            reducePower(reduceAmount);
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new HeatLevelPower(owner, source, amount);
    }

    @Override
    public void updateDescription() { // ["Increase attack and block by #b",". Up to #b", "stacks.", " Lose 1 stack
                                      // when HP is damaged.", " Lose 1 stack at the end of your turn."]
        description = DESCRIPTIONS[0] + (amount * getDamageEffects()) +
                DESCRIPTIONS[1] + (amount * getBlockEffects()) +
                DESCRIPTIONS[2] + getHeatCap() + DESCRIPTIONS[3];
        if (!AbstractDungeon.player.hasPower(FuryOfTheAzureDragonPower.POWER_ID)) {
            description += DESCRIPTIONS[4];
            if (AbstractDungeon.player.hasPower(ExtremeHeatModeDecayPower.POWER_ID)) {
                int decay = AbstractDungeon.player.getPower(ExtremeHeatModeDecayPower.POWER_ID).amount;

                description += DESCRIPTIONS[5] + decay;
                if (decay == 1) {
                    description += DESCRIPTIONS[6];
                } else {
                    description += DESCRIPTIONS[7];
                }
            }
        }
    }
}
