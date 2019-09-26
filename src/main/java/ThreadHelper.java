/*
 *  Gitas Fleet Tracking System 2019
 *
 *  Contributors:
 *      - Ahmet Ziya Kanbur
 *
 */

public class ThreadHelper {
    /**
     * Delay the thread for given time
     *
     * @param milliseconds delay interval
     */
    public static void delay(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch( InterruptedException e ){
            e.printStackTrace();
        }
    }
}
