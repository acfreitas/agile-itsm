package br.com.citframework.util.geo;

import br.com.citframework.util.Assert;

/**
 * Classe que representa um ponto na superfície de uma esfera
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 23/10/2014
 *
 */
public class GeoLocation {

    private static final String INVALID_LATITUDE = "Latitude is not a valid value: '%s'";
    private static final String INVALID_LONGITUDE = "Longitude is not a valid value: '%s'";
    private static final String LOCATION_MUST_NOT_BE_NULL = "Location must not be null";

    private GeoLocation() {}

    private double radiansLatitude;
    private double radiansLongitude;
    private double degreesLatitude;
    private double degreesLongitude;

    public double getLatitudeInDegrees() {
        return degreesLatitude;
    }

    public double getLongitudeInDegrees() {
        return degreesLongitude;
    }

    public double getLatitudeInRadians() {
        return radiansLatitude;
    }

    public double getLongitudeInRadians() {
        return radiansLongitude;
    }

    /**
     * Calcula um círculo de distância entre o ponto geográfico informado como argumento e o ponto geográfico atual
     *
     * @param radius
     *            o raio na esfera, para calculo da distância. Como exemplo, o raio aproximado da terra é 6371.01 quilômetros
     * @return distância, na mesma unidade de medida do raio informado como argumento
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public double distanceTo(final GeoLocation location, final double radius) {
        Assert.notNull(location, LOCATION_MUST_NOT_BE_NULL);
        return Math.acos(Math.sin(radiansLatitude) * Math.sin(location.getLatitudeInRadians()) + Math.cos(this.getLatitudeInRadians()) * Math.cos(location.getLatitudeInRadians())
                * Math.cos(this.getLongitudeInRadians() - location.getLongitudeInRadians()))
                * radius;
    }

    /**
     * Calcula distância em quilômetros entre dois pontos presentes na terra
     *
     * @param pointOne
     *            primeiro ponto para cálculo da distância
     * @param pointTwo
     *            segundo ponto para cálculo da distância
     * @return distância, em quilômetros, entre os pontos
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public static double distanceToOnEarth(final GeoLocation pointOne, final GeoLocation pointTwo) {
        Assert.notNull(pointOne, LOCATION_MUST_NOT_BE_NULL);
        return pointOne.distanceTo(pointTwo, GeoUtils.EARTH_RADIUS);
    }

    /**
     * <p>
     * Calcula as coordenadas limite de todos os pontos da superfície de uma esfera, tendo como referência {@link GeoLocation} e que a distância é menor ou igual à distância
     * {@code distance}
     * </p>
     *
     * @param distance
     *            a distância do ponto representado pela instância. Será medido na mesma unidade do argumento {@code radius}
     * @param radius
     *            raio da esfera onde será calculado a distância
     * @return um array contendo dois objetos {@link GeoLocation}, sendo o primeiro contendo as menores coordenadas e o segundo contendo as maiores coordenadas
     */
    public GeoLocation[] boundingCoordinates(final double distance, final double radius) {
        if (radius < 0d || distance < 0d) {
            throw new IllegalArgumentException();
        }

        final double radDist = distance / radius;

        double minLat = radiansLatitude - radDist;
        double maxLat = radiansLatitude + radDist;

        double minLon, maxLon;
        if (minLat > GeoUtils.MIN_LAT && maxLat < GeoUtils.MAX_LAT) {
            final double deltaLon = Math.asin(Math.sin(radDist) / Math.cos(radiansLatitude));
            minLon = radiansLongitude - deltaLon;
            if (minLon < GeoUtils.MIN_LON) {
                minLon += 2d * Math.PI;
            }
            maxLon = radiansLongitude + deltaLon;
            if (maxLon > GeoUtils.MAX_LON) {
                maxLon -= 2d * Math.PI;
            }
        } else {
            minLat = Math.max(minLat, GeoUtils.MIN_LAT);
            maxLat = Math.min(maxLat, GeoUtils.MAX_LAT);
            minLon = GeoUtils.MIN_LON;
            maxLon = GeoUtils.MAX_LON;
        }

        return new GeoLocation[] {fromRadians(minLat, minLon), fromRadians(maxLat, maxLon)};
    }

    /**
     * Gera um objeto {@link GeoLocation} a partir de ponto geográfico representado em graus
     *
     * @param latitude
     *            latitude em graus
     * @param longitude
     *            longitude em graus
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public static GeoLocation fromDegrees(final double latitude, final double longitude) {
        final GeoLocation result = new GeoLocation();
        result.radiansLatitude = Math.toRadians(latitude);
        result.radiansLongitude = Math.toRadians(longitude);
        result.degreesLatitude = latitude;
        result.degreesLongitude = longitude;
        result.checkBounds();
        return result;
    }

    /**
     * Gera um objeto {@link GeoLocation} a partir de ponto geográfico representado em radianos
     *
     * @param latitude
     *            latitude em radianos
     * @param longitude
     *            longitude em radianos
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public static GeoLocation fromRadians(final double latitude, final double longitude) {
        final GeoLocation result = new GeoLocation();
        result.radiansLatitude = latitude;
        result.radiansLongitude = longitude;
        result.degreesLatitude = Math.toDegrees(latitude);
        result.degreesLongitude = Math.toDegrees(longitude);
        result.checkBounds();
        return result;
    }

    private void checkBounds() {
        Assert.isTrue(radiansLatitude >= GeoUtils.MIN_LAT, String.format(INVALID_LATITUDE, radiansLatitude));
        Assert.isTrue(radiansLatitude <= GeoUtils.MAX_LAT, String.format(INVALID_LATITUDE, radiansLatitude));
        Assert.isTrue(radiansLongitude >= GeoUtils.MIN_LON, String.format(INVALID_LONGITUDE, radiansLongitude));
        Assert.isTrue(radiansLongitude <= GeoUtils.MAX_LON, String.format(INVALID_LONGITUDE, radiansLongitude));
    }

}
