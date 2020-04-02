package template;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/4/1 15:26
 * @Version: 1.0
 */
public class Test5 {
    @Test
    public void t1() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        //这里addActionListener方法的参数是ActionListener，是一个函数式接口
        //使用lambda表达式方式
        a.forEach(e -> {
            System.out.println("Lambda实现方式" + e);
        });
        //使用方法引用方式
        a.forEach(Test5::sys);

    }


    public static void sys(Integer e) {
        System.out.println("方法引用实现方式" + e);
    }

    @Test
    public void t2() {
        //串行流计算一个范围100万整数流,求能被2整除的数字
        long time0 = System.nanoTime();
        int a[] = IntStream.range(0, 1_000_000).sequential().filter(p -> p % 2 == 0).toArray();
        long time1 = System.nanoTime();
        //并行流来计算
        int b[] = IntStream.range(0, 1_000_000).parallel().filter(p -> p % 2 == 0).toArray();
        long time2 = System.nanoTime();

        System.out.printf("serial: %.2fs, parallel %.2fs%n", (time1 - time0) * 1e-9, (time2 - time1) * 1e-9);
    }

    @Test
    public void t3() {
// 1、创建一个包装对象值为空的Optional对象
        Optional<String> optStr = Optional.empty();
// 2、创建包装对象值非空的Optional对象
        Optional<String> optStr1 = Optional.of("optional");
// 3、创建包装对象值允许为空的Optional对象
        Optional<String> optStr2 = Optional.ofNullable(null);
//        System.out.println(optStr.isPresent());
//        System.out.println(optStr1.isPresent());
//        System.out.println(optStr1.get());
//        System.out.println(optStr2.isPresent());
//        System.out.println(optStr2.get());

        List<Person> personList = Lists.newArrayList();
        personList.add(new Person().setAge("18").setName("fhv").setPhone("131").setSex("y"));
        personList.add(new Person().setAge("19").setName("tom").setPhone("151").setSex("y"));
        personList.add(new Person().setAge("18").setName("lucy").setPhone("152").setSex("x"));
        personList.add(new Person().setAge("20").setName("jurry").setPhone("132").setSex("x"));
        Optional<List<Person>> optionalList = Optional.of(personList);
        Stream<Optional<List<Person>>> stream = Stream.of(optionalList);
        Stream<Person> stream1 = personList.stream();
//        List<String> x = stream1.filter(p -> p.getSex().equals("x")).map(Person::getName).collect(Collectors.toList());
        Optional<String> optional = stream1.findFirst().map(Person::getName);
//        x.forEach(System.out::println);
        System.out.println(optional.get());

    }

    @Test
    public void t4(){
        String location = "abc";
        int count = 1;
        Assert.notNull(location, "Location must not be null");
        System.out.println("location = [" + location + "]");
        Assert.state(count == 0,"负面清单已经存在");
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    class Person {
        private String name;
        private String age;
        private String phone;
        private String sex;
    }

    @Test
    public void test_Supplier() {
        //① 使用Supplier接口实现方法,只有一个get方法，无参数，返回一个值
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                //返回一个随机值
                return new Random().nextInt();
            }
        };

        System.out.println(supplier.get());

        System.out.println("********************");

        //② 使用lambda表达式，
        supplier = () -> new Random().nextInt();
        System.out.println(supplier.get());
        System.out.println("********************");

        //③ 使用方法引用
        Supplier<Double> supplier2 = Math::random;
        System.out.println(supplier2.get());
    }
}
