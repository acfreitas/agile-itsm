package br.com.citframework.util.geo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link GeoUtils}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 22/09/2014
 *
 */
public final class GeoUtilsTest {

    private static final String LATITUDE_FIELD_NAME = "latitude";
    private static final String LONGITUDE_FIELD_NAME = "longitude";
    private static final String EXPECTED_DISTANCE_WHERE_QUERY_WITHOUT_INDEX = " acos(sin(?) * sin(radians(latitude)) + cos(?) * cos(radians(latitude)) * cos(radians(longitude) - (?))) * 6371 <= ? ";
    private static final String EXPECTED_DISTANCE_WHERE_QUERY_WITH_INDEX = " (radians(latitude) >= ? AND radians(latitude) <= ?) AND (radians(longitude) >= ? AND radians(longitude) <= ?) AND acos(sin(?) * sin(radians(latitude)) + cos(?) * cos(radians(latitude)) * cos(radians(longitude) - (?))) <= ? ";

    @Test
    public void testWhenLatitudeIsValid() {
        final Boolean result = GeoUtils.validLatitude(-89.5498767D);
        Assert.assertTrue(result);
    }

    @Test
    public void testWhenLatitudeStringIsValid() {
        final Boolean result = GeoUtils.validLatitude("-89.5498767");
        Assert.assertTrue(result);
    }

    @Test
    public void testWhenLatitudeIsInvalid() {
        final Boolean result = GeoUtils.validLatitude(Double.MAX_VALUE);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLatitudeStringIsInvalid() {
        final Boolean result = GeoUtils.validLatitude(new Double(Double.MAX_VALUE).toString());
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLongitudeIsValid() {
        final Boolean result = GeoUtils.validLongitude(-176.5498767D);
        Assert.assertTrue(result);
    }

    @Test
    public void testWhenLongitudeStringIsValid() {
        final Boolean result = GeoUtils.validLongitude("-176.5498767");
        Assert.assertTrue(result);
    }

    @Test
    public void testWhenLongitudeIsInvalid() {
        final Boolean result = GeoUtils.validLongitude(Double.MAX_VALUE);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLongitudeStringIsInvalid() {
        final Boolean result = GeoUtils.validLongitude(new Double(Double.MAX_VALUE).toString());
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenCoordinatesIsValid() {
        final Boolean result = GeoUtils.validCoordinates(246.00000000000000056, 0.0);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenCoordinatesStringIsValid() {
        final Boolean result = GeoUtils.validCoordinates("246.00000000000000056", "0");
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLatitudeAndLongitudeAreValidCoordinates() {
        final Boolean result = GeoUtils.validCoordinates(46.00000000000000056, 90.0);
        Assert.assertTrue(result);
    }

    @Test
    public void testWhenLatitudeIsValidAndLongitudeIsInValidCoordinates() {
        final Boolean result = GeoUtils.validCoordinates(89.0000005620000056, Double.MAX_VALUE);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLatitudeIsInvalidAndLongitudeIsValidCoordinates() {
        final Boolean result = GeoUtils.validCoordinates(Double.MAX_VALUE, 89.0004620000000056);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenLatitudeAndLongitudeAreInvalidCoordinates() {
        final Boolean result = GeoUtils.validCoordinates(Double.MAX_VALUE, Double.MAX_VALUE);
        Assert.assertFalse(result);
    }

    @Test
    public void testWhenCoordinatesStringIsInvalid() {
        final Boolean result = GeoUtils.validCoordinates("246.00008645000000056", "0");
        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenLatitudeIsNull() {
        GeoUtils.validLatitude((Double) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenLongitudeIsNull() {
        GeoUtils.validLongitude((Double) null);
    }

    private final String INVALID = "This is invalid!!!";

    @Test(expected = NumberFormatException.class)
    public void testWhenLatitudeStringFormatIsNotValid() {
        GeoUtils.validLatitude(INVALID);
    }

    @Test(expected = NumberFormatException.class)
    public void testWhenLongitudeStringFormatIsNotValid() {
        GeoUtils.validLongitude(INVALID);
    }

    @Test
    public void testGetSQLWherePieceForDistanceWithoutIndex() {
        final String result = GeoUtils.getSQLWherePieceForDistanceWithoutIndex(LATITUDE_FIELD_NAME, LONGITUDE_FIELD_NAME);
        Assert.assertEquals(EXPECTED_DISTANCE_WHERE_QUERY_WITHOUT_INDEX, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithoutIndexWithEmptyLatitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithoutIndex("", LONGITUDE_FIELD_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithoutIndexWithNullLatitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithoutIndex(null, LONGITUDE_FIELD_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithoutIndexWithEmptyLongitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithoutIndex(LATITUDE_FIELD_NAME, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithoutIndexWithNullLongitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithoutIndex(LATITUDE_FIELD_NAME, null);
    }

    @Test
    public void testGetSQLWherePieceForDistanceWithIndex() {
        final String result = GeoUtils.getSQLWherePieceForDistanceWithIndex(LATITUDE_FIELD_NAME, LONGITUDE_FIELD_NAME, false);
        Assert.assertEquals(EXPECTED_DISTANCE_WHERE_QUERY_WITH_INDEX, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithIndexWithEmptyLatitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithIndex("", LONGITUDE_FIELD_NAME, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithIndexWithNullLatitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithIndex(null, LONGITUDE_FIELD_NAME, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithIndexWithEmptyLongitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithIndex(LATITUDE_FIELD_NAME, "", false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSQLWherePieceForDistanceWithIndexWithNullLongitudeFieldName() {
        GeoUtils.getSQLWherePieceForDistanceWithIndex(LATITUDE_FIELD_NAME, null, false);
    }

}
