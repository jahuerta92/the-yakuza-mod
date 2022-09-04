package theYakuza.relics.badgeRelics;

import basemod.abstracts.CustomRelic;
import theYakuza.YakuzaMod;
import theYakuza.util.TextureLoader;

import static theYakuza.YakuzaMod.makeRelicOutlinePath;
import static theYakuza.YakuzaMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class OmiBadgeRelic extends CustomRelic {
    public static final String ID = YakuzaMod.makeID("OmiBadgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("omi_badge_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("omi_badge_relic.png"));

    private boolean canDraw = false;
    private boolean disabledUntilEndOfTurn = false;

    public OmiBadgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRefreshHand() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.actionManager.actions.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
                && !p.hasPower("No Draw") && !AbstractDungeon.isScreenUp && canDraw
                && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT
                && p.drawPile.getAttacks().size() > 0 && p.hand.getAttacks().size() == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
            AbstractDungeon.actionManager.addToBottom(new DrawPileToHandAction(1, CardType.ATTACK));
            grayscale = true;
            canDraw = false;
        }

    }

    @Override
    public void atPreBattle() {
        this.canDraw = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        this.canDraw = true;
        this.grayscale = false;
        this.disabledUntilEndOfTurn = false;
    }

    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }
}
