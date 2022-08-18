package theYakuza.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.DefaultMod;
import theYakuza.cards.TemporaryCards.YakuzaGrabBrokenPipe;
import theYakuza.util.TextureLoader;

import static theYakuza.DefaultMod.makePowerPath;

public class EssenceOfRecyclingPower extends AbstractGrabPower implements NonStackablePower, CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("EssenceOfRecycling");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public boolean isUpgraded;

    public EssenceOfRecyclingPower(Boolean isUpgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.isUpgraded = isUpgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onItemBreak() {
        this.flash();

        AbstractCard c = new YakuzaGrabBrokenPipe();
        if (isUpgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    @Override
    public void updateDescription() {
        if (isUpgraded) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new EssenceOfRecyclingPower(isUpgraded);
    }
}
