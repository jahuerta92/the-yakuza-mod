package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import theYakuza.YakuzaMod;
import theYakuza.monsters.Majima;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makePowerPath;

public class ShadowClonesPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = YakuzaMod.makeID("ShadowClones");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("shadow_clones_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("shadow_clones_power32.png"));

    public ShadowClonesPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            if (amount - damageAmount <= 0) {
                ((Majima) owner).enterSecondPhase();
                AbstractDungeon.actionManager.addToTop(new RollMoveAction((Majima) owner));
                AbstractDungeon.actionManager.addToTop(new WaitAction(4.0F));

                AbstractDungeon.actionManager.callEndTurnEarlySequence();
                CardCrawlGame.sound.play("YAKUZA_MAJIMA_LAUGH");
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE, true));

                AbstractDungeon.actionManager.addToBottom(
                        new RemoveSpecificPowerAction(owner, owner, ShadowClonesPower.POWER_ID));
                // AbstractDungeon.actionManager.addToBottom(new EndTurnAction());
            } else {
                AbstractDungeon.actionManager
                        .addToBottom(new ReducePowerAction(owner, owner, ShadowClonesPower.POWER_ID, damageAmount));
            }

            // return damageAmount;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChairmanPower(owner, source, amount);
    }
}
