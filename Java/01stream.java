import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class JavaStreams {

    public static void main(String[] args) throws IOException {

        // 1. Integer Stream
        IntStream
            .range(1, 10)
            .forEach(System.out::print);
        System.out.println();

        /*
            결과
            123456789

        */

        // 2. Integer Stream with skip
        IntStream
            .range(1, 10)
            .skip(5)
            .forEach(x -> System.out.println(x));
        System.out.println();

        /*
            설명
            - skip : 앞 5개를 건너뛴다.
            - forEach : 람다식으로 표현 가능

            결과
            6789
            
        */

        // 3. Integer Stream with sum
        System.out.println(IntStream.range(1, 5).sum());
        System.out.println();

        /*
            설명
            - sum : 합계

            결과
            10
            
        */

        // 4. Stream.of, sorted and findFirst
        Stream.of("Ava", "Aneri", "Alberto")
            .sorted()
            .findFirst()
            .ifPresent(System.out::println);

        /*
            설명
            - of : 배열 등을 넣는다. 다양한 Object 넣기 가능
            - sorted : 오름차순, 내림차순, 사용자 정의 comparator 가능
            - findFirst : 첫번째 항목 선택
            - ifPresent : 만일 있다면 실행.

            결과
            Alverto
            
        */

        // 5. Stream from Array, sort, filter and print
        String[] names = {
            "Al",
            "Ankit",
            "Kushal",
            "Brent",
            "Sarika",
            "amanda",
            "Hans",
            "Shivika",
            "Sarah"
        };
        Arrays.stream(names) // same as Stream.of(names)
            .filter(x -> x.startsWith("S"))
            .sorted()
            .forEach(System.out::println);

        /*
            설명
            - Arrays.stream(배열) === Stream.of(배열)
            - filter : 람다식 조건에 따른 값만 추출해냄.

            결과
            Sarah
            Sarika
            Shivika

        */

        // 6. average of squares of an int array
        Arrays.stream(new int[] {
                2,
                4,
                6,
                8,
                10
            })
            .map(x -> x * x)
            .average()
            .ifPresent(System.out::println);

        /*
            설명
            - map : 각 요소들을 람다식에 의한 변형이 일어남.
            - average : 평균을 출력함.

            결과
            44.0 (double)
            
        */

        // 7. Stream from List, filter and print
        List < String > people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        people
            .stream()
            .map(String::toLowerCase)
            .filter(x -> x.startsWith("a"))
            .forEach(System.out::println);

        /*
            설명
            - list는 곧바로 stream을 통해 스트림 형태로 변형이 가능하다.

            결과
            al
            ankit
            amanda
            
        */

        /* band.txt
            Rolling Stones
            Lady Gaga
            Jackson Browne
            Maroon 5
            Arijit Singh
            Elton John
            John Mayer
            CCR
            Eagles
            Pink
            Aerosmith
            Adele
            Taylor Swift
            Faye Wong
            Bob Seger
            ColdPlay
            Boston
            The Cars
            Cheap Trick
            Def Leppard
            Ed Sheeran
            Dire Straits
            Train
            Tom Petty
            Jack Johnson
            Jimmy Buffett
            Mumford and Sons
            Phil Collins
            Rod Stewart
            The Script
            Elvis
            Michael Buble
        */

        // 8. Stream rows from text file, sort, filter, and print
        Stream < String > bands = Files.lines(Paths.get("bands.txt"));
        bands
            .sorted()
            .filter(x -> x.length() > 13)
            .forEach(System.out::println);
        bands.close();

        /*
            설명
            - 파일을 stream형태로 읽어서, 스트림 함수를 사용할 수 있다.
            - 정렬 후 14 이상 길이의 행을 출력.

            결과
            Jackson Browne
            Mumford and Sons
            Rolling Stones
            
        */

        // 9. Stream rows from text file and save to List
        List < String > bands2 = Files.lines(Paths.get("bands.txt"))
            .filter(x -> x.contains("jit"))
            .collect(Collectors.toList());
        bands2.forEach(x -> System.out.println(x));

        /*
            설명
            - 행 중 "jit"를 포함한 행을 filter.
            - .collect(Collectors.toList()) : Collect를 통해 List 형태로 변환할 수 있다.

            결과
            Arijit Singh
            
        */

        /* data.txt
            A,12,3.7
            B,17,2.8
            C,14,1.9
            D,23,2.7
            E
            F,18,3.4
        */

        // 10. Stream rows from CSV file and count
        Stream < String > rows1 = Files.lines(Paths.get("data.txt"));
        int rowCount = (int) rows1
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .count();
        System.out.println(rowCount + " rows.");
        rows1.close();

        /*
            설명
            - 정수와 실수형이 없는 row는 제외하고 싶다!
            - split을 통해 각 row를 분할하고, 각 row 중 배열의 길이가 3인 값만 filter한다. 그 후, row의 갯수를 count.

            결과
            5 rows.
            
        */

        // 11. Stream rows from CSV file, parse data from rows
        Stream < String > rows2 = Files.lines(Paths.get("data.txt"));
        rows2
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .filter(x -> Integer.parseInt(x[1]) > 15)
            .forEach(x -> System.out.println(x[0] + "  " + x[1] + "  " + x[2]));
        rows2.close();

        /*
            결과
            B  17  2.8
            D  23  2.7
            F  18  3.4
            
        */

        // 12. Stream rows from CSV file, store fields in HashMap
        Stream < String > rows3 = Files.lines(Paths.get("data.txt"));
        Map < String, Integer > map = new HashMap < > ();
        map = rows3
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .filter(x -> Integer.parseInt(x[1]) > 15)
            .collect(Collectors.toMap(
                    x -> x[0], // key
                    x -> Integer.parseInt(x[1])) // value
            );
        rows3.close();
        for (String key: map.keySet()) {
            System.out.println(key + "  " + map.get(key));
        }

        /*
            결과
            B  17
            D  23
            F  18
            
        */

        // 13. Reduction - sum
        double total = Stream.of(7.3, 1.5, 4.8)
            .reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println("Total = " + total);

        /*
            설명
            - reduce : 0.0 : 시작 값
            - 모든 요소를 순회하는 데, 초기값 or 람다식의 결과가 Double a 가 되고, 새로운 순회 요소가 b 가 된다.

            결과
            Total = 13.6
            
        */

        // 14. Reduction - summary statistics
        IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10)
            .summaryStatistics();
        System.out.println(summary);

    }
}
