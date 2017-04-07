package br.com.onurbasoft.core;

import br.com.onurbasoft.database.MedicaoTemperaturaDao;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SendMedicoesTemperaturaService implements Runnable {
    private MedicaoTemperaturaDao eDao = new MedicaoTemperaturaDao();
    private final String URL = "http://localhost:8080/medicao-temperatura";

    @Override
    public void run() {
        eDao.getTodasMedicoes().forEach(medicoes -> {
            Gson gson = Converters.registerInstant(new GsonBuilder()).create();
            try {
                HttpService.post(URL, gson.toJson(medicoes));
                System.out.println(gson.toJson(medicoes));
                //Testar se executou com sucesso
                eDao.remove(medicoes.getId());
            }
            catch (Exception e) {
               e.printStackTrace();
            }
        });
        System.out.println("rodei");
    }
}
