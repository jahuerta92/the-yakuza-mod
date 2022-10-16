package theYakuza.cards.UncommonSkills;

import theYakuza.YakuzaMod;
import theYakuza.actions.GrabAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.cards.YakuzaCardCollections;
import theYakuza.characters.TheYakuza;
import theYakuza.items.BatItem;

import static theYakuza.YakuzaMod.makeCardPath;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class YakuzaGrabBat extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaGrabBat.class.getSimpleName());
    public static final String IMG = makeCardPath("Yakuza_Grab_Bat.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 1;
    private static final int DURABILITY = 3;

    // /STAT DECLARATION/

    public YakuzaGrabBat(int timesUpgraded) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        durability = durabilityBase = DURABILITY;
        this.timesUpgraded = timesUpgraded;
    }

    public YakuzaGrabBat() {
        this(0);
    }

    public int timesUpgradeDeck() {
        ArrayList<AbstractCard> draw = AbstractDungeon.player.drawPile.group;
        ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
        ArrayList<AbstractCard> disc = AbstractDungeon.player.discardPile.group;

        ArrayList<AbstractCard> currentDeck = new ArrayList<AbstractCard>();
        currentDeck.addAll(draw);
        currentDeck.addAll(hand);
        currentDeck.addAll(disc);

        int count = 0;
        for (AbstractCard c : currentDeck) {
            for (AbstractCard grab : YakuzaCardCollections.GrabCards.group) {
                if (c.cardID.equals(grab.cardID)) {
                    count++;
                }
            }
        }
        return count;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager
                .addToBottom(new GrabAction(new BatItem(this.timesUpgraded + timesUpgradeDeck(), durability)));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new YakuzaGrabBat(this.timesUpgraded);
    }

    @Override
    public void atTurnStart() {
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            int modifier = timesUpgradeDeck();

            this.timesUpgraded += modifier;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;

            rawDescription = UPGRADE_DESCRIPTION;

            super.initializeDescription();

            this.timesUpgraded -= modifier;
        } else {
            super.initializeDescription();
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
