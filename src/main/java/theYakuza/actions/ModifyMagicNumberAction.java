package theYakuza.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class ModifyMagicNumberAction extends AbstractGameAction {
   private UUID uuid;

   public ModifyMagicNumberAction(UUID targetUUID, int amount) {
      this.setValues(this.target, this.source, amount);
      this.actionType = ActionType.CARD_MANIPULATION;
      this.uuid = targetUUID;
   }

   public void update() {
      for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
         c.baseMagicNumber += this.amount;
         if (c.baseMagicNumber < 0) {
            c.baseMagicNumber = 0;
         }
      }

      this.isDone = true;
   }
}
