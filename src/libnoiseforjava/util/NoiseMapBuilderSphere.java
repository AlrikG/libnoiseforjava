/*******************************************************************************
 *  Copyright (c) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright (c) 2010 Thomas J. Hodge (java port of libnoise)
 *  Copyright (c) Nick Whitney ( changed noisegen to perlin basis. added javadoc)
 *  
 *  This file is part of libnoiseforjava.
 *  
 *  libnoiseforjava is a Java port of the C++ library libnoise, which may be
 *  found at http://libnoise.sourceforge.net/. libnoise was developed by Jason
 *  Bevins, who may be contacted at jlbezigvins@gmzigail.com (for great email,
 *  take off every 'zig'). Porting to Java was done by Thomas Hodge, who may be
 *  contacted at libnoisezagforjava@gzagmail.com (remove every 'zag').
 *  
 *  libnoiseforjava is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option) any
 *  later version.
 *  
 *  libnoiseforjava is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *  
 *  You should have received a copy of the GNU General Public License along with
 *  libnoiseforjava. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package libnoiseforjava.util;

import libnoiseforjava.model.Sphere;

/**
 * Builds a spherical noise map.
 * <p>
 * This class builds a noise map by filling it with coherent-noise values
 * generated from the surface of a sphere.
 * <p>
 * This class describes these input values using a (latitude, longitude)
 * coordinate system. After generating the coherent-noise value from the input
 * value, it then "flattens" these coordinates onto a plane so that it can write
 * the values into a two-dimensional noise map.
 * <p>
 * The sphere model has a radius of 1.0 unit. Its center is at the origin.
 * <p>
 * The x coordinate in the noise map represents the longitude. The y coordinate
 * in the noise map represents the latitude.
 * <p>
 * The application must provide the southern, northern, western, and eastern
 * bounds of the noise map, in degrees.
 */
public class NoiseMapBuilderSphere extends NoiseMapBuilder {

    /**
     * Eastern boundary of the spherical noise map, in degrees.
     */
    double eastLonBound;

    /**
     * Northern boundary of the spherical noise map, in degrees.
     */
    double northLatBound;

    /**
     * Southern boundary of the spherical noise map, in degrees.
     */
    double southLatBound;

    /**
     * Western boundary of the spherical noise map, in degrees.
     */
    double westLonBound;

    public NoiseMapBuilderSphere() throws IllegalArgumentException {
        super();
        this.eastLonBound = 0.0;
        this.northLatBound = 0.0;
        this.southLatBound = 0.0;
        this.westLonBound = 0.0;
    }

    @Override
    public void build() throws IllegalArgumentException {
        if (this.eastLonBound <= this.westLonBound || this.northLatBound <= this.southLatBound || this.destWidth <= 0 || this.destHeight <= 0
                || this.sourceModule == null || this.destNoiseMap == null) {
            throw new IllegalArgumentException("Invalid Parameter in NoiseMapBuilderSphere");
        }

        // Resize the destination noise map so that it can store the new output
        // values from the source model.
        this.destNoiseMap.setSize(this.destWidth, this.destHeight);

        // Create the plane model.
        Sphere sphereModel = new Sphere();
        sphereModel.setModule(this.sourceModule);

        double lonExtent = this.eastLonBound - this.westLonBound;
        double latExtent = this.northLatBound - this.southLatBound;
        double xDelta = lonExtent / this.destWidth;
        double yDelta = latExtent / this.destHeight;
        double curLon = this.westLonBound;
        double curLat = this.southLatBound;

        // Fill every point in the noise map with the output values from the
        // model.
        for (int y = 0; y < this.destHeight; y++) {
            curLon = this.westLonBound;
            for (int x = 0; x < this.destWidth; x++) {
                float curValue = (float) sphereModel.getValue(curLat, curLon);
                this.destNoiseMap.setValue(x, y, curValue);
                curLon += xDelta;
            }
            curLat += yDelta;
            setCallback(y);

        }
    }

    /**
     * Returns the eastern boundary of the spherical noise map.
     *
     * @returns The eastern boundary of the noise map, in degrees.
     */
    public double getEastLonBound() {
        return this.eastLonBound;
    }

    /**
     * Returns the northern boundary of the spherical noise map.
     *
     * @returns The northern boundary of the noise map, in degrees.
     */
    public double getNorthLatBound() {
        return this.northLatBound;
    }

    /**
     * Returns the southern boundary of the spherical noise map.
     *
     * @return The southern boundary of the noise map, in degrees.
     */
    public double getSouthLatBound() {
        return this.southLatBound;
    }

    /**
     * Returns the western boundary of the spherical noise map.
     *
     * @return The western boundary of the noise map, in degrees.
     */
    public double getWestLonBound() {
        return this.westLonBound;
    }

    /**
     * Sets the coordinate boundaries of the noise map.
     *
     * @param southLatBound The southern boundary of the noise map, in degrees.
     * @param northLatBound The northern boundary of the noise map, in degrees.
     * @param westLonBound The western boundary of the noise map, in degrees.
     * @param eastLonBound The eastern boundary of the noise map, in degrees.
     *
     * @pre The southern boundary is less than the northern boundary.
     * @pre The western boundary is less than the eastern boundary.
     *
     * @throws IllegalArgumentException See the preconditions.
     */
    public void setBounds(double southLatBound, double northLatBound, double westLonBound, double eastLonBound) throws IllegalArgumentException {
        if (southLatBound >= northLatBound || westLonBound >= eastLonBound) {
            throw new IllegalArgumentException("Invalid Parameter in NoiseMapBuilderSphere");
        }

        this.southLatBound = southLatBound;
        this.northLatBound = northLatBound;
        this.westLonBound = westLonBound;
        this.eastLonBound = eastLonBound;
    }

    public void setEastLonBound(double eastLonBound) {
        this.eastLonBound = eastLonBound;
    }

    public void setNorthLatBound(double northLatBound) {
        this.northLatBound = northLatBound;
    }

    public void setSouthLatBound(double southLatBound) {
        this.southLatBound = southLatBound;
    }

    public void setWestLonBound(double westLonBound) {
        this.westLonBound = westLonBound;
    }

}
