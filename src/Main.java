public class Main {
    public static void main(String[] args) {
        lab_02();
    }

    static void lab_01() {
        lab_01 tasks = new lab_01();

        System.out.println(tasks.task_1());

        System.out.println(tasks.task_2());

        System.out.println(tasks.task_3());

        System.out.println(tasks.task_4());

        System.out.println(tasks.task_5());

        tasks.in.close();
    }

    static void lab_02() {
        lab_02 tasks = new lab_02();
        
//        System.out.println(tasks.task_1(tasks.stringInput()) + '\n');
//
//        System.out.println(tasks.task_2(tasks.intArrayInput(), tasks.intArrayInput())  + '\n');
//
//        System.out.println(tasks.task_3(tasks.intArrayInput())  + '\n');

        System.out.println(tasks.task_4(tasks.twoDimensionalIntArrayInput())  + '\n');
//
//        System.out.println(tasks.task_5(tasks.intArrayInput(), tasks.intInput())  + '\n');
//
//        System.out.println(tasks.task_6(tasks.twoDimensionalIntArrayInput())  + '\n');
//
//        System.out.println(tasks.task_7(tasks.twoDimensionalIntArrayInput())  + '\n');

        System.out.println(tasks.task_8(tasks.twoDimensionalIntArrayInput())  + '\n');

        tasks._in.close();
    }
}