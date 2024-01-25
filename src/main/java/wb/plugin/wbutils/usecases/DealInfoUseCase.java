package wb.plugin.wbutils.usecases;

import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.commands.DealInfoResult;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DealInfoUseCase {

    private static final Logger LOGGER = Logger.getLogger(DealInfoUseCase.class.getName());
    private final DealsRepository databaseDeals;

    public DealInfoUseCase(final DealsRepository databaseDeals) {
        this.databaseDeals = databaseDeals;
    }

    public @NotNull CompletableFuture<Stream<String>> getDealIds(final Predicate<String> filter) {
        return CompletableFuture.supplyAsync(() ->
                IntStream.rangeClosed(1, databaseDeals.getDealsQuantity())
                        .mapToObj(Integer::toString)
                        .filter(filter));
    }

    public DealInfoResult execute(final String senderName, final String[] args) {
        if (args.length != 3) {
            return DealInfoResult.INVALID_INPUT;
        }

        int dealId = Integer.parseInt(args[0].replaceAll("\\D+", ""));
        if (dealId <= 0 || dealId > databaseDeals.getDealsQuantity()) {
            return DealInfoResult.INVALID_INPUT;
        }

        Deal deal = databaseDeals.getDeal(dealId);
        if (deal == null) {
            return DealInfoResult.DEAL_NOT_FOUND;
        }

        String playerName = args[2];
        String amount = playerName.replaceAll("[^\\d-]", "");
        if (amount.equals("-")) {
            amount = "0";
        }

        final String changeTarget = args[1];
        return switch (changeTarget) {
            case "owner" -> handleOwner(senderName, deal, playerName);
            case "coins_gold" -> handleGoldCoins(senderName, deal, amount);
            case "coins_silver" -> handleSilverCoins(senderName, deal, amount);
            case "coins_copper" -> handleCopperCoins(senderName, deal, amount);
            case "materials" -> handleMaterials(senderName, deal, amount);
            default -> DealInfoResult.UNKNOWN_STAT;
        };
    }

    private DealInfoResult handleOwner(final String senderName, final Deal deal, final String playerName) {
        final int dealId = deal.getId();
        LOGGER.info(() -> String.format("[%s] [DEAL] [SET] [number:%d] [to:%s] [from:%s]", senderName, dealId, playerName, deal.getOwner()));
        Deal newDeal = new Deal(dealId, playerName, deal.getCopperCoins(), deal.getSilverCoins(), deal.getGoldCoins(), deal.getMaterials());
        System.out.println(newDeal);
        databaseDeals.setDeal(dealId, newDeal);
        if (playerName.equals("-")) {
            return DealInfoResult.DEAL_OWNER_RESET;
        }
        return DealInfoResult.OWNER_CHANGED;
    }

    private DealInfoResult handleGoldCoins(final String senderName, final Deal deal, final String amount) {
        final int dealId = deal.getId();
        logDealModification(senderName, amount, dealId, "coins_gold", deal.getGoldCoins());
        Deal newDeal = new Deal(deal.getId(), deal.getOwner(), deal.getCopperCoins(), deal.getSilverCoins(), amount, deal.getMaterials());
        databaseDeals.setDeal(dealId, newDeal);
        return DealInfoResult.GOLD_COINS_CHANGED;
    }

    private DealInfoResult handleSilverCoins(final String senderName, final Deal deal, final String amount) {
        final int dealId = deal.getId();
        logDealModification(senderName, amount, dealId, "coins_silver", deal.getSilverCoins());

        Deal newDeal = new Deal(deal.getId(), deal.getOwner(), deal.getCopperCoins(), amount, deal.getGoldCoins(), deal.getMaterials());
        databaseDeals.setDeal(dealId, newDeal);
        return DealInfoResult.SILVER_COINS_CHANGED;
    }

    private DealInfoResult handleCopperCoins(final String senderName, final Deal deal, final String amount) {
        final int dealId = deal.getId();
        logDealModification(senderName, amount, dealId, "coins_copper", deal.getCopperCoins());

        Deal newDeal = new Deal(deal.getId(), deal.getOwner(), amount, deal.getSilverCoins(), deal.getGoldCoins(), deal.getMaterials());
        databaseDeals.setDeal(dealId, newDeal);
        return DealInfoResult.COPPER_COINS_CHANGED;
    }

    private DealInfoResult handleMaterials(final String senderName, final Deal deal, final String amount) {
        final int dealId = deal.getId();
        logDealModification(senderName, amount, dealId, "materials", deal.getMaterials());

        Deal newDeal = new Deal(deal.getId(), deal.getOwner(), deal.getCopperCoins(), deal.getSilverCoins(), deal.getGoldCoins(), amount);
        databaseDeals.setDeal(dealId, newDeal);
        return DealInfoResult.MATERIALS_CHANGED;
    }

    private static void logDealModification(final String senderName, final String amount, final int dealId,
                                            final String changeTarget, final String oldValue) {
        LOGGER.info(() -> String.format("[%s] [DEAL] [SET] [number:%d] [%s:%s] [previous:%s]", senderName, dealId, changeTarget, amount, oldValue));
    }
}
