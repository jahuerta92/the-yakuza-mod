package theYakuza.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theYakuza.cards.YakuzaCardCollections;

public class SwitchGrabAction extends AbstractGameAction {
    private boolean upgraded;

    public SwitchGrabAction(Boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    public void update() {
        /*
         * CardGroup candidates = new
         * CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
         * for (AbstractCard c : YakuzaCardCollections.GrabCards.group) {
         * if (c.rarity == CardRarity.COMMON || c.rarity == CardRarity.UNCOMMON) {
         * candidates.addToRandomSpot(c);
         * System.out.println("card added -----------------------------------------");
         * }
         * }
         * AbstractCard c =
         * candidates.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();
         */
        AbstractCard c = YakuzaCardCollections.GrabCards.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();
        c.purgeOnUse = true;
        if (upgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(c, true, 0, true, true), true);

        isDone = true;
    }
}
