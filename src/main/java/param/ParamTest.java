package param;

public class ParamTest {
    public static void main(String[] args) {
        TestParam param = new TestParam();
        param.setA(1);
        param.setB(2);
        param.setC("asd");
        param.validator().b().validate("参数错误");
        System.out.println(param);
        TestV2Param testV2Param = new TestV2Param();
        testV2Param.copyOfParam(param);
        print(testV2Param);
    }

    public static void print(TestV2Param param) {
        System.out.println(param);
    }
}
