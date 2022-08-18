package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import theYakuza.DefaultMod;
import theYakuza.actions.RetaliateAction;
import theYakuza.util.TextureLoader;

import static theYakuza.DefaultMod.makePowerPath;

public class RetaliatePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Retaliate");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public RetaliatePower(int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        /*
         * CardGroup attacks = AbstractDungeon.player.drawPile.getAttacks();
         * for(AbstractCard card : attacks.group){
         * card.canUse();
         * }
         */

        while (amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RetaliateAction((AbstractPlayer) owner));
            amount--;
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, RetaliatePower.POWER_ID));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new RetaliatePower(amount);
    }
}
