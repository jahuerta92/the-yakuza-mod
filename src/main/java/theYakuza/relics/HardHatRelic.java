package theYakuza.relics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.powers.HeatLevelPower;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HardHatRelic extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = YakuzaMod.makeID("HardHatRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hard_hat_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hard_hat_relic.png"));

    public static final int POWER = 1;

    public HardHatRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public boolean onApplyPower(AbstractPower arg0, AbstractCreature arg1, AbstractCreature arg2) {
        // TODO Auto-generated method stub
        return !((arg0.ID.equals(StrengthPower.POWER_ID) ||
                arg0.ID.equals(DexterityPower.POWER_ID) ||
                arg0.ID.equals(HeatLevelPower.POWER_ID)) && arg1.isPlayer);
    }

}
