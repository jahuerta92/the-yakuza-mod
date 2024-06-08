package theYakuza.relics.badgeRelics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BadgeRelicCollection {
    private static ArrayList<String> badgeRelics = new ArrayList<String>();
    private static ArrayList<String> seenRelics = new ArrayList<String>();

    public static BadgeRelicCollection badgeCollection = new BadgeRelicCollection();

    private BadgeRelicCollection() {
        // for (String s : seenRelics) {
        // System.out.println(s);
        // }
        // seenRelics = new ArrayList<String>();
        // badgeRelics = new ArrayList<String>();
        badgeRelics.add(DojimaBadgeRelic.ID);
        badgeRelics.add(OmiBadgeRelic.ID);
        badgeRelics.add(ArakawaBadgeRelic.ID);
        badgeRelics.add(BlackMondayBadgeRelic.ID);
        badgeRelics.add(BleachBadgeRelic.ID);
        badgeRelics.add(DojimaBadgeRelic.ID);
        badgeRelics.add(GeomijulBadgeRelic.ID);
        badgeRelics.add(HiroshiBadgeRelic.ID);
        badgeRelics.add(JingweonBadgeRelic.ID);
        badgeRelics.add(KazamaBadgeRelic.ID);
        badgeRelics.add(MajimaBadgeRelic.ID);
        // badgeRelics.add(NishikiyamaBadgeRelic.ID);
        badgeRelics.add(RyudoBadgeRelic.ID);
        badgeRelics.add(SaioBadgeRelic.ID);
        badgeRelics.add(ShibusawaBadgeRelic.ID);
        badgeRelics.add(ShimanoBadgeRelic.ID);
        // badgeRelics.add(SnakeFlowerBadgeRelic.ID);
        badgeRelics.add(SomeyaBadgeRelic.ID);
        badgeRelics.add(UenoSeiwaBadgeRelic.ID);
        badgeRelics.add(YomeiBadgeRelic.ID);
        badgeRelics.add(AmonBadgeRelic.ID);
    }

    public String getRandomUnseenBadgeKey() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<String> eligibleRelics = new ArrayList<String>();

        for (String s : badgeRelics) {
            if (!p.hasRelic(s) && !isSeen(s)) {
                eligibleRelics.add(s);
            }
        }

        if (!eligibleRelics.isEmpty()) {
            int chosenRelic = AbstractDungeon.relicRng.random(eligibleRelics.size() - 1);
            String chosenRelicStr = eligibleRelics.get(chosenRelic);
            seenRelics.add(chosenRelicStr);
            return chosenRelicStr;
        } else {
            return "Circlet";
        }
    }

    private boolean isSeen(String r) {
        for (String s : seenRelics) {
            if (s.equals(r))
                return true;
        }
        return false;
    }

    public int getPlayerBadges() {
        AbstractPlayer p = AbstractDungeon.player;

        int i = 0;
        for (String s : badgeRelics) {
            if (p.hasRelic(s)) {
                i++;
            }
        }
        return i;

    }

    public void reset() {
        seenRelics.removeAll(seenRelics);
    }
}
