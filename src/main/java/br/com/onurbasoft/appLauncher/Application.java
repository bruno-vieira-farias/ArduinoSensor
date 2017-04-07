package br.com.onurbasoft.appLauncher;

import br.com.onurbasoft.core.SendMedicoesTemperaturaService;
import br.com.onurbasoft.core.ArduinoCommunicator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start(){
        System.out.println("Sistema Iniciado");
        new ArduinoCommunicator().initialize();


        scheduler.scheduleWithFixedDelay(new SendMedicoesTemperaturaService(), 0,30, TimeUnit.SECONDS );
    }
}