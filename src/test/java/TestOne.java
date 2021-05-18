
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class TestOne {

    @Test
    public void test() {
        int sum = 2 + 2;
        Assertions.assertEquals(sum, 4);
    }

    @Test
    public void check_Values_Inputs_happy_path() {
        Assert.assertTrue(RandomGroups.isCorrectValues(2, "c:/students", "c:/topics"));
    }

    @Test
    public void Check_Values_Inputs_With_Null() {
        Assert.assertFalse(RandomGroups.isCorrectValues(2, null, null));
    }

    @Test
    public void givenFileName_whenUsingFileUtils_thenFileData() throws IOException {
        String expectedData = "Hello world";

        Path path = Paths.get("C:\\Users\\Alien Ware\\Documents\\java\\file_test.txt");

        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        Assert.assertEquals(expectedData, line);
    }

    @Test
    public void read_File_And_Out_Names() throws IOException {
        int expectedData = 5;
        Path path = Paths.get("C:\\Users\\Alien Ware\\Documents\\java\\thais.txt");
        long counter = Files.lines(path).count();
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();

        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
        Assert.assertEquals(expectedData, counter);
    }

    @Test
    public void read_File_And_Out_Names_And_Add_To_List() throws IOException {
        int expectedData = 5;
        Path path = Paths.get("C:\\Users\\Alien Ware\\Documents\\java\\thais.txt");
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        List<String> students = new ArrayList<>();

        while (line != null) {
            System.out.println(line);
            students.add(line);
            line = reader.readLine();
        }

        reader.close();
        Assert.assertEquals(expectedData, students.size());
    }

    @Test
    public void check_If_Is_File_Null() throws IOException {
        String rootStudents = "c:/fake_students.txt";
        String rootTopics = "c:/topics.txt";
        File fileStudents = new File(rootStudents);
        File fileTopics = new File(rootTopics);
        Assert.assertFalse(RandomGroups.isCorrectFile(fileStudents, fileTopics));
    }

    @Test
    public void put_Students_By_Total_Groups() {
        int numberOfgroups = 2;
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("Jaan Jferns");

        Student student2 = new Student();
        student2.setName("Fol Cors");

        Student student3 = new Student();
        student3.setName("Tom Hola");

        Student student4 = new Student();
        student4.setName("Jhon Albert");

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);


        double divition = (double) students.size() / numberOfgroups;

        if (divition != Math.floor(divition)) {
            System.out.print(true);
        } else {
            List<Group> groups = new ArrayList<>();
            Collections.shuffle(students);

            students.stream().forEach(k -> {
                Random rn = new Random();
                int answer = rn.nextInt((int) divition) + 1;
                k.setGroupId(answer);
                System.out.println("Nombre: " + k.getName() + " groupid: " + k.getGroupId());
            });
            List<Integer> studentIds = students.stream().map(Student::getGroupId).collect(Collectors.toList());

            System.out.println("studentIds:" + studentIds);
            List<Integer> idsOfGroups = new ArrayList<>();

            for (int x = 1; x <= numberOfgroups; x++) {
                Group group = new Group();
                group.setId(x);
                groups.add(group);
                idsOfGroups.add(x);
            }

            for (Integer id : idsOfGroups) {
                List<Student> studentsGroups = students.stream().filter(s -> s.getGroupId() == id).distinct().collect(Collectors.toList());
                groups.stream().distinct().filter(g -> g.getId() == id).forEach(group -> {
                    group.setStudents(studentsGroups);
                });
            }

            System.out.println("group key 2:" + groups.get(0).getStudents());
            groups.get(0).getStudents().stream().forEach(k -> {
                System.out.println("Nombre " + k.getName() + " GroupId: " + k.getGroupId());
            });

            System.out.println("groups key 1:" + groups.get(1).getStudents());
            groups.get(1).getStudents().stream().forEach(k -> {
                System.out.println("Nombre " + k.getName() + " GroupId: " + k.getGroupId());
            });

        }
    }

    @Test
    public void put_Topics_By_Total_Groups() {
        int numberOfgroups = 2;
        List<String> topics = new ArrayList<>();
        topics.add("Madera");
        topics.add("Guacamole");
        topics.add("Teteo");
        topics.add("Clase");
        topics.add("Lampara");

        Assert.assertEquals(topics.size(), numberOfgroups);

        List<Group> groups = new ArrayList<>();
        Collections.shuffle(topics);

        List<Integer> idsOfGroups = new ArrayList<>();

        for (int x = 1; x <= numberOfgroups; x++) {
            Group group = new Group();
            group.setId(x);
            groups.add(group);
            idsOfGroups.add(x);
        }

        for (Integer id : idsOfGroups) {
            List<String> topicsGroups = topics.stream().distinct().collect(Collectors.toList());
            System.out.println("topicsGroups" + topicsGroups);
            groups.stream().distinct().filter(g -> g.getId() == id).forEach(group -> {
                System.out.println(topicsGroups.get(id - 1));
                group.setTopic(topicsGroups.get(id -1));
            });
        }

        System.out.println("group topic 0:" + groups.get(0).getTopic());
        System.out.println("group topic 1:" + groups.get(1).getTopic());

    }
}
