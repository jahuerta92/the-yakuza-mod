package theYakuza.cards.RarePowers;

import theYakuza.YakuzaMod;
import theYakuza.actions.MinigameAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheYakuza;
import theYakuza.powers.RealEstatePower;

import static theYakuza.YakuzaMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YakuzaRealEstate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = YakuzaMod.makeID(YakuzaRealEstate.class.getSimpleName());
    public static final String IMG = makeCardPath("Yakuza_Real_Estate.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheYakuza.Enums.COLOR_YAKUZA;

    private static final int COST = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/

    public YakuzaRealEstate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        // this.tags.add(CardTags.STARTER_DEFEND); // Tag your strike, defend and form
        // (Wraith form, Demon form, Echo form,
        // etc.) cards so that they function correctly.
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MinigameAction(p));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new RealEstatePower(p, p, magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
