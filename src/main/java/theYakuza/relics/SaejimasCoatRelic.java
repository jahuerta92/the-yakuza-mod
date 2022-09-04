package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.powers.RetaliatePower;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class SaejimasCoatRelic extends CustomRelic implements OnLoseBlockRelic {
    public static final String ID = YakuzaMod.makeID("SaejimasCoatRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("saejimas_coat_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("saejimas_coat_relic.png"));

    public static final int POWER = 1;

    public SaejimasCoatRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onLoseBlock(DamageInfo arg0, int arg1) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentBlock <= arg0.output) {
            flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager
                    .addToBottom(new ApplyPowerAction(p, p, new RetaliatePower(POWER)));
            AbstractDungeon.actionManager
                    .addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, POWER)));

        }
        return arg0.output;
    }

}
