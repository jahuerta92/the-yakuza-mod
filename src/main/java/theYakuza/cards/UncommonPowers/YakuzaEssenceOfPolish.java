package theYakuza.cards.UncommonPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theYakuza.YakuzaMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.powers.EssenceOfPolishPower;

import static theYakuza.YakuzaMod.makeCardPath;

public class YakuzaEssenceOfPolish extends AbstractDynamicCard {

    public static final String ID = YakuzaMod.makeID(YakuzaEssenceOfPolish.class.getSimpleName());
    public static final String IMG = makeCardPath("Yakuza_Essence_Of_Polish.png");// "public static final String IMG =
    // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
    private static final CardType TYPE = CardType.POWER; //
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 1; // COST = ${COST}

    private static final int MAGIC = 1; // DAMAGE = ${DAMAGE}
    private static final int UPGRADED_MAGIC = 1; // DAMAGE = ${DAMAGE}

    // /STAT DECLARATION/

    public YakuzaEssenceOfPolish() { // public ${NAME}() - This one and the one right under the imports are the
                                     // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("CARD_UPGRADE");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new EssenceOfPolishPower(p, p, magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
