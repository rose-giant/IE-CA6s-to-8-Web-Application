package Mizdooni.Controller;

import Mizdooni.Model.Table.TableRest;
import Mizdooni.Model.Table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

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
}
