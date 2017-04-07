package br.com.onurbasoft.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import br.com.onurbasoft.database.MedicaoTemperaturaDao;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;

public class ArduinoCommunicator implements SerialPortEventListener {
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    private BufferedReader input;
    private OutputStream output;
    private SerialPort serialPort;
    private static final String PORT_NAMES[] = {"/dev/ttyACM0", "/dev/ttyUSB0", "COM3"};

    public ArduinoCommunicator() {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
    }

    private CommPortIdentifier localizaPortaConectada() throws RuntimeException {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currentPortId = (CommPortIdentifier) portEnum.nextElement();

            for (String portName : PORT_NAMES) {
                System.out.println("Tentando se conectar na porta " + portName);
                if (currentPortId.getName().equals(portName)) {
                    portId = currentPortId;
                    System.out.println("Conectado em " + portName);
                    break;
                }
            }
        }
        if (portId == null) {
            throw new RuntimeException("Não existem portas disponíveis para a leitura do Arduino");
        }
        return portId;
    }

    public void initialize() {
        CommPortIdentifier portId = localizaPortaConectada();

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String dadosLeitura = input.readLine();
                MedicaoTemperatura medicaoTemperatura = new MedicaoTemperatura(Double.parseDouble(dadosLeitura));
                new MedicaoTemperaturaDao().insert(medicaoTemperatura.getGrauCelsius());
                System.out.println("Leitura realizada com sucesso. Valor: " + medicaoTemperatura.getGrauCelsius());
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
}

