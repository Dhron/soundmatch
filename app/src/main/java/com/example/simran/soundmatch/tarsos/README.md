The code in this package is taken from the [TarsosDSP](https://github.com/JorenSix/TarsosDSP) project and used in accordance with
their license. The [license](https://github.com/JorenSix/TarsosDSP/blob/master/license.txt) is provided for this package.

For the YIN algorithm, TarsosDSP notes the following:

    "For the implementation of the YIN pitch tracking algorithm. Both the
     [the YIN paper](http://recherche.ircam.fr/equipes/pcm/cheveign/ps/2002_JASA_YIN_proof.pdf) and the GPL’d
     [aubio implementation](http://aubio.org/) were used as a reference. Matthias Mauch (of Queen Mary University, London)
     kindly provided the FastYin implementation which uses an FFT to calculate the difference function, it makes the algorithm
     up to 3 times faster"

Also, the following resources provided some insight and led me to this algorithm:

    * http://recherche.ircam.fr/equipes/pcm/cheveign/pss/2002_JASA_YIN.pdf
    * http://stackoverflow.com/a/11569383/1478764