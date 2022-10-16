package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import theYakuza.YakuzaMod;
import theYakuza.actions.ApplyPowerToHighestHPEnemyAction;

public class NamelessKatanaItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("NamelessKatanaItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 3;
    private static final int UPGRADED_ATTACK_AMOUNT = 2;

    private static final int SKILL_AMOUNT = 1;
    private static final int UPGRADED_SKILL_AMOUNT = 0;

    private static final int THROW_AMOUNT = 0;
    private static final int UPGRADED_THROW_AMOUNT = 0;

    public NamelessKatanaItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("nameless_katana_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        String lastDesc = upgrades > 0 ? DESCRIPTIONS[6] : DESCRIPTIONS[7];
        int currentAttackValue = AbstractDungeon.player.currentBlock > 0 ? attackValue : 2 * attackValue;
        description = DESCRIPTIONS[0] + currentAttackValue + DESCRIPTIONS[1] +
                DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                DESCRIPTIONS[4] + throwValue * 2 + DESCRIPTIONS[5] + lastDesc;
    }

    @Override
    public int performAttackEffect(AbstractCard card) {
        applyCustomPowers(card);
        int currentAttackValue = AbstractDungeon.player.currentBlock > 0 ? attackValue : 2 * attackValue;
        restoreValues();
        return currentAttackValue;
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager
                .addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, skillValue));
    }

    @Override
    public void performThrownEffect() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerToHighestHPEnemyAction(
                null, new VulnerablePower(null, throwValue * 2, false),
                throwValue * 2, true, upgrades > 0));
    }
}
