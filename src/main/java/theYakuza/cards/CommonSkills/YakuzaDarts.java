package theYakuza.cards.CommonSkills;

import theYakuza.DefaultMod;
import theYakuza.actions.MinigameAction;
import theYakuza.cards.AbstractDynamicCard;
import theYakuza.characters.TheDefault;

import static theYakuza.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YakuzaDarts extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(YakuzaDarts.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    // /STAT DECLARATION/

    public YakuzaDarts() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        // this.tags.add(CardTags.STARTER_DEFEND); // Tag your strike, defend and form
        // (Wraith form, Demon form, Echo form,
        // etc.) cards so that they function correctly.
        baseBlock = block = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MinigameAction(p));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
