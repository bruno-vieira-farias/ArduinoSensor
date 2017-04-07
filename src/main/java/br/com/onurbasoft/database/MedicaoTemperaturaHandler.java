package br.com.onurbasoft.database;

import br.com.onurbasoft.core.MedicaoTemperatura;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedicaoTemperaturaHandler implements ResultSetHandler<List<MedicaoTemperatura>> {
    @Override
    public List<MedicaoTemperatura> handle(ResultSet rs) throws SQLException {
        List<MedicaoTemperatura> medicoes = Collections.emptyList();
        while (rs.next()) {
            medicoes = new ArrayList<>();
            do {
                medicoes.add(
                        new MedicaoTemperatura(
                                rs.getInt("ID"),
                                rs.getDouble("TEMPERATURA"),
                                rs.getTimestamp("INSTANTE").toInstant()
                        )
                );
            }
            while (rs.next());
        }
        return medicoes;
    }
}
