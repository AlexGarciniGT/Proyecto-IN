package banxico;

public class App {

    public static void main(String[] args) {
		String[] urls = ReadURL.getUrls();
        // usando los urls itere por cada url, creando una intancia del hilo de la clase series, tambien va a contar los hilos activos
        for (String url : urls) {
            new Thread(new Series(url)).start();
            if (Thread.activeCount() == 190) {
                System.out.println(Thread.activeCount());
                // try {
                    // levanta un hilo en segundo plano que dura 5 minutos y espera hasta que acabe
                    // Thread.sleep(5 * 60 * 1000);
                    while (Thread.activeCount() != 0) {
                        System.out.print(Thread.activeCount());
                        System.out.print("\r");
                    }

                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
            }
        }
            
    }
}