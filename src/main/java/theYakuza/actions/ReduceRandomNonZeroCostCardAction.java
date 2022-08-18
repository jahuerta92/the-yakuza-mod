package theYakuza.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReduceRandomNonZeroCostCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int reduction;

    public ReduceRandomNonZeroCostCardAction(int reduction) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.reduction = reduction;
    }

    public void update() {
        CardGroup candidates = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        int length = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.costForTurn > 0) {
                candidates.addToRandomSpot(c);
                length += 1;
            }
        }

        if (length > 0) {
            AbstractCard c = candidates.getRandomCard(AbstractDungeon.cardRandomRng);
            c.superFlash(Color.GOLD.cpy());
            AbstractDungeon.actionManager
                    .addToBottom(new ReduceCostForTurnAction(c, reduction));

        }

        isDone = true;
    }
}
