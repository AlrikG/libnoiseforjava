/*******************************************************************************
 *  Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright © 2010 Thomas J. Hodge (java port of libnoise)
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

package libnoiseforjava.module;

import java.util.Random;

import libnoiseforjava.NoiseQuality;
import libnoiseforjava.SimplexBasis;

/**
 * Noise module that outputs 3-dimensional Simplex noise.
 * 
 * <p>
 * Simplex noise is the sum of several coherent-noise functions of
 * ever-increasing frequencies and ever-decreasing amplitudes.
 * 
 * <p>
 * An important property of Simplex noise is that a small change in the input
 * value will produce a small change in the output value, while a large change
 * in the input value will produce a random change in the output value.
 * 
 * <p>
 * This noise module outputs Simplex-noise values that usually range from -1.0
 * to +1.0, but there are no guarantees that all output values will exist within
 * that range.
 * 
 * <p>
 * For a better description of Simplex noise, see the links in the <i>References
 * and Acknowledgments</i> section.
 * 
 * <p>
 * This noise module does not require any source modules.
 * 
 * <p>
 * <b>Octaves</b>
 * 
 * <p>
 * The number of octaves control the <i>amount of detail</i> of the Simplex
 * noise. Adding more octaves increases the detail of the Simplex noise, but
 * with the drawback of increasing the calculation time.
 * 
 * <p>
 * An octave is one of the coherent-noise functions in a series of
 * coherent-noise functions that are added together to form Simplex noise.
 * 
 * <p>
 * An application may specify the frequency of the first octave by calling the
 * setFrequency() method.
 * 
 * <p>
 * An application may specify the number of octaves that generate Simplex noise
 * by calling the setOctaveCount() method.
 * 
 * <p>
 * These coherent-noise functions are called octaves because each octave has, by
 * default, double the frequency of the previous octave. Musical tones have this
 * property as well; a musical C tone that is one octave higher than the
 * previous C tone has double its frequency.
 * 
 * <p>
 * <b>Frequency</b>
 * 
 * <p>
 * An application may specify the frequency of the first octave by calling the
 * setFrequency() method.
 * 
 * <p>
 * <b>Persistence</b>
 * 
 * <p>
 * The persistence value controls the <i>roughness</i> of the Simplex noise.
 * Larger values produce rougher noise.
 * 
 * <p>
 * The persistence value determines how quickly the amplitudes diminish for
 * successive octaves. The amplitude of the first octave is 1.0. The amplitude
 * of each subsequent octave is equal to the product of the previous octave's
 * amplitude and the persistence value. So a persistence value of 0.5 sets the
 * amplitude of the first octave to 1.0; the second, 0.5; the third, 0.25; etc.
 * 
 * <p>
 * An application may specify the persistence value by calling the
 * setPersistence() method.
 * 
 * <p>
 * <b>Lacunarity</b>
 * 
 * <p>
 * The lacunarity specifies the frequency multipler between successive octaves.
 * 
 * <p>
 * The effect of modifying the lacunarity is subtle; you may need to play with
 * the lacunarity value to determine the effects. For best results, set the
 * lacunarity to a number between 1.5 and 3.5.
 * 
 * <p>
 * <b>References &amp; acknowledgments</b>
 * 
 * <p>
 * <a href=http://www.noisemachine.com/talk1/>The Noise Machine</a> - From the
 * master, Ken Simplex himself. This page contains a presentation that describes
 * Simplex noise and some of its variants. He won an Oscar for creating the
 * Simplex noise algorithm!
 * 
 * <p>
 * <a href=http://freespace.virgin.net/hugo.elias/models/m_perlin.htm> Simplex
 * Noise</a> - Hugo Elias's webpage contains a very good description of Simplex
 * noise and describes its many applications. This page gave me the inspiration
 * to create libnoise in the first place. Now that I know how to generate
 * Simplex noise, I will never again use cheesy subdivision algorithms to create
 * terrain (unless I absolutely need the speed.)
 * 
 * <p>
 * <a href=http://www.robo-murito.net/code/perlin-noise-math-faq.html>The
 * Simplex noise math FAQ</a> - A good page that describes Simplex noise in
 * plain English with only a minor amount of math. During development of
 * libnoise, I noticed that my coherent-noise function generated terrain with
 * some "regularity" to the terrain features. This page describes a better
 * coherent-noise function called <i>gradient noise</i>. This version of the
 * Simplex module uses gradient coherent noise to generate Simplex noise.
 */
public class Simplex extends ModuleBase {

    // Default frequency for the noise::module::Simplex noise module.
    static final double DEFAULT_SIMPLEX_FREQUENCY = 1.0;

    // Default lacunarity for the noise::module::Simplex noise module.
    static final double DEFAULT_SIMPLEX_LACUNARITY = 2.0;

    // Default number of octaves for the noise::module::Simplex noise module.
    static final int DEFAULT_SIMPLEX_OCTAVE_COUNT = 6;

    // Default persistence value for the noise::module::Simplex noise module.
    static final double DEFAULT_SIMPLEX_PERSISTENCE = 0.5;

    // Default noise quality for the noise::module::Simplex noise module.
    static final NoiseQuality DEFAULT_SIMPLEX_QUALITY = NoiseQuality.QUALITY_STD;

    // Default noise seed for the noise::module::Simplex noise module.
    static final int DEFAULT_SIMPLEX_SEED = 0;

    // Maximum number of octaves for the noise::module::Simplex noise module.
    static final int SIMPLEX_MAX_OCTAVE = 30;

