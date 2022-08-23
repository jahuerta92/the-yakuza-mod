package theYakuza.items;

import static theYakuza.YakuzaMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import theYakuza.YakuzaMod;

public class ConeItem extends CustomItem {

    // Standard ID/Description
    public static final String ITEM_ID = YakuzaMod.makeID("ConeItem");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ITEM_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int ATTACK_AMOUNT = 2;
    private static final int UPGRADED_ATTACK_AMOUNT = 1;

    private static final int SKILL_AMOUNT = 3;
    private static final int UPGRADED_SKILL_AMOUNT = 1;

    private static final int THROW_AMOUNT = 6;
    private static final int UPGRADED_THROW_AMOUNT = 3;

    public ConeItem(int upgraded, int durability) {
        super(ITEM_ID, orbString.NAME,
                durability,
                ATTACK_AMOUNT,
                SKILL_AMOUNT,
                THROW_AMOUNT,
                makeOrbPath("traffic_cone_item.png"));

        if (upgraded >= 1) {
            this.upgrade(upgraded, UPGRADED_ATTACK_AMOUNT, UPGRADED_SKILL_AMOUNT, UPGRADED_THROW_AMOUNT);
        }
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        description = DESCRIPTIONS[0] + attackValue + DESCRIPTIONS[1] +
                DESCRIPTIONS[2] + skillValue + DESCRIPTIONS[3] +
                DESCRIPTIONS[4] + throwValue + DESCRIPTIONS[5];
    }

    @Override
    public void performSkillEffect(AbstractCard card) {
        AbstractDungeon.actionManager
                .addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, skillValue));
    }

    @Override
    public void performThrownEffect() {
        AbstractCreature mo = AbstractDungeon.getRandomMonster();
        DamageInfo dmg = new DamageInfo(AbstractDungeon.player, throwValue, DamageType.NORMAL);
        dmg.applyPowers(AbstractDungeon.player, mo);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(mo, dmg, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

}
