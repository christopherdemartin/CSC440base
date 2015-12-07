public class OrderLine
{
	private String productName;
	private int sku;
	private int quantity;

	public OrderLine(String prodName, int skusku, int quant)
	{
		productName = new String(prodName);
		sku = skusku;
		quantity = quant;
	}
	
	//Getters
	
	public String getProdName()
	{
		return productName;
	}

	public int getSku()
	{
		return sku;
	}

	public int getQuantity()
	{
		return quantity;
	}




}