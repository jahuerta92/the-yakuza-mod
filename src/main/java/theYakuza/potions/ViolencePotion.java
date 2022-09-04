package theYakuza.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import theYakuza.actions.RetaliateAction;

public class ViolencePotion extends CustomPotion {

    public static final String POTION_ID = theYakuza.YakuzaMod.makeID("ViolencePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ViolencePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.S, PotionColor.SMOKE);

        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        isThrown = false;

        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < potency; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new RetaliateAction(AbstractDungeon.player));
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ViolencePotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    public void upgradePotion() {
        potency += 2;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
