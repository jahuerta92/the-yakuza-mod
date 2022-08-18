package theYakuza.cards.RareAttacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import theYakuza.DefaultMod;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheDefault;

import static theYakuza.DefaultMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to
// https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what
// it does.
public class YakuzaEssenceOfNoMercy extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaEssenceOfNoMercy.class.getSimpleName()); // USE THIS
                                                                                                     // ONE
    // FOR THE
    // TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG =
                                                                // makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the
    // card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; // Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
    private static final CardType TYPE = CardType.ATTACK; //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1; // COST = ${COST}

    private static final int MAGIC = 4;
    private static final int UPGRADED_MAGIC = -2;

    // /STAT DECLARATION/
    public YakuzaEssenceOfNoMercy() { // public ${NAME}() - This one and the one right under the imports are the
                                      // most
        // important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = 0;
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int trueDamage = (int) Math.floor((m.maxHealth - m.currentHealth) / magicNumber);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, trueDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));

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
