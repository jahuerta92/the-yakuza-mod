package theYakuza.monsters;

import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomMonster;
import theYakuza.YakuzaMod;
import theYakuza.powers.MadDogPower;
import theYakuza.powers.ShadowClonesPower;
import theYakuza.powers.TemporalConfusionPower;
import theYakuza.powers.WeavePower;

import static theYakuza.YakuzaMod.makeMonsterPath;

public class Majima extends CustomMonster {

    public static final String ID = YakuzaMod.makeID("Majima");
    private static final MonsterStrings MONSTER_STRINGS = CardCrawlGame.languagePack.getMonsterStrings(ID);

    private static final String NAME = MONSTER_STRINGS.NAME;
    private static final int MAX_HEALTH = 300;
    private static final float HB_X = 15.0F;
    private static final float HB_Y = 30.0F;
    private static final float HB_W = 180.0F;
    private static final float HB_H = 380.0F;
    private static final float OFFSET_X = 60.0F;
    private static final float OFFSET_Y = -25.0F; // 135.0F

    private static final String IMGURL = makeMonsterPath("majima/majima.png");
    private static final float SPAWN_X = -150.0F;

    private static final int CHANGE_INTENTION_COUNT = 2;
    private static final int BASE_STRONG_COUNT = 5;
    private static final int BASE_STRONG_MULTI = 4;
    private static final int BASE_WEAK_COUNT = 4;
    private static final int BASE_WEAK_MULTI = 3;
    private static final int BASE_BUFF_AMOUNT = 3;

    private int finalHealth;
    private int secondPhaseHealth;
    private boolean firstMove;
    private boolean ascensionHigh;
    private HashMap<Integer, AbstractMonster> enemySlots;
    private int nCopies;

    public Majima() {
        super(NAME, ID, MAX_HEALTH, HB_X, HB_Y, HB_W, HB_H, IMGURL, OFFSET_X, OFFSET_Y);
        this.ascensionHigh = AbstractDungeon.ascensionLevel >= 19;
        this.nCopies = ascensionHigh ? 3 : 2;
        this.finalHealth = AbstractDungeon.ascensionLevel >= 8 ? (int) (MAX_HEALTH * 1.1) : MAX_HEALTH;
        this.secondPhaseHealth = this.finalHealth / 3;
        this.firstMove = true;
        this.setHp(this.finalHealth, this.finalHealth);
        this.damage.add(new DamageInfo(this, BASE_STRONG_COUNT));
        this.damage.add(new DamageInfo(this, BASE_WEAK_COUNT));

        this.enemySlots = new HashMap<Integer, AbstractMonster>();
    }

    public void usePreBattleAction() {
        // CardCrawlGame.music.unsilenceBGM();
        // AbstractDungeon.scene.fadeOutAmbiance();
        // AbstractDungeon.getCurrRoom().playBgmInstantly("");

        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(this, this, new MadDogPower(this, CHANGE_INTENTION_COUNT)));
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(this, this, new ShadowClonesPower(this, this.secondPhaseHealth)));

    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToTop(new SFXAction("YAKUZA_MAJIMA_SOUND_1"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToTop(new SFXAction("YAKUZA_MAJIMA_SOUND_2"));
        } else {
            AbstractDungeon.actionManager.addToTop(new SFXAction("YAKUZA_MAJIMA_SOUND_3"));
        }
    }

    private void shout() {
        int roll = MathUtils.random(4);
        AbstractDungeon.actionManager.addToTop(new ShoutAction(this, MONSTER_STRINGS.DIALOG[roll], 1.7F, 1.7F));
    }

    private AttackEffect rollEffect() {
        int roll = MathUtils.random(4);
        AttackEffect chosenEffect;
        switch (roll) {
            case 0:
                chosenEffect = AttackEffect.SLASH_DIAGONAL;
                break;
            case 1:
                chosenEffect = AttackEffect.SLASH_HEAVY;
                break;
            case 2:
                chosenEffect = AttackEffect.SLASH_HORIZONTAL;
                break;
            default:
                chosenEffect = AttackEffect.SLASH_VERTICAL;
                break;
        }

        return chosenEffect;
    }

    @Override
    protected void getMove(int move) {
        if (this.firstMove || move <= 33) {
            this.firstMove = false;
            this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base, BASE_STRONG_MULTI, true);
        } else if (move <= 66) {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base,
                    BASE_WEAK_MULTI, true);
        } else if (move <= 100) {
            this.setMove((byte) 3, Intent.ATTACK_BUFF, ((DamageInfo) this.damage.get(1)).base,
                    BASE_WEAK_MULTI, true);
        }
        this.createIntent();
    }

    @Override
    public void takeTurn() {
        this.playSfx();
        this.shout();
        AbstractCreature p = AbstractDungeon.player;

        switch (this.nextMove) {
            case 1:
                for (int i = 0; i < BASE_STRONG_MULTI; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo) this.damage.get(0),
                            this.rollEffect(), true));
                }

                break;
            case 2:
                for (int i = 0; i < BASE_WEAK_MULTI; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo) this.damage.get(1),
                            this.rollEffect(), true));
                }
                int turns = 1;
                if (this.ascensionHigh) {
                    turns += 1;
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new TemporalConfusionPower(AbstractDungeon.player, this, turns)));
                break;
            case 3:
                for (int i = 0; i < BASE_WEAK_MULTI; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo) this.damage.get(1),
                            this.rollEffect(), true));
                }

                int amount = BASE_BUFF_AMOUNT;
                if (this.ascensionHigh) {
                    amount += 1;
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
                        new WeavePower(this, this, amount)));
                break;

        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));

    }

    public void enterSecondPhase() {
        AbstractMonster newMonster;
        for (int key = 1; key <= this.nCopies; ++key) {
            System.out.println("New Shadow Clone");
            newMonster = new MajimaClone(SPAWN_X + -200.0F * (float) key, MathUtils.random(-20.0F, 30.0F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_SUMMON"));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(newMonster, true));
            this.enemySlots.put(key, newMonster);
        }
    }

}
