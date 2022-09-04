package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.powers.HeatLevelPower;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShibusawaBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("ShibusawaBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shibusawa_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shibusawa_badge_relic.png"));

    private static final int POWER = 2;

    public ShibusawaBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new HeatLevelPower(p, p, POWER)));
    }

}
