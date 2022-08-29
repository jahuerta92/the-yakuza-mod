package theYakuza.relics;

import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;

public class IdolsRodRelic extends CustomMinigameRelic {
    public static final String ID = YakuzaMod.makeID("IdolsRodRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("idol_rod_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("idol_rod_relic.png"));

    public static final int POWER = 1;
    public static final int POTION_CHANCE = 20;
    public static final int POTION_CAP = 2;

    public IdolsRodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
        counter = POTION_CAP;
    }

    @Override
    public void atBattleStart() {
        setCounter(POTION_CAP);
    }

    @Override
    public void onMinigameActivation() {
        int roll = AbstractDungeon.miscRng.random(100);
        if (roll < POTION_CHANCE && counter > 0) {
            AbstractDungeon.actionManager
                    .addToBottom(new ObtainPotionAction(
                            AbstractDungeon.returnRandomPotion(PotionRarity.COMMON, true)));
            setCounter(counter - 1);
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
