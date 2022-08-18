package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.DefaultMod;
import theYakuza.util.TextureLoader;

import static theYakuza.DefaultMod.makePowerPath;

public class HeatLevelPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("HeatLevel");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("heat_level_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("heat_level_power32.png"));

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
        return damage + this.amount * 2;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount + this.amount * 2;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount + stackAmount <= 3) {
            this.amount += stackAmount;
        } else {
            this.amount = 3;
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (this.amount - reduceAmount <= 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, HeatLevelPower.POWER_ID, this.amount));
        } else {
            this.amount -= reduceAmount;
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.reducePower(1);
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new HeatLevelPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount * 2) + DESCRIPTIONS[1];
    }
}
