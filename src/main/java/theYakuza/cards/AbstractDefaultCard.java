package theYakuza.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import theYakuza.powers.EssenceOfDurableWeaponsPower;
import theYakuza.powers.HeatLevelPower;

public abstract class AbstractDefaultCard extends CustomCard {

    public int defaultSecondMagicNumber; // Just like magic number, or any number for that matter, we want our regular,
                                         // modifiable stat
    public int defaultBaseSecondMagicNumber; // And our base stat - the number in it's base state. It will reset to that
                                             // by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or
                                                       // not, for coloring purposes. (red/green)

    public int durability;
    public int durabilityBase;
    public boolean upgradedDurability;
    public boolean isDurabilityModified;

    public int baseHeatCost;
    public int heatCost;
    private boolean upgradedHeatCost;
    public int itemUpgrades;
    public boolean isItemUpgraded;

    public AbstractDefaultCard(final String id,
            final String name,
            final String img,
            final int cost,
            final String rawDescription,
            final CardType type,
            final CardColor color,
            final CardRarity rarity,
            final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
        isItemUpgraded = false;
        upgradedDurability = false;
        isDurabilityModified = false;
        upgradedHeatCost = false;
        heatCost = baseHeatCost = 0;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat,
                                                                     // the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number
                                                       // is being changed.
        }
        if (upgradedDurability) {
            durability = durabilityBase;
            isDurabilityModified = true;
        }
        if (upgradedHeatCost) {
            heatCost = baseHeatCost;
        }

    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note
                                                              // "upgrade" and NOT "upgraded" - 2 different things. One
                                                              // is a boolean, and then this one is what you will
                                                              // usually use - change the integer by how much you want
                                                              // to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeItem(int amount) {
        itemUpgrades += amount;
        isItemUpgraded = true;
    }

    public void upgradeDurability(int amount) {
        durabilityBase += amount;
        durability = durabilityBase;
        upgradedDurability = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(EssenceOfDurableWeaponsPower.POWER_ID)) {
            durability = durabilityBase + AbstractDungeon.player.getPower(EssenceOfDurableWeaponsPower.POWER_ID).amount;
            isDurabilityModified = true;
        } else {
            durability = durabilityBase;
            isDurabilityModified = false;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (heatCost > 0) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            if (AbstractDungeon.player.hasPower(HeatLevelPower.POWER_ID)
                    && AbstractDungeon.player.getPower(HeatLevelPower.POWER_ID).amount >= heatCost) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            super.triggerOnGlowCheck();
        }
    }

    @Override
    public void onMoveToDiscard() {
        heatCost = baseHeatCost;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (heatCost > 0
                && (!p.hasPower(HeatLevelPower.POWER_ID) || p.getPower(HeatLevelPower.POWER_ID).amount < heatCost)) {
            this.cantUseMessage = "Not enough Heat Levels.";
            return false;
        } else {
            return canUse;
        }
    }

    public void upgradeHeatCost(int newBaseCost) {
        int diff = baseHeatCost - heatCost;
        baseHeatCost = newBaseCost;
        if (heatCost > 0) {
            heatCost = cost + diff;
        }

        if (heatCost < 0) {
            heatCost = 0;
        }

        upgradedHeatCost = true;
    }

    public void disableHeatCost() {
        heatCost = 0;
    };

    public void enableHeatCost() {
        heatCost = baseHeatCost;
    };

}