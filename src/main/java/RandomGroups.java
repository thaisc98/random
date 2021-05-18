import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class RandomGroups {

    public static void main(final String[] args) throws IOException {

        int numberOfGroups = Integer.parseInt(args[0]);
        String rootStudents = args[1];
        String rootTopics = args[2];
        if (!isCorrectValues(numberOfGroups, rootStudents, rootTopics)) {
            throw new IllegalArgumentException("Some input values are not correct, please provide the correct values");
        }

        checkFiles(rootStudents, rootTopics);

        List<Student> students = getStudents(rootStudents);

        List<String> topics = getTopics(rootTopics);

        List<Group> groups = new ArrayList<>();
        Collections.shuffle(students);

        calculateRandomGroups(numberOfGroups, students, topics, groups);

        for (int x = 0; x < numberOfGroups; x++) {
            System.out.println("Group ID: " + (x + 1));
            System.out.println("Topic: " + groups.get(x).getTopic());

            groups.get(x).getStudents().stream().forEach(k -> {
                System.out.println(k.getName() + " GroupId: " + k.getGroupId());
            });
            System.out.println("------------");


        }

    }

    private static void calculateRandomGroups(int numberOfGroups, List<Student> students, List<String> topics, List<Group> groups) {

        students.stream().forEach(k -> {
            k.setGroupId(getRandomInteger(numberOfGroups));
        });

        List<Integer> studentIds = students.stream().map(Student::getGroupId).collect(Collectors.toList());

        System.out.println("id students:" + studentIds);

        List<Integer> groupsOfNumber = new ArrayList<>();
        for (int x = 1; x <= numberOfGroups; x++) {
            groupsOfNumber.add((x));
        }
        List<Boolean> ans = new ArrayList<>();
        groupsOfNumber.stream().forEach(n -> {
            ans.add(studentIds.contains(n));
        });

        System.out.println("groupsOfNumber" + groupsOfNumber);

        while (students.containsAll(groupsOfNumber)) {
            students.stream().forEach(k -> {
                k.setGroupId(getRandomInteger(numberOfGroups));
            });
        }

        List<Integer> idsOfGroups = new ArrayList<>();

        for (int x = 1; x <= numberOfGroups; x++) {
            Group group = new Group();
            group.setId(x);
            groups.add(group);
            idsOfGroups.add(x);
        }

        Collections.shuffle(topics);

        for (Integer id : idsOfGroups) {
            List<String> topicsGroups = topics.stream().distinct().collect(Collectors.toList());

            List<Student> studentsGroups = students.stream().filter(s -> s.getGroupId() == id).distinct().collect(Collectors.toList());
            groups.stream().distinct().filter(g -> g.getId() == id).forEach(group -> {
                group.setStudents(studentsGroups);
                group.setTopic(topicsGroups.get(id - 1));
            });
        }
    }

    private static List<String> getTopics(String rootTopics) throws IOException {
        Path pathTopics = Paths.get(rootTopics);
        BufferedReader readerTopics = Files.newBufferedReader(pathTopics);
        String lineTopics = readerTopics.readLine();
        List<String> topics = new ArrayList<>();

        while (lineTopics != null) {
            topics.add(lineTopics);
            lineTopics = readerTopics.readLine();
        }
        readerTopics.close();
        return topics;
    }


    private static List<Student> getStudents(String rootStudents) throws IOException {
        Path path = Paths.get(rootStudents);
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        List<Student> students = new ArrayList<>();

        while (line != null) {
            Student student = new Student();
            student.setName(line);
            students.add(student);
            line = reader.readLine();
        }
        reader.close();
        return students;
    }

    public static boolean isCorrectValues(int number, String rootStudents, String rootTopics) {
        if (Objects.isNull(number) || Objects.isNull(rootStudents) || Objects.isNull(rootTopics)) {
            return false;
        }
        return true;
    }

    public static void checkFiles(String rootStudents, String rootTopics) {
        File fileStudents = new File(rootStudents);
        File fileTopics = new File(rootTopics);

        if (!isCorrectFile(fileStudents, fileTopics)) {
            throw new IllegalArgumentException("Something is happend with your files");
        }
    }

    public static boolean isCorrectFile(File rootStudents, File rootTopics) {
        if (rootStudents.isFile() && rootStudents.exists()) {
            return true;
        } else if (rootTopics.isFile() && rootTopics.exists()) {
            return true;
        }
        return false;
    }

    public static int getRandomInteger(int number) {
        Random rn = new Random();
        return rn.nextInt(number) + 1;
    }


}
