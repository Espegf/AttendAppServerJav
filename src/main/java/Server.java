
import attendapp.persistence.AddService;
import attendapp.persistence.Arrivals;
import attendapp.persistence.Student;
import attendapp.persistence.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Server {
    private static final List<Client> CLIENTS = new ArrayList<>();

    public static void main(String[] args) {
        // ---------------- INICIO Hilo de salida Este hilo se queda esperando a que escribas algo
        new Thread(() -> {
            while (true) {
                Scanner sc = new Scanner(System.in);
                try {
                    readScanner(sc);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
        try (ServerSocket server = new ServerSocket()) {
            server.bind(new InetSocketAddress("0.0.0.0", 8000));
            System.out.println("Servidor iniciado para la recuperación de datos: " + server.getLocalPort());
            while (!server.isClosed()) {
                Client soc = new Client(server.accept());
                // ---------------- INICIO Hilo de entrada por este hilo se leen los mensajes de los clientes
                new Thread(() -> {
                    CLIENTS.add(soc);
                    try {
                        receive(soc);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    } finally {
                        try {
                            closeClient(soc);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //Recibir de un cliente concreto
    private static void receive(Client soc) throws IOException {
        while (!soc.getSocket().isClosed()) {
            String msj = soc.receiveMessage();
            System.out.println();
            closeClient(soc);
            save(msj);

        }
    }
    public static void save(String dat){
        String[] datos = dat.split(",");
        String name = datos[0];
        String surname = datos[1];
        String mac = datos[2];
        String place = datos[3];
        LocalDateTime date = LocalDateTime.now();
        AddService add = new AddService();
        add.addStudent(name,surname,mac,place,date);

        // System.out.println(name + " " + surname + " " + mac);
    }

    public static void closeClient(Client soc) throws IOException {
        if (soc.getSocket().isClosed() && !CLIENTS.contains(soc)) return;
        soc.close();
        CLIENTS.remove(soc);
    }

    //Cierra la conexión de todos los clientes
    public static void closeAllClients() throws IOException {
        ListIterator<Client> iterator = CLIENTS.listIterator();
        while (iterator.hasNext()) {
            closeClient(iterator.next());
        }
    }

    //Menu
    public static void menu(){
        System.out.println("**************************************");
        System.out.println("Menu");
        System.out.println("**************************************");
        System.out.println("1.Escribe 'alumnos' para mostrar una lista de los que han asistido a clase y si han llegado tarde o no (5 min margen desde la hora actual)");
        System.out.println();
        System.out.println("2.Escribe 'alumnos fecha' para mostrar una lista de los que han asistido a clase y si han llegado tarde o no (5 min margen desde la hora indicada)");
        System.out.println("La fecha debe estar en formato '2023-02-09 16:42:00'");
        System.out.println();
        System.out.println("3.Escribe 'alumnos fecha margen(int)' para mostrar una lista de los que han asistido a clase y si han llegado tarde o no (margen a elegir)");
        System.out.println();
        System.out.println("4.Escribe 'clase' para mostrar una lista de los alumnos");
        System.out.println();
    }

    //Consulta rápida sin parametros coge la hora actual con 5 min de margen
    public static void listAlumnos() throws IOException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Arrivals> criteria = builder.createQuery(Arrivals.class);
            Root<Arrivals> root = criteria.from(Arrivals.class);
            List<Arrivals> resultSet = session.createQuery(criteria).getResultList();
            LocalDateTime tiempoIni=LocalDateTime.now().minusMinutes(5);
            LocalDateTime tiempoFin=LocalDateTime.now().plusMinutes(5);
            for (Arrivals r : resultSet){
                if (r.getDate().after(Timestamp.valueOf(tiempoIni)) & r.getDate().before(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r + " ha llegado a tiempo");
                }else if(r.getDate().after(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r + " ha llegado tarde");
                }
            }
            System.out.println("Los demas no han llegado");
        }
    }

    //Consulta alumnos
    public static void listAlumnos(LocalDateTime horaInicio) throws IOException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Arrivals> criteria = builder.createQuery(Arrivals.class);
            Root<Arrivals> root = criteria.from(Arrivals.class);
            List<Arrivals> resultSet = session.createQuery(criteria).getResultList();
            LocalDateTime tiempoIni=horaInicio.minusMinutes(5);
            LocalDateTime tiempoFin=horaInicio.plusMinutes(5);
            for (Arrivals r : resultSet){
                if (r.getDate().after(Timestamp.valueOf(tiempoIni)) & r.getDate().before(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r + " ha llegado a tiempo");
                }else if(r.getDate().after(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r + " ha llegado tarde");
                }
            }
            System.out.println("Los demás no han llegado");
        }
    }

    //Consulta todos alumnos
    public static void listClase() throws IOException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
            Root<Student> root = criteria.from(Student.class);
            List<Student> resultSet = session.createQuery(criteria).getResultList();
            for (Student r : resultSet){
                System.out.println(r.toString());
            }
        }
    }

    //Consulta todos alumnos
    public static void asistenciaDe1Hora(LocalDateTime horaInicio, int margen) throws IOException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Arrivals> criteria = builder.createQuery(Arrivals.class);
            Root<Arrivals> root = criteria.from(Arrivals.class);
            List<Arrivals> resultSet = session.createQuery(criteria).getResultList();
            LocalDateTime tiempoIni=horaInicio.minusMinutes(margen);
            LocalDateTime tiempoFin=horaInicio.plusMinutes(margen);
            for (Arrivals r : resultSet){
                if (r.getDate().after(Timestamp.valueOf(tiempoIni)) & r.getDate().before(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r.toString() + " ha llegado a tiempo");
                }else if(r.getDate().after(Timestamp.valueOf(tiempoFin))){
                    System.out.println(r.toString() + " ha llegado tarde");
                }
            }
            System.out.println("Los demás no han llegado");
        }
    }

    //Metodo leer lo escrito para saber que hacer
    //list lista los clientes que hay conectados al servidor
    //close cierra la conexión de uno(close num) o todos los clientes (close)
    //exit Cierra la conexión de los clientes y se cierra el servidor
    public static void readScanner(Scanner sc) throws IOException {
        String message = sc.nextLine();
        if (message.toLowerCase().equals("alumnos")){
            listAlumnos();
        } else if(message.toLowerCase().startsWith("alumnos ")) {
            String[] parts = message.split(" ");
            if (parts.length == 3) {
                String index = parts[1]+"T"+parts[2];
                System.out.println(index);
                LocalDateTime hora = LocalDateTime.parse(index);
                listAlumnos(hora);
            }else if (parts.length == 4) {
                String index = parts[1]+"T"+parts[2];
                int tiemo = Integer.parseInt(parts[3]);
                LocalDateTime hora = LocalDateTime.parse(index);
                asistenciaDe1Hora(hora, tiemo);
            } else {
                System.out.println("Error al pasar parametros");
            }
        }else if(message.toLowerCase().startsWith("clase")) {
            listClase();
        }else if(message.toLowerCase().startsWith("cerrar")){
            closeAllClients();
            System.exit(0);
        }else{
            menu();
        }
    }
}
