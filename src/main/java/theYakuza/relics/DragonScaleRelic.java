package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;

public class DragonScaleRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("DragonScaleRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("dragon_scale_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("dragon_scale_relic.png"));

    public static final int POWER = 1;

    public DragonScaleRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
