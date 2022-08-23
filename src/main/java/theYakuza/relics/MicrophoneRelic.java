package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.actions.MinigameAction;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MicrophoneRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("MicrophoneRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("microphone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("microphone_relic.png"));

    public static final int POWER = 1;

    public MicrophoneRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        AbstractDungeon.actionManager.addToBottom(new MinigameAction(AbstractDungeon.player));
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

}
