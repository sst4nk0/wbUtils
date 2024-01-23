package wb.plugin.wbutils.adapters;

import wb.plugin.wbutils.io.DatabaseConnectionManager;
import wb.plugin.wbutils.entities.Deal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DealsRepositoryImpl implements DealsRepository {

    private static final Logger LOGGER = Logger.getLogger(DealsRepositoryImpl.class.getName());
    private static final int DEALS_QUANTITY = 25;
    private final DatabaseConnectionManager dbConnectionManager;
    private final List<Deal> deals = new ArrayList<>(DEALS_QUANTITY);

    public DealsRepositoryImpl(final DatabaseConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        loadDealsInfo();
    }

    public void addDeal(final Deal deal) {
        deals.add(deal);
    }

    public Deal getDeal(final int dealId) {
        return deals.get(dealId - 1);
    }

    public void setDeal(final int dealId, final Deal deal) {
        deals.set(dealId - 1, deal);
    }

    public int getDealsQuantity() {
        return DEALS_QUANTITY;
    }

    public void saveDealsInfo() {
        final String updateQuery = "UPDATE `wb_deals` SET `owner` = ?, `coins_copper` = ?, `coins_silver` = ?, `coins_gold` = ?, `materials` = ? WHERE `id` = ?";

        try (final Connection connection = dbConnectionManager.getConnectionAsync().join();
             final PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            connection.setAutoCommit(false);

            for (int i = 1; i < getDealsQuantity() + 1; i++) {
                final Deal deal = getDeal(i);
                stmt.setString(1, deal.owner());
                stmt.setString(2, deal.coins_copper());
                stmt.setString(3, deal.coins_silver());
                stmt.setString(4, deal.coins_gold());
                stmt.setString(5, deal.materials());
                stmt.setInt(6, i);
                stmt.addBatch();
            }
            stmt.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save deals info", e);
        }
    }

    public void loadDealsInfo() {
        final String query = "SELECT * FROM wb_deals";
        try (final Connection connection = dbConnectionManager.getConnectionAsync().join();
             final PreparedStatement stmt = connection.prepareStatement(query);
             final ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                final Deal deal = new Deal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6));
                addDeal(deal);
            }
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load deals info", e);
        }
    }
}
