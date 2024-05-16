package Mizdooni.Model.Table;

import java.util.ArrayList;

public class TableRepository {
    private static TableRepository instance;
    private ArrayList<TableRest> tableRests = new ArrayList<>();

    public TableRepository() throws Exception {
        TableDAO dao = new TableDAO();
        tableRests = dao.getFromAPI();
    }

    public static TableRepository getInstance() throws Exception {
        if(instance == null)
            return new TableRepository();
        else return instance;
    }

    public ArrayList<TableRest> getAll() {
        return tableRests;
    }

    public ArrayList<TableRest> findTableByRestaurantName(String restaurantName) {
        ArrayList<TableRest> tables = new ArrayList<>();
        for(TableRest tableRest: tableRests) {
            if (restaurantName.equals(tableRest.restaurantName)) {
                tables.add(tableRest);
            }
        }

        return tables;
    }
}
