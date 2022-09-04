package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theYakuza.powers.FuryOfTheAzureDragonPower;
import theYakuza.powers.HeatLevelPower;

public class HeatLevelCostAction extends AbstractGameAction {
    public int heat;

    public HeatLevelCostAction(final int cost) {
        this.actionType = ActionType.SPECIAL;
        this.heat = cost;
    }

    public void update() {
        if (!AbstractDungeon.player.hasPower(FuryOfTheAzureDragonPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(AbstractDungeon.player,
                            AbstractDungeon.player,
                            HeatLevelPower.POWER_ID,
                            heat));
        }
        isDone = true;
    }

}
