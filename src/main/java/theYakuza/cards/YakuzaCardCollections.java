package theYakuza.cards;

import com.megacrit.cardcrawl.cards.CardGroup;
import theYakuza.cards.CommonAttacks.*;
import theYakuza.cards.UncommonAttacks.*;
import theYakuza.cards.UncommonSkills.*;
import theYakuza.cards.CommonSkills.*;
import theYakuza.cards.RareSkills.YakuzaEssenceOfSwitchGrab;
import theYakuza.cards.TemporaryCards.YakuzaGrabBrokenPipe;

public class YakuzaCardCollections {
        public static CardGroup HeatLevelCards = new CardGroup(
                        com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        public static CardGroup GrabCards = new CardGroup(
                        com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        public static CardGroup MinigameCards = new CardGroup(
                        com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);

        public static YakuzaCardCollections cardCollections = new YakuzaCardCollections();

        private YakuzaCardCollections() {
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaHeatStrike());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfSweepKick());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfComboRush());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfSlapping());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfSwiftStrikes());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfTaunt());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfHeatPunches());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfDropkick());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfHitAndRun());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfFollowUp());
                /*
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaBrawlerStyle());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new
                 * YakuzaEssenceOfUltimatePower());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new
                 * YakuzaEssenceOfEssenceOfNoMercy());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new
                 * YakuzaEssenceOfEssenceOfTheIronFist());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaStaminanRoyale());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaColosseum());
                 * YakuzaCardCollections.HeatLevelCards.addToBottom(new Yakuza4thChairman());
                 */

                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabTrafficCone());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabNunchaku());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabPan());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabNamelessKatana());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaEssenceOfSwitchGrab());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabSake());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabRevolver());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabBike());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabBrokenPipe());
                /*
                 * YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabStaminanRoyale());
                 * YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabMotorcycle());
                 * YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabDemonfireDagger());
                 */

                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBaseball());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBowling());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaPoker());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaShogi());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaDarts());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaFishing());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaTelephoneClub());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaTaxiDriver());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaPubCrawl());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaCabaretClub());
                /*
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaMahjong());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBoxing());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaDragonKart());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBakaMitai());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaRealState());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaColosseum());
                 * YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaFridayNight());
                 */

        }
}
