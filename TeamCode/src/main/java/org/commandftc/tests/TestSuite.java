package org.commandftc.tests;
import org.commandftc.*;
import java.util.Scanner;

public class TestSuite {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        // test7();
        test8();
        // test9();
    }

    public static void test1(){
        System.out.println("----------TEST 1----------");
        NumberCounter nc = new NumberCounter();
        CountTo10Command c10 = new CountTo10Command(nc);
        CommandScheduler.scheduleCommand(c10);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }

        CommandScheduler.scheduleCommand(new ListToYCommand(new LetterLister()));
        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }

        System.out.println("--------TEST 1 END--------");
        sc.nextLine();
    }

    public static void test2() {
        System.out.println("----------TEST 2----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter());
        ListToYCommand ly = new ListToYCommand(new LetterLister());
        CommandScheduler.scheduleCommand(c10);
        CommandScheduler.scheduleCommand(ly);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }

        c10 = new CountTo10Command(new NumberCounter());
        ly = new ListToYCommand(new LetterLister());
        CommandScheduler.scheduleCommand(ly);
        CommandScheduler.scheduleCommand(c10);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }

        System.out.println("--------TEST 2 END--------");
        sc.nextLine();
    }

    public static void test3() {
        System.out.println("----------TEST 3----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter());
        ListToYCommand ly = new ListToYCommand(new LetterLister());
        
        Command c10ThenLy = Command.makeSequential(ly, c10);
        CommandScheduler.scheduleCommand(c10ThenLy);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }


        System.out.println("--------TEST 3 END--------");
        sc.nextLine();
    }

    public static void test4() {
        System.out.println("----------TEST 4----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter());
        ListToYCommand ly = new ListToYCommand(new LetterLister());
        
        Command c10PLy = Command.makeParallelFirst(c10, ly);
        CommandScheduler.scheduleCommand(c10PLy);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }

        c10 = new CountTo10Command(new NumberCounter());
        ly = new ListToYCommand(new LetterLister());
        
        c10PLy = Command.makeParallelFirst(ly, c10);
        CommandScheduler.scheduleCommand(c10PLy);

        for(int i = 0; i < 100; i++) {
            CommandScheduler.runOnce();
        }


        System.out.println("--------TEST 4 END--------");
        sc.nextLine();

    }

    public static void test5() {
        System.out.println("----------TEST 5----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter());
        ListToYCommand ly = new ListToYCommand(new LetterLister());
        MyTrigger trig = new MyTrigger();
        trig.whenActive(c10);

        for(int i = 1; i <= 100; i++) {
            if(i == 20) {
                trig.set(true);
            }
            System.out.print(i + "th output: ");
            CommandScheduler.runOnce();
            System.out.println();
        }



        System.out.println("--------TEST 5 END--------");
        sc.nextLine();

    }

    public static void test6() {
        System.out.println("----------TEST 6----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter());
        MyTrigger trig = new MyTrigger();
        trig.whenActive(c10);

        for(int i = 1; i <= 100; i++) {
            if(i == 20) {
                trig.set(true);
            }
            if(i == 25) {
                trig.set(false);
            }
            System.out.print(i + "th output: ");
            CommandScheduler.runOnce();
            System.out.println();
        }



        System.out.println("--------TEST 6 END--------");
        sc.nextLine();

    }

    public static void test7() {
        System.out.println("----------TEST 7----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter() {
            @Override
            public void periodic() {

            }
        });
        MyTrigger trig = new MyTrigger();
        trig.whenHeld(c10);

        for(int i = 1; i <= 100; i++) {
            if(i == 20) {
                trig.set(true);
            }
            if(i == 35) {
                trig.set(false);
            }
            System.out.print(i + "th output: ");
            CommandScheduler.runOnce();
            System.out.println();
        }



        System.out.println("--------TEST 7 END--------");
        sc.nextLine();

    }
    public static void test8() {
        System.out.println("----------TEST 8----------");
        CountTo10Command c10 = new CountTo10Command(new NumberCounter() {
            @Override
            public void periodic() {

            }
        });
        MyTrigger trig = new MyTrigger();
        trig.whileHeld(c10);

        for(int i = 1; i <= 100; i++) {
            if(i == 20) {
                trig.set(true);
            }
            if(i == 50) {
                trig.set(false);
            }
            System.out.print(i + "th output: ");
            CommandScheduler.runOnce();
            System.out.println();
        }



        System.out.println("--------TEST 8 END--------");
        sc.nextLine();

    }

    public static void test9() {
        MyTrigger myTrig = new MyTrigger();
        myTrig.whenHeld(new RunCommand(() -> System.out.print("Hah!")));

        for(int i = 0; i < 20; i++) {
            myTrig.set(i % 2 == 0);
            System.out.print("Output: ");
            CommandScheduler.runOnce();
            System.out.println();
        }
    }
}