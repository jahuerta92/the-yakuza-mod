package theYakuza.rewards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.FiendFire;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

public class FiendFireItem extends RewardItem {

    private Color reticleColor;
    private boolean isBoss;

    public FiendFireItem() {
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;
        this.reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.type = com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD;
        this.isBoss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;

        this.cards = new ArrayList<>();
        this.cards.add(new FiendFire());
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);

        for (AbstractCard c : this.cards) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(c);
            }
        }
    }
}
