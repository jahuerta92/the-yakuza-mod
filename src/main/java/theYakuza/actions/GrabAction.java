package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.items.AbstractItem;
import theYakuza.powers.AbstractGrabPower;

public class GrabAction extends AbstractGameAction {
    private AbstractItem item;

    public GrabAction(AbstractItem item) {
        this.actionType = ActionType.SPECIAL;
        this.item = item;
    }

    public void update() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractGrabPower) {
                ((AbstractGrabPower) p).onGrab();
            }
        }

        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(item));
        isDone = true;
    }
}
