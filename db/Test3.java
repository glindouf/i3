public class Test3 {

    public static void main(String[] args) {
    }

private void DisplayData(List<productdetail>ProductsList) {
DefaultTableModel aModel = new DefaultTableModel() {
//setting the jtable read only

    @Override
    public boolean isCellEditable(int row, int column) {
    return false;
    }
    };
    //setting the column name
    Object[] tableColumnNames = new Object[2];
    tableColumnNames[0] = “Product Name”;
    tableColumnNames[1] = “Description”;

    aModel.setColumnIdentifiers(tableColumnNames);
    if (ProductsList == null) {
    this.tbProducts.setModel(aModel);
    return;
    }

    Object[] objects = new Object[2];
    ListIterator<productsdetail> lstrg = ProductsList.listIterator();
    //populating the tablemodel
    while (lstrg.hasNext()) {
    productsdetail newcus = lstrg.next();
    objects[0] = newcus.getProductName();
    objects[1] = newcus.getDescription();

    aModel.addRow(objects);
    }

    //binding the jtable to the model
    this.tbProducts.setModel(aModel);
}