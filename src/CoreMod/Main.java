package CoreMod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Collection<Person> personsList = new ArrayList<>(initializeList());

        // Найдём несоврешеннолетних (Возраст <18)
        Stream<Person> stream1 = personsList.stream();
        long countOfUnderage = stream1
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несоврешеннолетних в списке: " + countOfUnderage);

        // Найдём призывников (18<возраст<27 И мужчины) и составим список из их Имён + фамилий
        Stream<Person> stream2 = personsList.stream();
        List<String> surnamesOfRecruits = new LinkedList<>();
        surnamesOfRecruits = stream2
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .map(person -> person.getName() + " " + person.getSurname())
                .collect(Collectors.toList());
        //TODO раскомментить, чтобы получить список призывников
        //surnamesOfRecruits.forEach(System.out::println);

        // Получим отсортированный по фамилии список работоспособных людей (М: 18-65, Ж: 18-60 + Высшее образование)
        Stream<Person> stream3 = personsList.stream();
        List<Person> sortedWorkerList = new LinkedList<>();
        sortedWorkerList = stream3
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> (person.getAge() < 65 && person.getAge() > 17 && person.getSex() == Sex.MAN)
                || (person.getAge() < 60 && person.getAge() > 17 && person.getSex() == Sex.WOMAN))
                .sorted(Comparator.comparing(person -> person.getSurname()))
                .collect(Collectors.toList());
        //TODO раскомментить, чтобы получить список работоспособных людей по фамилиям
        //sortedWorkerList.forEach(System.out::println);
    }

    public static Collection<Person> initializeList() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John", "Carry", "Mary", "Anna", "Steven", "Katerina");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown", "Gray", "Black", "King", "Walker");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }
}
