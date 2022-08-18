package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import theYakuza.powers.AbstractMinigamePower;

public class MinigameAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int effectiveness;
    private int repeats;

    private int BASE_BLOCK = 3;
    private int BASE_GOLD = 5;
    private int BASE_DAMAGE = 5;
    private int BASE_DRAW = 1;
    private int BASE_DEBUFF = 1;
    private int BASE_BUFF = 1;

    public MinigameAction(final AbstractPlayer p) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        effectiveness = 1;
        repeats = 1;
    }

    public MinigameAction(final AbstractPlayer p, final int effectiveness, final int repeats) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        this.effectiveness = effectiveness;
        this.repeats = repeats;
    }

    @Override
    public void update() {
        for (int j = 0; j < repeats; j++) {
            int value = AbstractDungeon.miscRng.random(9);

            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractMinigamePower) {
                    ((AbstractMinigamePower) p).onMinigameActivation();
                }
            }

            switch (value) {
                case 0:
                    AbstractDungeon.actionManager
                            .addToBottom(new GainBlockAction(p, p, BASE_BLOCK * effectiveness));
                    break;
                case 1:
                    AbstractDungeon.actionManager
                            .addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, BASE_BUFF * effectiveness)));
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p, new LoseDexterityPower(p, BASE_BUFF * effectiveness)));

                    break;
                case 2:
                    AbstractDungeon.actionManager
                            .addToBottom(new GainGoldAction(BASE_GOLD * effectiveness));
                    break;

                case 3:
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageAction(AbstractDungeon.getRandomMonster(),
                                    new DamageInfo(null, BASE_DAMAGE * effectiveness, DamageType.THORNS),
                                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    break;

                case 4:
                    AbstractDungeon.actionManager
                            .addToBottom(new DrawCardAction(p, BASE_DRAW * effectiveness));
                    break;

                case 5:
                    for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                                new VulnerablePower(mo, BASE_DEBUFF * effectiveness, false)));
                    }
                    break;

                case 6:
                    for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                                new WeakPower(mo, BASE_DEBUFF * effectiveness, false)));
                    }
                    break;

                case 7:
                    for (int i = 0; i < effectiveness; i++) {
                        AbstractDungeon.actionManager.addToBottom(
                                new ObtainPotionAction(AbstractDungeon.returnRandomPotion(PotionRarity.COMMON, true)));
                    }
                    break;
                case 8:
                    AbstractDungeon.actionManager
                            .addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, BASE_BUFF * effectiveness)));
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p, new LoseStrengthPower(p, BASE_BUFF * effectiveness)));

                    break;

            }
        }
        isDone = true;
    }
}
