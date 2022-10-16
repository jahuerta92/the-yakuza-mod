package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makePowerPath;

public class MadDogPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = YakuzaMod.makeID("MadDog");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("mad_dog_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("mad_dog_power32.png"));

    private int originalAmount;

    public MadDogPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = this.originalAmount = amount;
        this.source = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = this.originalAmount;
            AbstractDungeon.actionManager
                    .addToTop(new ApplyPowerAction(owner, source, new HeatLevelPower(owner, source, 1)));
            AbstractDungeon.actionManager
                    .addToTop(new RollMoveAction((AbstractMonster) this.owner));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = this.originalAmount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        String plural = amount > 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[1];
        description = DESCRIPTIONS[0] + amount + plural;
    }

    @Override
    public AbstractPower makeCopy() {
        return new MadDogPower(owner, amount);
    }
}
