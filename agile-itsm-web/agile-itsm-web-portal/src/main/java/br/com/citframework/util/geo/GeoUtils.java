package br.com.citframework.util.geo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.citframework.util.Assert;

/**
 * Utilitários para trabalho com atributos de geolocalização
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 22/09/2014
 *
 */
public final class GeoUtils {

    public static final double MIN_LAT = Math.toRadians(-90d); // -PI/2
    public static final double MAX_LAT = Math.toRadians(90d); // PI/2
    public static final double MIN_LON = Math.toRadians(-180d); // -PI
    public static final double MAX_LON = Math.toRadians(180d); // PI

    public static final double EARTH_RADIUS = 6371.01;

    private static final String LONGITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY = "Longitude field name must not be null or empty";
    private static final String LATITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY = "Latitude field name must not be null or empty";

    private static final String STR_COMMON_PATTERN = "^([-+])?(?:%s(?:(?:\\.0{1,15})?)|(?:[0-9]|[1-%s][0-9]%s)(?:(?:\\.[0-9]{1,15})?))$";

    private static final String STR_LATITUDE_PATTERN = String.format(STR_COMMON_PATTERN, 90, 8, "");
    private static final String STR_LONGITUDE_PATTERN = String.format(STR_COMMON_PATTERN, 180, 9, "{1,}");

    private static final Pattern LATITUDE_PATTERN = Pattern.compile(STR_LATITUDE_PATTERN);
    private static final Pattern LONGITUDE_PATTERN = Pattern.compile(STR_LONGITUDE_PATTERN);

