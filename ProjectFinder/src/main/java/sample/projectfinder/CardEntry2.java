package sample.projectfinder;

public class CardEntry2 {
    private ProductSearchModel productSearchModel ;
    private int quantity;


    public CardEntry2(ProductSearchModel productSearchModel, int quantity) {
        this.productSearchModel = productSearchModel;
        this.quantity = quantity;
    }

    public ProductSearchModel getProductSearchModel() {
        return productSearchModel;
    }

    public void setProductSearchModel(ProductSearchModel productSearchModel) {
        this.productSearchModel = productSearchModel;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }
    public void decreaseQuantity(){
        if (this.quantity>0){
            this.quantity--;
        }

    }
}
