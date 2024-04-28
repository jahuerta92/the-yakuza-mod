package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class SnakeFlowerBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("SnakeFlowerBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("snake_flower_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader
            .getTexture(makeRelicOutlinePath("snake_flower_badge_relic.png"));

    private boolean pickCard;

    public SnakeFlowerBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        this.pickCard = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);

        for (int i = 0; i < 20; ++i) {
            AbstractCard card = CardLibrary.getAnyColorCard(AbstractDungeon.rollRarity()).makeCopy();
            boolean containsDupe = true;

            while (true) {
                while (containsDupe) {
                    containsDupe = false;
                    for (AbstractCard c : group.group) {
                        if (c.cardID.equals(card.cardID)) {
                            containsDupe = true;
                            card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                            break;
                        }
                    }
                }

                if (group.contains(card)) {
                    --i;
                } else {
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onPreviewObtainCard(card);
                    }

                    group.addToBottom(card);
                }
                break;
            }
        }

        for (AbstractCard c : group.group) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }

        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1], false);
        this.pickCard = true;
    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = ((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            AbstractDungeon.effectList
                    .add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }

    }

}