    // Frequency of the first octave.
    double frequency;

    // Frequency multiplier between successive octaves.
    double lacunarity;

    // Quality of the Simplex noise.
    NoiseQuality noiseQuality;

    // Total number of octaves that generate the Simplex noise.
    int octaveCount;

    // Persistence of the Simplex noise.
    double persistence;

    // Seed value used by the Simplex-noise function.
    int seed;

    private SimplexBasis[] source;
    double[] frequencies;
    double[] amplitudes;

    public Simplex() {
        super(0);
        this.frequency = DEFAULT_SIMPLEX_FREQUENCY;
        this.lacunarity = DEFAULT_SIMPLEX_LACUNARITY;
        this.noiseQuality = DEFAULT_SIMPLEX_QUALITY;
        this.octaveCount = DEFAULT_SIMPLEX_OCTAVE_COUNT;
        this.persistence = DEFAULT_SIMPLEX_PERSISTENCE;
        this.seed = DEFAULT_SIMPLEX_SEED;
    }

    public void build() {
        this.source = new SimplexBasis[this.octaveCount];
        this.frequencies = new double[this.octaveCount];
        this.amplitudes = new double[this.octaveCount];

        Random rnd = new Random(this.seed);

        for (int i = 0; i < this.octaveCount; i++) {
            this.source[i] = new SimplexBasis();

            if (this.seed == 0) {
                this.source[i].setSeed(0);
            } else {
                this.source[i].setSeed(rnd.nextInt());
            }

            this.frequencies[i] = this.frequency * Math.pow(this.lacunarity, i);
            this.amplitudes[i] = Math.pow(this.persistence, i);
        }
    }

    @Override
    public double getValue(double x, double y, double z) {
        double value = 0;
        double signal = 0;

        for (int i = 0; i < this.source.length; i++) {
            signal = this.source[i].getValue(x * this.frequencies[i], y * this.frequencies[i], z * this.frequencies[i]);
            value += signal * this.amplitudes[i];
        }

        return value;
    }

    /**
     * Returns the frequency of the first octave.
     *
     * @return The frequency of the first octave.
     */
    public double getFrequency() {
        return this.frequency;
    }

    /**
     * Returns the lacunarity of the Simplex noise.
     * 
     * <p>
     * The lacunarity is the frequency multiplier between successive octaves.
     *
     * @return The lacunarity of the Simplex noise.
     */
    public double getLacunarity() {
        return this.lacunarity;
    }

    /**
     * Returns the quality of the Simplex noise.
     * 
     * See NoiseQuality for definitions of the various coherent-noise qualities.
     * 
     * @return The quality of the Simplex noise.
     */
    public NoiseQuality getNoiseQuality() {
        return this.noiseQuality;
    }

    /**
     * Returns the number of octaves that generate the Simplex noise.
     *
     * <p>
     * The number of octaves controls the amount of detail in the Simplex noise.
     * 
     * @return The number of octaves that generate the Simplex noise.
     */
    public int getOctaveCount() {
        return this.octaveCount;
    }

    /**
     * Returns the persistence value of the Simplex noise.
     *
     * <p>
     * The persistence value controls the roughness of the Simplex noise.
     * 
     * @return The persistence value of the Simplex noise.
     */
    public double getPersistence() {
        return this.persistence;
    }

    /**
     * Returns the seed value used by the Simplex-noise function.
     *
     * @return The seed value.
     */
    public int getSeed() {
        return this.seed;
    }

    /**
     * Sets the frequency of the first octave.
     *
     * @param frequency The frequency of the first octave.
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    /**
     * Sets the lacunarity of the Simplex noise.
     * 
     * <p>
     * The lacunarity is the frequency multiplier between successive octaves.
     *
     * <p>
     * For best results, set the lacunarity to a number between 1.5 and 3.5.
     *
     * @param lacunarity The lacunarity of the Simplex noise.
     */
    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    /**
     * Sets the quality of the Simplex noise.
     *
     * <p>
     * See NoiseQuality for definitions of the various coherent-noise qualities.
     * 
     * @param noiseQuality The quality of the Simplex noise.
     */
    public void setNoiseQuality(NoiseQuality noiseQuality) {
        this.noiseQuality = noiseQuality;
    }

    /**
     * Sets the number of octaves that generate the Simplex noise.
     * 
     * <p>
     * The number of octaves controls the amount of detail in the Simplex noise.
     *
     * <p>
     * The larger the number of octaves, the more time required to calculate the
     * Simplex-noise value.
     *
     * @param octaveCount The number of octaves that generate the Simplex noise.
     *
     * @pre The number of octaves ranges from 1 to SIMPLEX_MAX_OCTAVE.
     */
    public void setOctaveCount(int octaveCount) {
        if (octaveCount < 1) {
            octaveCount = 1;
        } else if (octaveCount > SIMPLEX_MAX_OCTAVE) {
            octaveCount = SIMPLEX_MAX_OCTAVE;
        }

        this.octaveCount = octaveCount;
    }

    /**
     * Sets the persistence value of the Simplex noise.
     *
     * <p>
     * The persistence value controls the roughness of the Simplex noise.
     *
     * <p>
     * For best results, set the persistence to a number between 0.0 and 1.0.
     * 
     * @param persistence The persistence value of the Simplex noise.
     */
    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    /**
     * Sets the seed value used by the Simplex-noise function.
     *
     * @param seed The seed value.
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }
}
