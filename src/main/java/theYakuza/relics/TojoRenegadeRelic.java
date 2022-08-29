package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.ReorganizeObtainRelicGetHook;
import theYakuza.YakuzaMod;
import theYakuza.powers.HeatLevelPower;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TojoRenegadeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("TojoRenegadeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tojo_renegade_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tojo_renegade_relic.png"));

    public static final int HEAT_LEVELS = 2;

    public TojoRenegadeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HeatLevelPower(p, p, HEAT_LEVELS)));
    }

    @Override
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;
        p.loseRelic(TojoBadgeRelic.ID);
        ReorganizeObtainRelicGetHook.Insert(this, p, 0, true, 1);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TojoBadgeRelic.ID);
    }

}