    /**
     * Valida a latitude informada como argumento
     *
     * @param latitude
     *            latitude a ser validada
     * @return {@code true}, caso a latitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validLatitude(String)
     * @since 22/09/2014
     */
    public static Boolean validLatitude(final Double latitude) {
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude must not be null.");
        }
        final Matcher matcherLatitude = LATITUDE_PATTERN.matcher(latitude.toString());
        return matcherLatitude.matches();
    }

    /**
     * Valida a latitude informada como argumento
     *
     * @param latitude
     *            latitude a ser validada
     * @return {@code true}, caso a latitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validLatitude(Double)
     * @since 22/09/2014
     */
    public static Boolean validLatitude(final String latitude) {
        return validLatitude(Double.valueOf(latitude));
    }

    /**
     * Valida a longitude informada como argumento
     *
     * @param longitude
     *            longitude a ser validada
     * @return {@code true}, caso a longitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validLongitude(String)
     * @since 22/09/2014
     */
    public static Boolean validLongitude(final Double longitude) {
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude must not be null.");
        }
        final Matcher matcherLongitude = LONGITUDE_PATTERN.matcher(longitude.toString());
        return matcherLongitude.matches();
    }

    /**
     * Valida a longitude informada como argumento
     *
     * @param longitude
     *            longitude a ser validada
     * @return {@code true}, caso a longitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validLongitude(Double)
     * @since 22/09/2014
     */
    public static Boolean validLongitude(final String longitude) {
        return validLongitude(Double.valueOf(longitude));
    }

    /**
     * Valida a latitude e a longitude informadas como argumento
     *
     * @param latitude
     *            latitude a ser validada
     * @param longitude
     *            longitude a ser validada
     * @return {@code true}, caso latitude e longitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validCoordinates(String, String)
     * @since 22/09/2014
     */
    public static Boolean validCoordinates(final Double latitude, final Double longitude) {
        return validLatitude(latitude) && validLongitude(longitude);
    }

    /**
     * Valida a latitude e a longitude informadas como argumento
     *
     * @param latitude
     *            latitude a ser validada
     * @param longitude
     *            longitude a ser validada
     * @return {@code true}, caso latitude e longitude seja válida. {@code false}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @see GeoUtils#validCoordinates(Double, Double)
     * @since 22/09/2014
     */
    public static Boolean validCoordinates(final String latitude, final String longitude) {
        return validCoordinates(Double.valueOf(latitude), Double.valueOf(longitude));
    }

    private static final String DISTANCE_WHERE_QUERY_WITHOUT_INDEX = " acos(sin(?) * sin(radians(%s)) + cos(?) * cos(radians(%s)) * cos(radians(%s) - (?))) * 6371 <= ? ";
    private static final String DISTANCE_WHERE_QUERY_WITH_INDEX = " (radians(%s) >= ? AND radians(%s) <= ?) AND (radians(%s) >= ? %s radians(%s) <= ?) AND acos(sin(?) * sin(radians(%s)) + cos(?) * cos(radians(%s)) * cos(radians(%s) - (?))) <= ? ";
    private static final String DISTANCE_WHERE_QUERY_WITH_INDEX_FOR_MYSQL_MSSQLSERVER = " (%s >= ? AND %s <= ?) AND (%s >= ? %s %s <= ?) AND acos(sin(?) * sin(%s) + cos(?) * cos(%s) * cos(%s - (?))) <= ? ";

    /**
     * Recupera um trecho de query SQL, a ser adicionado à cláusula {@code WHERE} final a ser executada
     *
     * <p>
     * Exemplo de como seria o resultado final:
     *
     * <pre>
     * acos(sin(-0.306154991) * sin(radians(latitude)) + cos(-0.306154991) * cos(radians(latitude)) * cos(radians(longitude) - (-0.811136922))) * 6371 <= 10000;
     * </pre>
     *
     * </p>
     *
     * Em que:
     * <ul>
     * <li><b>latitude</b>: nome da coluna que contém o valor da latitude na base de dados</li>
     * <li><b>longitude</b>: nome da coluna que contém o valor da longitude na base de dados</li>
     * <li><b>-0.306154991</b>: latitude do ponto central de onde será calculado o raio</li>
     * <li><b>-0.811136922</b>: longitude do ponto central de onde será calculado o raio</li>
     * <li><b>6371</b>: raio da terra</li>
     * <li><b>10000</b>: distância em KM a partir do ponto</li>
     * </ul>
     * </p>
     *
     * <p>
     * Exemplo de uso do trecho de query gerado:
     *
     * <pre>
     *
     * </pre>
     *
     * </p>
     *
     * OBSERVAÇÃO: a query retornada não permite indexação por parte do SGBD, mais perfomática para menor quantidade de dados
     *
     * @param latitudeColumnName
     *            nome da coluna que armazena a informação de latitude do ponto geográfico
     * @param longitudeColumnName
     *            nome da coluna que armazena a informação de longitude do ponto geográfico
     * @return query a ser adicionada à cláusula query, com os statements em seus devidos lugares
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     */
    public static String getSQLWherePieceForDistanceWithoutIndex(final String latitudeColumnName, final String longitudeColumnName) {
        Assert.notNullAndNotEmpty(latitudeColumnName, LATITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        Assert.notNullAndNotEmpty(longitudeColumnName, LONGITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        return String.format(DISTANCE_WHERE_QUERY_WITHOUT_INDEX, latitudeColumnName, latitudeColumnName, longitudeColumnName);
    }

    /**
     * Recupera um trecho de query SQL, a ser adicionado à cláusula {@code WHERE} final a ser executada. A query filtra por faixas, então os limites devem ser calculados antes.
     * Veja {@link GeoLocation#boundingCoordinates(double, double)}
     *
     * <p>
     * Exemplo de como seria o resultado final:
     *
     * <pre>
     * (radians(latitude) >= -0.3078396187175743 AND radians(latitude) <= -0.30470039903341123)
     *    AND
     * (radians(longitude) >= -0.8128445354586982 AND radians(longitude) <= -0.8095521016055605)
     *    AND
     * acos(sin(-0.30627000887549277) * sin(radians(latitude)) + cos(-0.30627000887549277) * cos(radians(latitude)) * cos(radians(longitude) - (-0.8111983185321293))) <= 0.0015696098420815538
     * </pre>
     *
     * </p>
     *
     * Em que:
     * <ul>
     * <li><b>latitude</b>: nome da coluna que contém o valor da latitude na base de dados</li>
     * <li><b>longitude</b>: nome da coluna que contém o valor da longitude na base de dados</li>
     * <li><b>-0.3078396187175743</b>: latitude minima para filtro</li>
     * <li><b>-0.30470039903341123</b>: latitude máxima para filtro</li>
     * <li><b>-0.8128445354586982</b>: longitude minima para filtro</li>
     * <li><b>-0.8095521016055605</b>: longitude máxima para filtro</li>
     * <li><b>-0.30627000887549277</b>: latitude do ponto de referência</li>
     * <li><b>-0.8111983185321293</b>: longitude do ponto de referência</li>
     * <li><b>0.0015696098420815538</b>: relação distância raio (10 / 6371.01)</li>
     * </ul>
     * </p>
     *
     * <p>
     * Exemplo de uso do trecho de query gerado:
     *
     * <pre>
     * final double distance = 10; // in kilometers
     * final double radius = GeoUtils.EARTH_RADIUS; // in kilometers
     * final GeoLocation referencePoint = GeoLocation.fromDegrees(-17.547978900000000, -46.478240000000000);
     * final GeoLocation[] bounds = location.boundingCoordinates(distance, radius);
     * 
     * final boolean meridian180WithinDistance = bounds[0].getLongitudeInRadians() &gt; bounds[1].getLongitudeInRadians();
     * 
     * final java.lang.StringBuilder query = new java.lang.StringBuilder(&quot;select * from endereco where &quot;);
     * query.append(GeoUtils.getSQLWherePieceForDistanceWithIndex(&quot;latitude&quot;, &quot;longitude&quot;, meridian180WithinDistance));
     * java.sql.PreparedStatement ps = connection.prepareStatement(query.toString());
     * ps.setDouble(1, bounds[0].getLatitudeInRadians());
     * ps.setDouble(2, bounds[1].getLatitudeInRadians());
     * ps.setDouble(3, bounds[0].getLongitudeInRadians());
     * ps.setDouble(4, bounds[1].getLongitudeInRadians());
     * ps.setDouble(5, referencePoint.getLatitudeInRadians());
     * ps.setDouble(6, referencePoint.getLatitudeInRadians());
     * ps.setDouble(7, referencePoint.getLongitudeInRadians());
     * ps.setDouble(8, distance / radius);
     * return ps.executeQuery();
     * </pre>
     *
     * </p>
     *
     * @param latitudeColumnName
     *            nome da coluna que armazena a informação de latitude do ponto geográfico
     * @param longitudeColumnName
     *            nome da coluna que armazena a informação de longitude do ponto geográfico
     * @param meridian180WithinDistance
     * @return query a ser adicionada à cláusula query, com os statements em seus devidos lugares
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/10/2014
     * @see GeoLocation#boundingCoordinates(double, double) para calcular os limites
     */
    public static String getSQLWherePieceForDistanceWithIndex(final String latitudeColumnName, final String longitudeColumnName, final boolean meridian180WithinDistance) {
        Assert.notNullAndNotEmpty(latitudeColumnName, LATITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        Assert.notNullAndNotEmpty(longitudeColumnName, LONGITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        return String.format(DISTANCE_WHERE_QUERY_WITH_INDEX, latitudeColumnName, latitudeColumnName, longitudeColumnName, meridian180WithinDistance ? "OR" : "AND",
                longitudeColumnName, latitudeColumnName, latitudeColumnName, longitudeColumnName);
    }

    /**
     * Recupera um trecho de query SQL para MySQL e MS SQL Server, a ser adicionado à cláusula {@code WHERE} final a ser executada. A query filtra por faixas, então os limites
     * devem ser calculados antes.
     * Veja {@link GeoLocation#boundingCoordinates(double, double)}
     *
     * <p>
     * Exemplo de como seria o resultado final:
     *
     * <pre>
     * (latitude_radians >= -0.3078396187175743 AND latitude_radians <= -0.30470039903341123)
     *    AND
     * (longitude_radians >= -0.8128445354586982 AND longitude_radians <= -0.8095521016055605)
     *    AND
     * acos(sin(-0.30627000887549277) * sin(latitude_radians) + cos(-0.30627000887549277) * cos(latitude_radians) * cos(longitude_radians - (-0.8111983185321293))) <= 0.0015696098420815538
     * </pre>
     *
     * </p>
     *
     * Em que:
     * <ul>
     * <li><b>latitude</b>: nome da coluna que contém o valor da latitude na base de dados</li>
     * <li><b>longitude</b>: nome da coluna que contém o valor da longitude na base de dados</li>
     * <li><b>-0.3078396187175743</b>: latitude minima para filtro</li>
     * <li><b>-0.30470039903341123</b>: latitude máxima para filtro</li>
     * <li><b>-0.8128445354586982</b>: longitude minima para filtro</li>
     * <li><b>-0.8095521016055605</b>: longitude máxima para filtro</li>
     * <li><b>-0.30627000887549277</b>: latitude do ponto de referência</li>
     * <li><b>-0.8111983185321293</b>: longitude do ponto de referência</li>
     * <li><b>0.0015696098420815538</b>: relação distância raio (10 / 6371.01)</li>
     * </ul>
     * </p>
     *
     * <p>
     * Exemplo de uso do trecho de query gerado:
     *
     * <pre>
     * final double distance = 10; // in kilometers
     * final double radius = GeoUtils.EARTH_RADIUS; // in kilometers
     * final GeoLocation referencePoint = GeoLocation.fromDegrees(-17.547978900000000, -46.478240000000000);
     * final GeoLocation[] bounds = location.boundingCoordinates(distance, radius);
     * 
     * final boolean meridian180WithinDistance = bounds[0].getLongitudeInRadians() &gt; bounds[1].getLongitudeInRadians();
     * 
     * final java.lang.StringBuilder query = new java.lang.StringBuilder(&quot;select * from endereco where &quot;);
     * query.append(GeoUtils.getSQLWherePieceForDistanceWithIndexForMySQLAndMSSQLServer(&quot;latitude&quot;, &quot;longitude&quot;, meridian180WithinDistance));
     * java.sql.PreparedStatement ps = connection.prepareStatement(query.toString());
     * ps.setDouble(1, bounds[0].getLatitudeInRadians());
     * ps.setDouble(2, bounds[1].getLatitudeInRadians());
     * ps.setDouble(3, bounds[0].getLongitudeInRadians());
     * ps.setDouble(4, bounds[1].getLongitudeInRadians());
     * ps.setDouble(5, referencePoint.getLatitudeInRadians());
     * ps.setDouble(6, referencePoint.getLatitudeInRadians());
     * ps.setDouble(7, referencePoint.getLongitudeInRadians());
     * ps.setDouble(8, distance / radius);
     * return ps.executeQuery();
     * </pre>
     *
     * </p>
     *
     * @param latitudeColumnName
     *            nome da coluna que armazena a informação de latitude do ponto geográfico
     * @param longitudeColumnName
     *            nome da coluna que armazena a informação de longitude do ponto geográfico
     * @param meridian180WithinDistance
     * @return query a ser adicionada à cláusula query, com os statements em seus devidos lugares
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 27/10/2014
     * @see GeoLocation#boundingCoordinates(double, double) para calcular os limites
     */
    public static String getSQLWherePieceForDistanceWithIndexForMySQLAndMSSQLServer(final String latitudeColumnName, final String longitudeColumnName,
            final boolean meridian180WithinDistance) {
        Assert.notNullAndNotEmpty(latitudeColumnName, LATITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        Assert.notNullAndNotEmpty(longitudeColumnName, LONGITUDE_FIELD_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
        return String.format(DISTANCE_WHERE_QUERY_WITH_INDEX_FOR_MYSQL_MSSQLSERVER, latitudeColumnName, latitudeColumnName, longitudeColumnName, meridian180WithinDistance ? "OR"
                : "AND", longitudeColumnName, latitudeColumnName, latitudeColumnName, longitudeColumnName);
    }

}
