package wb.plugin.wbutils.adapters;

import java.util.ArrayList;

public class DatabaseDeals implements IDatabaseDeals {

    private final int DEALS_QUANTITY = 25;

    private final ArrayList<Integer> id = new ArrayList<>(DEALS_QUANTITY);
    private final ArrayList<String> owner = new ArrayList<>(DEALS_QUANTITY);
    private final ArrayList<String> coins_copper = new ArrayList<>(DEALS_QUANTITY);
    private final ArrayList<String> coins_silver = new ArrayList<>(DEALS_QUANTITY);
    private final ArrayList<String> coins_gold = new ArrayList<>(DEALS_QUANTITY);
    private final ArrayList<String> materials = new ArrayList<>(DEALS_QUANTITY);

    /**
     * Adds deal's statistics to local data storage.
     *
     * @param dealId       ID of a deal to set stats for.
     * @param owner        Set owner of a deal.
     * @param coins_copper Set copper coins amount.
     * @param coins_silver Set silver coins amount.
     * @param coins_gold   Set gold coins amount.
     * @param materials    Set materials amount.
     */
    public void addDealInfo(int dealId, String owner, String coins_copper,String coins_silver, String coins_gold, String materials) {
        id.add(dealId);
        this.owner.add(owner);
        this.coins_copper.add(coins_copper);
        this.coins_silver.add(coins_silver);
        this.coins_gold.add(coins_gold);
        this.materials.add(materials);
    }

    public int getDealsQuantity() {
        return DEALS_QUANTITY;
    }

    public String getOwner(int dealId) {
        return owner.get(dealId - 1);
    }

    public String getCoinsCopper(int dealId) {
        return coins_copper.get(dealId - 1);
    }

    public String getCoinsSilver(int dealId) {
        return coins_silver.get(dealId - 1);
    }

    public String getCoinsGold(int dealId) {
        return coins_gold.get(dealId - 1);
    }

    public String getMaterials(int dealId) {
        return materials.get(dealId - 1);
    }

    public void setOwner(int dealId, String newOwner) {
        owner.set(dealId - 1, newOwner);
    }

    public void setCoinsCopper(int dealId, String newQuantity) {
        coins_copper.set(dealId - 1, newQuantity);
    }

    public void setCoinsSilver(int dealId, String newQuantity) {
        coins_silver.set(dealId - 1, newQuantity);
    }

    public void setCoinsGold(int dealId, String newQuantity) {
        coins_gold.set(dealId - 1, newQuantity);
    }

    public void setMaterials(int dealId, String newQuantity) {
        materials.set(dealId - 1, newQuantity);
    }
}
