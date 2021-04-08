package lombok;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Pupil extends Student{
    private Boolean isHaveHLJ;

    @Builder
    private Pupil(Boolean isHaveHLJ, Student student) {
        this.setIsHaveHLJ(isHaveHLJ);
        this.setAge(student.getAge());
        this.setMale(student.getMale());
        this.setName(student.getName());
        this.setStudentNo(student.getStudentNo());
    }

}
