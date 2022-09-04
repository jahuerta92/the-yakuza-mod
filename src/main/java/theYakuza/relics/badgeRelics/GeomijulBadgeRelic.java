package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GeomijulBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("GeomijulBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("geomijul_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("geomijul_badge_relic.png"));

    private static final int POWER = 8;

    public GeomijulBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (!AbstractDungeon.getMonsters().monsters.isEmpty()) {
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster();
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -POWER)));
            if (m != null && !m.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new GainStrengthPower(m, POWER), POWER));
            }
        }
    }

}
