package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.stances.AbstractStance;

import theYakuza.items.AbstractItem;

public class RepairAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractStance i;
    private int amount;

    public RepairAction(final AbstractPlayer p, final AbstractStance i, final int amount) {
        this.p = p;
        this.i = i;
        this.amount = amount;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (i instanceof AbstractItem) {
            ((AbstractItem) i).repair(amount);
        }
        isDone = true;
    }
}
