package lombok;

import com.google.common.collect.Lists;

public class LombokTest {
    static Var v =()-> Lists.newArrayList(1,2,3,4);

    public static void main(String[] args) {
        System.out.println(v.get());
        var student = new Student();
        student.setAge(27);
        student.setMale("man");
        student.setName("lance");
        student.setStudentNo("2017");

        System.out.println(student.toString());

        Pupil pupil = Pupil.builder()
                .isHaveHLJ(false)
                .student(student)
                .build();

        System.out.println(pupil.toString());

    }

}
