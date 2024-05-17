package Mizdooni.Controller;


import Mizdooni.Model.Table.TableRest;
import Mizdooni.Model.Table.TableRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/tables")
public class TableController {
    private final TableRepository tableRepo;
    @Autowired
    public TableController() throws Exception {
        tableRepo = TableRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<TableRest> getAll() throws SQLException {
        return tableRepo.getAll();
    }

    @PostMapping("")
    public TableRest addTable(HttpServletResponse response,
                                    @RequestBody Map<String, String> body) throws Exception {
        TableRest newRest = new TableRest(body.get("username"), Integer.parseInt(body.get("tableNumber")),body.get("restaurantName"),Integer.parseInt(body.get("seatNumber")));
        try{
            tableRepo.addTable(newRest);
            response.setStatus(HttpServletResponse.SC_CREATED);
            return newRest;
        }catch (SQLWarning e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
            System.out.println(e.getMessage());
        }
        return null;
    }

}
