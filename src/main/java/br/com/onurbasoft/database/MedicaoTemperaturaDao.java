package br.com.onurbasoft.database;

import br.com.onurbasoft.core.MedicaoTemperatura;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;

public class MedicaoTemperaturaDao {
    private static final MedicaoTemperaturaHandler handler = new MedicaoTemperaturaHandler();

    public void insert(double temperatura) {
        try {
            QueryRunner run = new QueryRunner(DataSourceFactory.getInstance().create());
            run.insert("INSERT INTO medicao_temperatura(temperatura) VALUES (?)", handler, temperatura);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) {
        try {
            QueryRunner run = new QueryRunner(DataSourceFactory.getInstance().create());
            run.update("DELETE FROM medicao_temperatura WHERE id = ?", id);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MedicaoTemperatura> getTodasMedicoes() {
        try {
            QueryRunner run = new QueryRunner(DataSourceFactory.getInstance().create());
                return run.query("SELECT * FROM medicao_temperatura LIMIT 100", handler);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}