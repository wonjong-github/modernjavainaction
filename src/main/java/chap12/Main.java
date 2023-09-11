package chap12;

import org.assertj.core.api.Assertions;

import java.time.*;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {
        LocalTime time = LocalTime.of(13, 45, 20);
        LocalDate date = LocalDate.of(2023, 12, 30);
        LocalDate date2 = LocalDate.of(2023, 12, 30);
        int aligendDayOfWeekInMonth = date.get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        LocalDateTime dt = LocalDateTime.of(2017, Month.JUNE, 2, 2, 30);
        LocalDateTime dt2 = time.atDate(date);
        LocalDateTime dt3 = date.atTime(time);
        if (dt2.equals(dt3)) {
            System.out.println("equal");
        }
        Instant instant = Instant.ofEpochSecond(3, 1_000_000_000);
        System.out.println("instant = " + instant);
        Duration d1 = Duration.between(dt, dt2);
        //Duration.between(date, date2);  // 에러 발생
        Duration between = Duration.between(time, dt);
        System.out.println("between = " + between);
        Period between1 = Period.between(date, date2);
        System.out.println("between1 = " + between1);
        LocalDate with = LocalDate.of(2023, 2, 11).with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH, 5);
        System.out.println("with = " + with);
        LocalDate now = LocalDate.now();
        LocalDate with1 = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        System.out.println("with1 = " + with1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now1 = LocalDate.now();
        String format = dateTimeFormatter.format(now1);
        System.out.println("format = " + format);
        LocalDate parse = LocalDate.parse(format, dateTimeFormatter);
        if (parse.isEqual(now1)) System.out.println("true");
        Assertions.assertThat(parse).isEqualTo(now1);

        DateTimeFormatter korea = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.KOREA);
        LocalDate now2 = LocalDate.now();
        String format1 = korea.format(now2);
        System.out.println("format1 = " + format1);
        LocalDate koreaParse = LocalDate.parse(format1, korea);
        if(koreaParse.isEqual(now2)) System.out.println("korea true");
        Assertions.assertThat(koreaParse).isEqualTo(now2);

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(".")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.KOREA);
        System.out.println(formatter.format(now2));

        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        ZonedDateTime zonedDateTime = now.atStartOfDay(zoneId);
        System.out.println("zonedDateTime = " + zonedDateTime);
        LocalDateTime now3 = LocalDateTime.now();
        ZonedDateTime zonedDateTime1 = now3.atZone(seoul);
        System.out.println("zonedDateTime1 = " + zonedDateTime1);
        Instant now4 = Instant.now();
        ZonedDateTime zonedDateTime2 = now4.atZone(seoul);
        System.out.println("zonedDateTime2 = " + zonedDateTime2);

        ZoneOffset seoulOffset = ZoneOffset.ofHours(9);
        System.out.println("서울의 시간 차이: " + seoulOffset);

        // UTC와의 차이가 -5시간 30분인 ZoneOffset을 생성합니다.
        ZoneOffset newYorkOffset = ZoneOffset.ofHoursMinutes(-5, -30);
        System.out.println("뉴욕의 시간 차이: " + newYorkOffset);
        LocalDate now5 = LocalDate.now();
        JapaneseDate from = JapaneseDate.from(now5);
        System.out.println("from = " + from);
        System.out.println(HijrahDate.now());
        HijrahDate ramadanDate = HijrahDate.now().with(ChronoField.DAY_OF_MONTH, 1)
                .with(ChronoField.MONTH_OF_YEAR, 9);
        System.out.println("ramadanDate = " + IsoChronology.INSTANCE.date(ramadanDate));
    }
}
