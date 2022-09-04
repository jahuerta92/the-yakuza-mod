package theYakuza.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;

public class DragonPotion extends CustomPotion {

    public static final String POTION_ID = theYakuza.YakuzaMod.makeID("DragonPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DragonPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SNECKO, PotionColor.SMOKE);

        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        isThrown = false;

        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (AbstractPower pow : target.powers) {
                if (pow.type == PowerType.BUFF) {
                    pow.stackPower(potency);
                    pow.flashWithoutSound();
                    pow.updateDescription();
                }
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new DragonPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion() {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
