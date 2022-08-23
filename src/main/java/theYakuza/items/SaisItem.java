package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import theYakuza.YakuzaMod;
import theYakuza.powers.RetaliatePower;

public class SaisItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("SaisItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 2;
    private static final int UPGRADED_ATTACK_AMOUNT = 1;

    private static final int SKILL_AMOUNT = 2;
    private static final int UPGRADED_SKILL_AMOUNT = 1;

    private static final int THROW_AMOUNT = 1;
    private static final int UPGRADED_THROW_AMOUNT = 0;

    public SaisItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("sais_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
        // remove update here
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[5];
    }

    @Override
    public int performAttackEffect(AbstractCard card) {
        return 0;
    }

    @Override
    public void performAdditionalAttackEffect(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, attackValue)));
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(p, p, new LoseDexterityPower(p, attackValue)));

    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, skillValue)));
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, skillValue)));

    }

    @Override
    public void performThrownEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(p, p, new RetaliatePower(throwValue)));
    }

}
