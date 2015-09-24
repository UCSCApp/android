package slugapp.com.ucscstudentapp.map;

/**
 * Created by isayyuhh on 9/24/2015.
 */
public class MarkerEnum {
    public enum DiningHall {
        COLLEGE_EIGHT_OAKES("College Eight / Oakes", 36.991565, -122.065267),
        PORTER_KRESGE("Porter / Kresge", 36.994344, -122.065800),
        COLLEGE_NINE_COLLEGE_TEN("College Nine / College Ten", 37.001096, -122.058031),
        CROWN_MERRILL("Crown / Merrill", 36.999971, -122.054448),
        COWELL_STEVENSON("Cowell / Stevenson", 36.997157, -122.053150);

        private String name;
        private double lat;
        private double lng;

        DiningHall (String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }

        public String getName () {
            return this.name;
        }
        public double getLat () {
            return this.lat;
        }
        public double getLng () {
            return this.lng;
        }
    }

    public enum Library {
        MCHENRY("McHenry Library", 36.99578157522153, -122.058908423001),
        S_AND_E("Science and Engineering Library", 36.99904411574191, -122.06070818525006);

        private String name;
        private double lat;
        private double lng;

        Library (String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }

        public String getName () {
            return this.name;
        }
        public double getLat () {
            return this.lat;
        }
        public double getLng () {
            return this.lng;
        }
    }
}
