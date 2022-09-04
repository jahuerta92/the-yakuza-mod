package theYakuza.relics.badgeRelics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BadgeRelicCollection {
    private static ArrayList<String> badgeRelics = new ArrayList<String>();

    public static BadgeRelicCollection badgeCollection = new BadgeRelicCollection();

    private BadgeRelicCollection() {
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
        badgeRelics.add(NishikiyamaBadgeRelic.ID);
        badgeRelics.add(RyudoBadgeRelic.ID);
        badgeRelics.add(SaioBadgeRelic.ID);
        badgeRelics.add(ShibusawaBadgeRelic.ID);
        badgeRelics.add(ShimanoBadgeRelic.ID);
        badgeRelics.add(SnakeFlowerBadgeRelic.ID);
        badgeRelics.add(SomeyaBadgeRelic.ID);
        badgeRelics.add(UenoSeiwaBadgeRelic.ID);
        badgeRelics.add(YomeiBadgeRelic.ID);
        badgeRelics.add(AmonBadgeRelic.ID);

    }

    public String getRandomUnseenBadgeKey() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<String> eligibleRelics = new ArrayList<String>();

        for (String s : badgeRelics) {
            if (!p.hasRelic(s)) {
                eligibleRelics.add(s);
            }
        }

        if (!eligibleRelics.isEmpty()) {
            int chosenRelic = AbstractDungeon.relicRng.random(eligibleRelics.size() - 1);
            return eligibleRelics.get(chosenRelic);
        } else {
            return "Circlet";
        }
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
}
