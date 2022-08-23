package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class ThrowingGlovesRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("ThrowingGlovesRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("throwing_gloves_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("throwing_gloves_relic.png"));

    public static final int POWER = 1;

    public ThrowingGlovesRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
