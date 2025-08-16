package edu.app.listas;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LinkedList<?> lista = null;

        while (true) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Seleccionar tipo de lista");
            System.out.println("2. Manejar agenda de contactos");
            System.out.println("3. Operar con lista actual");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    lista = seleccionarTipoLista();
                    break;
                case 2:
                    if (lista == null) {
                        System.out.println("Primero selecciona un tipo de lista (opción 1).");
                    } else {
                        manejarAgendaContactos((LinkedList<DataTypeExamples.Contact>) lista);
                    }
                    break;
                case 3:
                    if (lista == null) {
                        System.out.println("Primero selecciona un tipo de lista (opción 1).");
                    } else {
                        operarListaActual((LinkedList<Object>) lista);
                    }
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    private static LinkedList<?> seleccionarTipoLista() {
        System.out.println("\nSelecciona el tipo de lista:");
        System.out.println("1. Simplemente enlazada");
        System.out.println("2. Doblemente enlazada");
        System.out.println("3. Circular");
        System.out.print("Opción: ");

        int opcion = leerEntero();
        LinkedList.ListType tipo;
        switch (opcion) {
            case 1: tipo = LinkedList.ListType.SINGLY; break;
            case 2: tipo = LinkedList.ListType.DOUBLY; break;
            case 3: tipo = LinkedList.ListType.CIRCULAR; break;
            default:
                System.out.println("Opción inválida, se seleccionará lista simplemente enlazada por defecto.");
                tipo = LinkedList.ListType.SINGLY;
        }
        System.out.println("Lista seleccionada: " + tipo);
        return new LinkedList<>(tipo);
    }

    private static void manejarAgendaContactos(LinkedList<DataTypeExamples.Contact> agenda) {
        while (true) {
            System.out.println("\n=== Agenda de Contactos ===");
            System.out.println("1. Agregar contacto");
            System.out.println("2. Eliminar contacto por nombre");
            System.out.println("3. Buscar contacto por nombre");
            System.out.println("4. Mostrar todos los contactos");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Dirección: ");
                    String direccion = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    agenda.addLast(new DataTypeExamples.Contact(nombre, direccion, telefono));
                    System.out.println("Contacto agregado.");
                    break;
                case 2:
                    System.out.print("Nombre del contacto a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    boolean eliminado = false;
                    for (int i = 0; i < agenda.size(); i++) {
                        DataTypeExamples.Contact c = agenda.getDataAt(i);
                        if (c.getName().equalsIgnoreCase(nombreEliminar)) {
                            agenda.removeAt(i);
                            eliminado = true;
                            System.out.println("Contacto eliminado.");
                            break;
                        }
                    }
                    if (!eliminado) System.out.println("Contacto no encontrado.");
                    break;
                case 3:
                    System.out.print("Nombre del contacto a buscar: ");
                    String nombreBuscar = scanner.nextLine();
                    boolean encontrado = false;
                    for (int i = 0; i < agenda.size(); i++) {
                        DataTypeExamples.Contact c = agenda.getDataAt(i);
                        if (c.getName().equalsIgnoreCase(nombreBuscar)) {
                            System.out.println("Contacto encontrado: " + c);
                            encontrado = true;
                        }
                    }
                    if (!encontrado) System.out.println("Contacto no encontrado.");
                    break;
                case 4:
                    System.out.println("Contactos en la agenda:");
                    System.out.println(agenda.toForwardString());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    private static void operarListaActual(LinkedList<Object> lista) {
        while (true) {
            System.out.println("\n=== Operar con lista actual ===");
            System.out.println("1. Agregar elemento al inicio");
            System.out.println("2. Agregar elemento al final");
            System.out.println("3. Agregar elemento en índice");
            System.out.println("4. Eliminar primer elemento");
            System.out.println("5. Eliminar último elemento");
            System.out.println("6. Eliminar elemento por índice");
            System.out.println("7. Buscar elemento");
            System.out.println("8. Mostrar lista");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el elemento: ");
                    String e1 = scanner.nextLine();
                    lista.addFirst(e1);
                    System.out.println("Elemento agregado.");
                    break;
                case 2:
                    System.out.print("Ingresa el elemento: ");
                    String e2 = scanner.nextLine();
                    lista.addLast(e2);
                    System.out.println("Elemento agregado.");
                    break;
                case 3:
                    System.out.print("Índice: ");
                    int index = leerEntero();
                    System.out.print("Elemento: ");
                    String e3 = scanner.nextLine();
                    try {
                        lista.addAt(index, e3);
                        System.out.println("Elemento agregado.");
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                case 4:
                    try {
                        Object removed = lista.removeFirst();
                        System.out.println("Elemento eliminado: " + removed);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 5:
                    try {
                        Object removed = lista.removeLast();
                        System.out.println("Elemento eliminado: " + removed);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Índice a eliminar: ");
                    int idx = leerEntero();
                    try {
                        Object removed = lista.removeAt(idx);
                        System.out.println("Elemento eliminado: " + removed);
                    } catch (Exception ex) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                case 7:
                    System.out.print("Elemento a buscar: ");
                    String bus = scanner.nextLine();
                    int pos = lista.indexOf(bus);
                    if (pos >= 0) System.out.println("Elemento encontrado en índice: " + pos);
                    else System.out.println("Elemento no encontrado.");
                    break;
                case 8:
                    System.out.println("Lista actual: " + lista.toForwardString());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    private static int leerEntero() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida, ingresa un número: ");
            }
        }
    }
}
