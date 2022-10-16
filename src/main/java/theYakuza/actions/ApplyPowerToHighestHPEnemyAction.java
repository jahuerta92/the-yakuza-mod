package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyPowerToHighestHPEnemyAction extends AbstractGameAction {
    private AbstractPower powerToApply;
    private boolean isFast;
    private AttackEffect effect;
    private boolean ignoreBlock;

    public ApplyPowerToHighestHPEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount,
            boolean isFast, AttackEffect effect, boolean ignoreBlock) {
        this.setValues((AbstractCreature) null, source, stackAmount);
        this.powerToApply = powerToApply;
        this.isFast = isFast;
        this.effect = effect;
        this.ignoreBlock = ignoreBlock;
    }

    public ApplyPowerToHighestHPEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount,
            boolean isFast, boolean ignoreBlock) {
        this(source, powerToApply, stackAmount, isFast, AttackEffect.NONE, ignoreBlock);
    }

    public ApplyPowerToHighestHPEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount,
            boolean isFast) {
        this(source, powerToApply, stackAmount, isFast, AttackEffect.NONE, true);
    }

    public ApplyPowerToHighestHPEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(source, powerToApply, stackAmount, false);
    }

    public ApplyPowerToHighestHPEnemyAction(AbstractCreature source, AbstractPower powerToApply) {
        this(source, powerToApply, -1);
    }

    public void update() {
        int maxHP = -999;
        AbstractMonster target = null;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.currentHealth > maxHP && (m.currentBlock < 1 || ignoreBlock)) {
                target = m;
                maxHP = m.currentHealth;
            }
        }
        if (target != null) {
            this.target = target;
            this.powerToApply.owner = this.target;
            if (this.target != null) {
                this.addToTop(
                        new ApplyPowerAction(this.target, this.source, this.powerToApply, this.amount, this.isFast,
                                this.effect));
            }

        }
        this.isDone = true;
    }
}
