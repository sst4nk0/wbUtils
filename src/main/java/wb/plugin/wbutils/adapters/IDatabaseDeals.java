package wb.plugin.wbutils.adapters;

public interface IDatabaseDeals {

    String getMaterials(int dealId);

    void setOwner(int dealId, String owner);

    void setMaterials(int dealId, String materials);

    String getOwner(int dealId);

    int getDealsQuantity();

    void addDealInfo(int dealId, String owner, String coins_copper, String coins_silver, String coins_gold, String materials);

    String getCoinsCopper(int dealId);

    String getCoinsSilver(int dealId);

    String getCoinsGold(int dealId);

    void setCoinsCopper(int dealId, String newQuantity);

    void setCoinsSilver(int dealId, String newQuantity);

    void setCoinsGold(int dealId, String newQuantity);
}
