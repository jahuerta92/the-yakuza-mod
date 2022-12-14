package theYakuza.cards.RareSkills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import theYakuza.YakuzaMod;
import theYakuza.actions.GrabAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.items.DemonfireDaggerItem;

import static theYakuza.YakuzaMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaGrabDemonfireDagger extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaGrabDemonfireDagger.class.getSimpleName()); // USE THIS
    // ONE
    // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Yakuza_Grab_Demonfire_Dagger.png");// "public static final String IMG
                                                                                      // =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.SKILL; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 2; // 1// COST = ${COST}

    private static final int DURABILITY = 99;
    private static final int UPGRADE_VAL = 1;

    // /STAT DECLARATION/
    public YakuzaGrabDemonfireDagger() { // public ${NAME}() - This one and the one right under the imports are the
        // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        durabilityBase = durability = DURABILITY;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("YAKUZA_MAJIMA_LAUGH");
        AbstractDungeon.actionManager
                .addToBottom(new GrabAction(new DemonfireDaggerItem(this.itemUpgrades, durability)));
    }

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
