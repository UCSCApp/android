package slugapp.com.sluglife.utils;

import com.google.android.gms.maps.model.LatLng;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/* Copyright 2013 Google Inc.
   Licensed under Apache 2.0: http://www.apache.org/licenses/LICENSE-2.0.html */

/**
 * Created by isaiah on 9/1/2015
 * <p/>
 * This file contains a helper interface that helps moves google map markers.
 */
public interface LatLngInterpolator {

    /**
     * Allows the user to move markers with animation
     *
     * @param fraction Fraction of time in terms with period of time passed
     * @param a        Position 'a'
     * @param b        Position 'b'
     * @return New position
     */
    LatLng interpolate(float fraction, LatLng a, LatLng b);

    /**
     * Class that contains math to move markers in a straight line
     */
    class Linear implements LatLngInterpolator {

        /**
         * Moves markers with animation
         *
         * @param fraction Fraction of time in terms with period of time passed
         * @param a        Position 'a'
         * @param b        Position 'b'
         * @return New position
         */
        @Override
        public LatLng interpolate(float fraction, LatLng a, LatLng b) {
            double lat = (b.latitude - a.latitude) * fraction + a.latitude;
            double lng = (b.longitude - a.longitude) * fraction + a.longitude;
            return new LatLng(lat, lng);
        }
    }
}
