public class Order 
{
    private String orderStatus;
    private int orderNumber;
    //private Picklist orderLines;
    
    public Order(String status, int orderNum /*,Picklist items*/)
    {
        orderStatus = new String(status);
        orderNumber = orderNum;
        //orderLines = items;
    }
    
    public void setStatus(String updatedStatus)
    {
        orderStatus = updatedStatus;
    }
    
    public void setOrderNum(int newNum)
    {
        orderNumber = newNum;
    }
    
    public String getStatus()
    {
        return orderStatus;
    }
    
    public int getOrderNum()
    {
        return orderNumber;
    }
    
}
