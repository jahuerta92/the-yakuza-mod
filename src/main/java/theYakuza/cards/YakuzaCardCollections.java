package theYakuza.cards;

import com.megacrit.cardcrawl.cards.CardGroup;
import theYakuza.cards.CommonAttacks.*;
import theYakuza.cards.UncommonAttacks.*;
import theYakuza.cards.UncommonPowers.*;
import theYakuza.cards.UncommonSkills.*;
import theYakuza.cards.CommonSkills.*;
import theYakuza.cards.RareAttacks.*;
import theYakuza.cards.RarePowers.*;
import theYakuza.cards.RareSkills.*;
import theYakuza.cards.TemporaryCards.*;

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
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaBrawlerStyle());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaKomakiParry());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaKomakiTigerDrop());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfNoMercy());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfIronFists());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaEssenceOfUltimatePower());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaGrabStaminanX());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new YakuzaColiseum());
                YakuzaCardCollections.HeatLevelCards.addToBottom(new Yakuza4thChairman());

                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabTrafficCone());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabNunchaku());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabPan());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabNamelessKatana());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaEssenceOfSwitchGrab());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabSake());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabRevolver());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabBike());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabBrokenPipe());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabSais());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabDragonKart());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabStaminanX());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabMotorcycle());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabDemonfireDagger());
                YakuzaCardCollections.GrabCards.addToBottom(new YakuzaGrabBat());

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
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaMahjong());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBoxing());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaGrabDragonKart());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaBakaMitai());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaRealEstate());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaColiseum());
                YakuzaCardCollections.MinigameCards.addToBottom(new YakuzaFridayNight());

        }
}
