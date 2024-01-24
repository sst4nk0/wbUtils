package wb.plugin.wbutils.adapters.repositories;

import wb.plugin.wbutils.frameworks.DatabaseConnectionManager;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.utilities.repository.AsyncRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DealsRepositoryImpl extends AsyncRepositoryImpl<Deal> implements DealsRepository {

    private static final Logger LOGGER = Logger.getLogger(DealsRepositoryImpl.class.getName());
    private static final int DEALS_QUANTITY = 25;
    private static final String SAVE_QUERY = "INSERT INTO wb_deals (id, owner, coins_copper, coins_silver, coins_gold, materials) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE wb_deals SET owner = ?, coins_copper = ?, coins_silver = ?, coins_gold = ?, materials = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM wb_deals WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM wb_deals WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM wb_deals";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM wb_deals";
    private List<Deal> deals = new ArrayList<>(DEALS_QUANTITY);

    public DealsRepositoryImpl(DatabaseConnectionManager connectionManager) {
        super(connectionManager, Deal.class, SAVE_QUERY, UPDATE_QUERY, DELETE_QUERY, SELECT_QUERY, SELECT_ALL_QUERY, COUNT_QUERY);
        loadDealsInfo();
    }

    public void addDeal(final Deal deal) {
        save(deal).join();
    }

    public Deal getDeal(final int dealId) {
        return findById(dealId).join().orElse(null);
    }

    public void setDeal(final int dealId, final Deal deal) {
        save(deal).join();
    }

    public int getDealsQuantity() {
        return DEALS_QUANTITY;
    }

    public void saveDealsInfo() {
        saveAll(deals).join();
    }

    public void loadDealsInfo() {
        deals = findAll().join();
    }
}
