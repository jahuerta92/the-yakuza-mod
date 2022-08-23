package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class IdolsRodRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("IdolsRodRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("idol_rod_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("idol_rod_relic.png"));

    public static final int POWER = 1;

    public IdolsRodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
