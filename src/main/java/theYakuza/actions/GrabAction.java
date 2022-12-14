package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theYakuza.items.AbstractItem;
import theYakuza.powers.AbstractGrabPower;
import theYakuza.powers.DuctTapePower;

public class GrabAction extends AbstractGameAction {
    private AbstractItem item;

    public GrabAction(AbstractItem item) {
        this.actionType = ActionType.SPECIAL;
        this.item = item;
    }

    public void update() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractGrabPower) {
                ((AbstractGrabPower) p).onGrab(item);
            }
        }

        if (!AbstractDungeon.player.hasPower(DuctTapePower.POWER_ID)
                || !(AbstractDungeon.player.stance instanceof AbstractItem)) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
            CardCrawlGame.sound.play("BLOCK_GAIN_1");
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(item));
        }
        isDone = true;
    }
}
