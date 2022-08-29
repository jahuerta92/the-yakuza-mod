package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theYakuza.cards.AbstractDefaultCard;

public class RetaliateAction extends AbstractGameAction {
    private AbstractPlayer p;

    public RetaliateAction(final AbstractPlayer p) {
        this.p = p;
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        // AbstractDungeon.actionManager.addToTop(new
        // WaitAction(Settings.ACTION_DUR_MED));

        CardGroup attacks = p.drawPile.getAttacks();

        if (AbstractDungeon.player.drawPile.size() == 0) {
            isDone = true;
        }

        if (attacks.group.size() > 0) {
            AbstractCard c = attacks.getRandomCard(AbstractDungeon.cardRandomRng);
            AbstractDungeon.player.drawPile.group.remove(c);
            c.current_y = -200.0F * Settings.scale;
            c.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            c.target_y = (float) Settings.HEIGHT / 2.0F;
            c.targetAngle = 0.0F;
            c.lighten(false);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.applyPowers();

            if (c instanceof AbstractDefaultCard) {
                ((AbstractDefaultCard) c).disableHeatCost();
            }

            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(c, true, 0, true, true), true);
            if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }

        isDone = true;
    }

}
