package theYakuza.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import basemod.abstracts.CustomMonster;
import theYakuza.YakuzaMod;
import theYakuza.powers.MadDogPower;
import theYakuza.powers.TemporalConfusionPower;
import theYakuza.powers.WeavePower;

import static theYakuza.YakuzaMod.makeMonsterPath;

public class MajimaClone extends CustomMonster {

    public static final String ID = YakuzaMod.makeID("MajimaClone");
    private static final MonsterStrings MONSTER_STRINGS = CardCrawlGame.languagePack.getMonsterStrings(Majima.ID);

    private static final String NAME = MONSTER_STRINGS.NAME;
    private static final int MAX_HEALTH = 150;
    private static final float HB_X = 15.0F;
    private static final float HB_Y = 20.0F;
    private static final float HB_W = 180.0F;
    private static final float HB_H = 380.0F;

    private static final String IMGURL = makeMonsterPath("majima/majimaClone.png");

    private static final int CHANGE_INTENTION_COUNT = 3;
    private static final int BASE_STRONG_COUNT = 3;
    private static final int BASE_STRONG_MULTI = 3;
    private static final int BASE_WEAK_COUNT = 2;
    private static final int BASE_WEAK_MULTI = 2;
    private static final int BASE_BUFF_AMOUNT = 1;

    private int finalHealth;
    private boolean firstMove = true;
    private boolean ascensionHigh;

    public MajimaClone(float x, float y) {
        super(NAME, ID, MAX_HEALTH, HB_X, HB_Y, HB_W, HB_H, IMGURL, x, y);
        this.ascensionHigh = AbstractDungeon.ascensionLevel >= 19;
        this.finalHealth = AbstractDungeon.ascensionLevel >= 8 ? (int) (MAX_HEALTH * 1.1) : MAX_HEALTH;
        this.firstMove = true;
        this.setHp(this.finalHealth, this.finalHealth);
        this.damage.add(new DamageInfo(this, BASE_STRONG_COUNT));
        this.damage.add(new DamageInfo(this, BASE_WEAK_COUNT));

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(this, this, new MadDogPower(this, CHANGE_INTENTION_COUNT)));
    }

    private AttackEffect rollEffect() {
        int roll = MathUtils.random(3);
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
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte) 4, Intent.BUFF);
        } else if (move < 33) {
            this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base, BASE_STRONG_MULTI, true);
        } else if (move < 66) {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base,
                    BASE_WEAK_MULTI, true);
        } else {
            this.setMove((byte) 3, Intent.ATTACK_BUFF, ((DamageInfo) this.damage.get(1)).base,
                    BASE_WEAK_MULTI, true);
        }
        this.createIntent();
    }

    @Override
    public void takeTurn() {
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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new TemporalConfusionPower(AbstractDungeon.player, this, 1)));
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
            case 4:
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(this, this, new MadDogPower(this, CHANGE_INTENTION_COUNT)));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));

    }
}
