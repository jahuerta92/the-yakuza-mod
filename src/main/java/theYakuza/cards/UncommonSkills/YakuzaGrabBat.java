package theYakuza.cards.UncommonSkills;

import theYakuza.YakuzaMod;
import theYakuza.actions.GrabAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.items.BatItem;

import static theYakuza.YakuzaMod.makeCardPath;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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

    private static final int COST = 2;
    private static final int DURABILITY = 3;

    private static final int UPGRADE_VAL = 1;

    // /STAT DECLARATION/

    public YakuzaGrabBat(int timesUpgraded) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        durability = durabilityBase = DURABILITY;
        this.itemUpgrades = timesUpgraded;
    }

    public YakuzaGrabBat() {
        this(0);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager
                .addToBottom(new GrabAction(new BatItem(this.itemUpgrades, durability)));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new YakuzaGrabBat(this.itemUpgrades);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        upgradeItem(UPGRADE_VAL);
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.itemUpgrades;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
