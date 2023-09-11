package chap17;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Random;

@RequiredArgsConstructor
@ToString
@Getter
public class TempInfo {
    public static final Random random = new Random();

    private final String town;
    private final int temp;

    public static TempInfo fetch(String town) {
        if (random.nextInt(10) == 0) {
            throw new RuntimeException("Error!");
        }
        return new TempInfo(town, random.nextInt(100));
    }
}
