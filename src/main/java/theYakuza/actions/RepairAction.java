package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.stances.AbstractStance;

import theYakuza.items.AbstractItem;

public class RepairAction extends AbstractGameAction {
    private AbstractStance i;
    private int amount;

    public RepairAction(final AbstractPlayer p, final AbstractStance i, final int amount) {
        this.i = i;
        this.amount = amount;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (i instanceof AbstractItem) {
            CardCrawlGame.sound.play("CARD_UPGRADE");
            ((AbstractItem) i).repair(amount);
        }
        isDone = true;
    }
}
