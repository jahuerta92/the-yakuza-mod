package theYakuza.rewards;

import theYakuza.cards.RareAttacks.YakuzaKomakiParry;
import theYakuza.cards.RareAttacks.YakuzaKomakiTigerDrop;
import theYakuza.cards.RareSkills.YakuzaKomakiKnockback;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class KomakiItem extends RewardItem {

    public KomakiItem() {
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;
        new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.type = com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD;

        this.cards = new ArrayList<>();
        this.cards.add(new YakuzaKomakiParry());
        this.cards.add(new YakuzaKomakiTigerDrop());
        this.cards.add(new YakuzaKomakiKnockback());
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);

        for (AbstractCard c : this.cards) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(c);
            }
        }
    }
}
