package wb.plugin.wbutils.deals;

import java.util.ArrayList;

public class DatabaseDeals {

    private static ArrayList<Integer> id = new ArrayList<>(25);
    private static ArrayList<String> owner = new ArrayList<>(25);
    private static ArrayList<String> coins_copper = new ArrayList<>(25);
    private static ArrayList<String> coins_silver = new ArrayList<>(25);
    private static ArrayList<String> coins_gold = new ArrayList<>(25);
    private static ArrayList<String> materials = new ArrayList<>(25);


    public static void addDealInfo(int id, String owner, String coins_copper, String coins_silver, String coins_gold, String materials) {
        DatabaseDeals.id.add(id);
        DatabaseDeals.owner.add(owner);
        DatabaseDeals.coins_copper.add(coins_copper);
        DatabaseDeals.coins_silver.add(coins_silver);
        DatabaseDeals.coins_gold.add(coins_gold);
        DatabaseDeals.materials.add(materials);
    }


    public static String getOwner(int dealId){
        return DatabaseDeals.owner.get(dealId-1);
    }
    public static String getCoinsCopper(int dealId){
        return DatabaseDeals.coins_copper.get(dealId-1);
    }
    public static String getCoinsSilver(int dealId){
        return DatabaseDeals.coins_silver.get(dealId-1);
    }
    public static String getCoinsGold(int dealId){
        return DatabaseDeals.coins_gold.get(dealId-1);
    }
    public static String getMaterials(int dealId){
        return DatabaseDeals.materials.get(dealId-1);
    }

    public static void setOwner(int dealId, String newOwner) {
        owner.set(dealId-1, newOwner);
    }
    public static void setCoinsCopper(int dealId, String newQuantity) {
        coins_copper.set(dealId-1, newQuantity);
    }
    public static void setCoinsSilver(int dealId, String newQuantity) {
        coins_silver.set(dealId-1, newQuantity);
    }
    public static void setCoinsGold(int dealId, String newQuantity) {
        coins_gold.set(dealId-1, newQuantity);
    }
    public static void setMaterials(int dealId, String newQuantity) {
        materials.set(dealId-1, newQuantity);
    }
}
