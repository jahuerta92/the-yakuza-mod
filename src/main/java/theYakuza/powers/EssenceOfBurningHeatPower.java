package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makePowerPath;

public class EssenceOfBurningHeatPower extends AbstractGrabPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = YakuzaMod.makeID("EssenceOfBurningHeat");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("essence_of_burning_heat_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("essence_of_burning_heat_power32.png"));

    private static final int BASE_DAMAGE = 3;

    public EssenceOfBurningHeatPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new EssenceOfBurningHeatPower(owner, source, amount);
    }

    @Override
    public void atStartOfTurn() {
        this.flash();

        int bonus = 0;
        if (AbstractDungeon.player.hasPower(HeatLevelPower.POWER_ID)) {
            bonus += AbstractDungeon.player.getPower(HeatLevelPower.POWER_ID).amount * amount;
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(AbstractDungeon.getRandomMonster(),
                        new DamageInfo(null, BASE_DAMAGE + bonus, DamageType.THORNS),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
