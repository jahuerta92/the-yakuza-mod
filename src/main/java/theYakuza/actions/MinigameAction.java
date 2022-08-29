package theYakuza.actions;

import java.util.ArrayList;
import java.util.Collections;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.ExplosivePotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ChokePower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import theYakuza.cards.YakuzaCardCollections;
import theYakuza.powers.AbstractMinigamePower;
import theYakuza.powers.FridayNightPower;
import theYakuza.powers.RealEstatePower;
import theYakuza.powers.RetaliatePower;
import theYakuza.powers.WeavePower;
import theYakuza.relics.CustomMinigameRelic;
import theYakuza.relics.NuggetRelic;

public class MinigameAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int effectiveness;
    private int repeats;
    private int nEffects;
    private int maxEffects;

    private int BASE_EFFECTS = 10;
    private int FRIDAY_EFFECTS = 10;
    private int BASE_BLOCK = 3;
    private int BASE_DAMAGE = 5;
    private int BASE_DRAW = 1;
    private int BASE_DEBUFF = 1;
    private int BASE_BUFF = 1;
    private int BASE_VIGOR = 3;
    private int BASE_ENERGIZE = 1;
    private int BASE_REDUCTION = 1;
    private int BASE_TEMPHP = 1;
    private int BASE_METAL = 1;
    private int BASE_THORNS = 2;
    private int BASE_RETALIATE = 1;
    private int BASE_WEAVE = 1;
    private int BASE_ARTIFACT = 1;
    private int BASE_CHOKE = 1;

    public MinigameAction(final AbstractPlayer p) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        effectiveness = 1;
        repeats = 1;
        nEffects = 1;
        maxEffects = BASE_EFFECTS;
    }

    public MinigameAction(final AbstractPlayer p, final int effectiveness, final int repeats) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        this.effectiveness = effectiveness;
        this.repeats = repeats;
        nEffects = 1;
    }

    @Override
    public void update() {
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow.ID.equals(FridayNightPower.POWER_ID)) {
                maxEffects += FRIDAY_EFFECTS;
            }
            if (pow.ID.equals(RealEstatePower.POWER_ID)) {
                nEffects += p.getPower(RealEstatePower.POWER_ID).amount;
            }

        }

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.relicId.equals(NuggetRelic.ID)) {
                effectiveness *= 1.5;
            }
        }

        if (nEffects >= maxEffects) {
            nEffects = maxEffects;
        }

        for (int j = 0; j < repeats; j++) {
            ArrayList<Integer> allEffects = new ArrayList<Integer>();
            ArrayList<Integer> chosenEffects = new ArrayList<Integer>();

            for (AbstractPower pow : AbstractDungeon.player.powers) {
                if (pow instanceof AbstractMinigamePower) {
                    ((AbstractMinigamePower) pow).onMinigameActivation();
                }
            }

            for (AbstractRelic pow : AbstractDungeon.player.relics) {
                if (pow instanceof CustomMinigameRelic) {
                    ((CustomMinigameRelic) pow).onMinigameActivation();
                }
            }

            for (int i = 0; i < maxEffects; i++)
                allEffects.add(i);
            Collections.shuffle(allEffects);
            for (int i = 0; i < nEffects; i++)
                chosenEffects.add(allEffects.get(i));

            for (int v : chosenEffects) {
                if (v == 0) { // Block gain
                    AbstractDungeon.actionManager
                            .addToBottom(new GainBlockAction(p, p, BASE_BLOCK * effectiveness));

                } else if (v == 1) { // Flex and Dex
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p, new DexterityPower(p, BASE_BUFF * effectiveness)));
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p,
                                            new LoseDexterityPower(p, BASE_BUFF * effectiveness)));
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p, new StrengthPower(p, BASE_BUFF * effectiveness)));
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    new ApplyPowerAction(p, p,
                                            new LoseStrengthPower(p, BASE_BUFF * effectiveness)));

                } else if (v == 2) { // Draw card next turn
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, BASE_DRAW * effectiveness)));

                } else if (v == 3) { // Enemy loses health
                    AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
                            new DamageInfo(null, BASE_DAMAGE * effectiveness, DamageType.THORNS),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));

                } else if (v == 4) { // Draw a card
                    AbstractDungeon.actionManager
                            .addToBottom(new DrawCardAction(p, BASE_DRAW * effectiveness));

                } else if (v == 5) { // Weak and Vulnerable
                    if (!AbstractDungeon.getMonsters().monsters.isEmpty()) {
                        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster();
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,
                                p, new VulnerablePower(m, BASE_DEBUFF * effectiveness, false)));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,
                                p, new WeakPower(m, BASE_DEBUFF * effectiveness, false)));
                    }

                } else if (v == 6) { // Energize 1
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(p, p, new EnergizedPower(p, BASE_ENERGIZE * effectiveness)));

                } else if (v == 7) { // Add Vigor
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(p, p, new VigorPower(p, BASE_VIGOR * effectiveness)));
                } else if (v == 8) { // Add TempHP
                    AbstractDungeon.actionManager
                            .addToBottom(new AddTemporaryHPAction(p, p, BASE_TEMPHP * effectiveness));
                } else if (v == 9) { // Add random minigame common card with purge
                    for (int i = 0; i < effectiveness; i++) {
                        AbstractCard c = YakuzaCardCollections.MinigameCards.getRandomCard(
                                AbstractDungeon.cardRandomRng,
                                CardRarity.COMMON);
                        c.purgeOnUse = true;
                        c.rawDescription = "Purge. NL " + c.rawDescription;
                        AbstractDungeon.actionManager
                                .addToBottom(new BetterMakeTempCardInHandAction(c));
                    }
                } // IMPROVED LIST BY FRIDAY NIGHT
                else if (v == 10) { // Reduce card cost by 1
                    AbstractDungeon.actionManager
                            .addToBottom(new ReduceRandomNonZeroCostCardAction(effectiveness * BASE_REDUCTION));
                } else if (v == 11) { // Gain 1 plated armor and metallicize
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new MetallicizePower(p, effectiveness * BASE_METAL)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new PlatedArmorPower(p, effectiveness * BASE_METAL)));
                } else if (v == 12) { // Gain 2 thorns
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new ThornsPower(p, effectiveness * BASE_THORNS)));
                } else if (v == 13) { // Gain 1 Retaliate
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new RetaliatePower(effectiveness * BASE_RETALIATE)));
                } else if (v == 14) { // Gain 1 Weave
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new WeavePower(p, p, effectiveness * BASE_WEAVE)));
                } else if (v == 15) { // Upgrade a random card in your hand
                    for (int i = 0; i < effectiveness; i++) {
                        AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
                    }
                } else if (v == 16) { // Gain 1 Artifact
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new ArtifactPower(p, effectiveness * BASE_ARTIFACT)));
                } else if (v == 17) { // Add a random common card from ANY class to your hand with Purge and Ethereal
                    for (int i = 0; i < effectiveness; i++) {
                        AbstractCard c = CardLibrary.getAnyColorCard(CardRarity.COMMON);

                        c.purgeOnUse = true;
                        c.isEthereal = true;
                        c.rawDescription = "Purge. Ethereal. NL " + c.rawDescription;
                        AbstractDungeon.actionManager
                                .addToBottom(new BetterMakeTempCardInHandAction(c));
                    }
                } else if (v == 18) { // Obtain an Explosive potion
                    for (int i = 0; i < effectiveness; i++) {
                        AbstractDungeon.actionManager
                                .addToBottom(new ObtainPotionAction(new ExplosivePotion()));
                    }
                } else if (v == 19) { // A random enemy loses 1HP per card played this turn
                    if (!AbstractDungeon.getMonsters().monsters.isEmpty()) {
                        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster();
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(m, p, new ChokePower(m, effectiveness * BASE_CHOKE)));
                    }

                }

            }
        }
        isDone = true;
    }
}
