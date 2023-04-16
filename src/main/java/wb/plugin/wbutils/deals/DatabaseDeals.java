package wb.plugin.wbutils.deals;

import java.util.ArrayList;

public class DatabaseDeals {

    private static int DEALS_QUANTITY = 25;

    private static ArrayList<Integer> id = new ArrayList<>(DEALS_QUANTITY);
    private static ArrayList<String> owner = new ArrayList<>(DEALS_QUANTITY);
    private static ArrayList<String> coins_copper = new ArrayList<>(DEALS_QUANTITY);
    private static ArrayList<String> coins_silver = new ArrayList<>(DEALS_QUANTITY);
    private static ArrayList<String> coins_gold = new ArrayList<>(DEALS_QUANTITY);
    private static ArrayList<String> materials = new ArrayList<>(DEALS_QUANTITY);



    /**
     * Adds deal's statistics to local data storrage.
     * @param dealId ID of deal to set stats for.
     * @param owner Set owner of deal.
     * @param coins_copper Set copper coins amount.
     * @param coins_silver Set silver coins amount.
     * @param coins_gold Set gold coins amount.
     * @param materials Set materils amount.
     */
    public static void addDealInfo(int dealId, String owner, String coins_copper, String coins_silver, String coins_gold, String materials) {
        DatabaseDeals.id.add(dealId);
        DatabaseDeals.owner.add(owner);
        DatabaseDeals.coins_copper.add(coins_copper);
        DatabaseDeals.coins_silver.add(coins_silver);
        DatabaseDeals.coins_gold.add(coins_gold);
        DatabaseDeals.materials.add(materials);
    }


    public static int getDealsQuantity() {
        return DEALS_QUANTITY;
    }

    public static String getOwner(int dealId) {
        return DatabaseDeals.owner.get(dealId-1);
    }
    public static String getCoinsCopper(int dealId) {
        return DatabaseDeals.coins_copper.get(dealId-1);
    }
    public static String getCoinsSilver(int dealId) {
        return DatabaseDeals.coins_silver.get(dealId-1);
    }
    public static String getCoinsGold(int dealId) {
        return DatabaseDeals.coins_gold.get(dealId-1);
    }
    public static String getMaterials(int dealId) {
        return DatabaseDeals.materials.get(dealId-1);
    }

    public static void setOwner(int dealId, String newOwner) {
        owner.set(dealId-1, newOwner);
    }
    public static void setCoinsCopper(int dealId, String newQuantity) {
        coins_copper.set(dealId-1, newQuantity);
    }
    public static void setCoinsCopper(int dealId, int newQuantity) {
        coins_copper.set(dealId-1, Integer.toString(newQuantity));
    }
    public static void setCoinsSilver(int dealId, String newQuantity) {
        coins_silver.set(dealId-1, newQuantity);
    }
    public static void setCoinsSilver(int dealId, int newQuantity) {
        coins_silver.set(dealId-1, Integer.toString(newQuantity));
    }
    public static void setCoinsGold(int dealId, String newQuantity) {
        coins_gold.set(dealId-1, newQuantity);
    }
    public static void setCoinsGold(int dealId, int newQuantity) {
        coins_gold.set(dealId-1, Integer.toString(newQuantity));
    }
    public static void setMaterials(int dealId, String newQuantity) {
        materials.set(dealId-1, newQuantity);
    }
    public static void setMaterials(int dealId, int newQuantity) {
        materials.set(dealId-1, Integer.toString(newQuantity));
    }
}
