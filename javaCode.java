public class javaCode {

    // The actual "password" we are trying to verify character by character
    static String pass = "WKZVUUAWEFJLWSJTEFFWZDKVF";
    static int p_len = pass.length();

    // Volatile counter used so the JVM / JIT does NOT optimize away the empty delay
    // loop.
    // Declaring it volatile ensures writes are visible and prevents certain
    // compiler optimizations
    private static volatile int volatileCounter = 0;

    public static void main(String[] args) {
        // Start with a partial guess of the password
        StringBuilder sb = new StringBuilder();

        int flag = 0; // Indicator if full password has been found

        // Repeat trials to reduce noise in timing measurements
        while (true) {

            // Array to accumulate timing measurements for each letter A..Z
            long times[] = new long[26];
            char corrChar = '?'; // Stores the most likely correct next character
            long maxD = -1; // Keeps track of the maximum accumulated time

            for (int i = 1; i <= 1000; i++) {
                for (char j = 'A'; j <= 'Z'; j++) {
                    sb.append(j); // Try appending candidate character

                    // Measure verification time for current guess
                    long start = System.nanoTime();
                    int correct = verify(sb, sb.length());
                    long end = System.nanoTime();
                    long duration = end - start;

                    // Accumulate time for this candidate character
                    times[j - 'A'] += duration;

                    // If verify reports a full match, print and exit
                    if (correct == 1) {
                        flag = 1;
                        System.out.println(sb);
                        return;
                    }

                    // Remove last tested character before next candidate
                    sb.deleteCharAt(sb.length() - 1);
                }
            }

            // Print accumulated timings and pick the character with max total time
            for (char i = 'A'; i <= 'Z'; i++) {
                System.out.print(sb);
                System.out.print(" time : " + times[i - 'A']);
                System.out.println();

                if (maxD < times[i - 'A']) {
                    maxD = times[i - 'A'];
                    corrChar = i;
                }
            }

            // Append the most likely correct character based on timing
            sb.append(corrChar);

            if (flag == 1) {
                System.out.println(sb);
                return;
            }

            // Safety: if guess length exceeds password length, stop
            if (sb.length() > p_len) {
                return;
            }
        }
    }
    //Translated verify function.
    static int verify(StringBuilder data, int len) {
        int correct = 1;
        int matched_chars = 0;

        for (int j = 0; j < p_len && j < data.length(); j++) {
            if (data.charAt(j) == pass.charAt(j)) {
                matched_chars++;
                for (int i = 0; i < 5000; i++) {
                    volatileCounter++;
                }
            } else {
                break;
            }
        }

        if (matched_chars != p_len) {
            correct = 0;
        }
        return correct;
    }
}
