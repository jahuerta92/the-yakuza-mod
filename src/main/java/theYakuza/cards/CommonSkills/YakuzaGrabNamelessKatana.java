package theYakuza.cards.CommonSkills;

import theYakuza.YakuzaMod;
import theYakuza.actions.GrabAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.items.NamelessKatanaItem;

import static theYakuza.YakuzaMod.makeCardPath;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YakuzaGrabNamelessKatana extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaGrabNamelessKatana.class.getSimpleName());
    public static final String IMG = makeCardPath("Yakuza_Grab_Nameless_Katana.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 1;
    private static final int DURABILITY = 3;

    private static final int UPGRADE_VAL = 1;

    // /STAT DECLARATION/

    public YakuzaGrabNamelessKatana() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        durability = durabilityBase = DURABILITY;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager
                .addToBottom(new GrabAction(new NamelessKatanaItem(this.itemUpgrades, durability)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeItem(UPGRADE_VAL);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
