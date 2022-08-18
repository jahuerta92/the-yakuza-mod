package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theYakuza.cards.AbstractDefaultCard;

public class RetaliateAction extends AbstractGameAction {
    private AbstractPlayer p;

    public RetaliateAction(final AbstractPlayer p) {
        this.p = p;
        actionType = ActionType.USE;
    }

    @Override
    public void update() {
        CardGroup attacks = p.drawPile.getAttacks();

        if (attacks.group.size() > 0) {
            AbstractCard c = attacks.getRandomCard(AbstractDungeon.cardRandomRng);
            if (c instanceof AbstractDefaultCard) {
                ((AbstractDefaultCard) c).disableHeatCost();
            }
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(c, true, 0, true, true), true);
        }

        isDone = true;
    }
}
