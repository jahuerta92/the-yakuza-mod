package theYakuza.events;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import theYakuza.YakuzaMod;
import theYakuza.monsters.Majima;

public class MajimaEvent extends AbstractEvent {

    public static final String ID = YakuzaMod.makeID("MajimaEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    // private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public MajimaEvent() {
        super();
        AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;
        this.body = DESCRIPTIONS[0];
        this.roomEventText.addDialogOption(OPTIONS[0]);
        this.roomEventText.addDialogOption(OPTIONS[1]);
        this.hasDialog = true;
        this.hasFocus = true;
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (i) {
            case 0:
                if (this.screenNum == 0) {

                    AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Majima());// MonsterHelper.getEncounter("Majima");
                    AbstractEvent.logMetric("Majima", "Fought Majima");
                    AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelic(RelicTier.BOSS));

                    this.enterCombat();
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("YAKUZA_MAJIMA_LAUGH"));
                    AbstractDungeon.lastCombatMetricKey = "Majima";
                } else if (this.screenNum == 1) {
                    this.openMap();
                }
                return;
            case 1:
                this.openMap();
                return;
        }

    }

}
