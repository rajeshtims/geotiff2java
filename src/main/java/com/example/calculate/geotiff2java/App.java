package com.example.calculate.geotiff2java;

import java.awt.image.Raster;
import java.io.File;

import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class App {

  public static void main( String[] args ) throws Exception {
    
    // ref: http://www.smartjava.org/content/access-information-geotiff-using-java/

    File tiffFile = new File("tiff_files/moisture_data.tif");
    GeoTiffReader reader = new GeoTiffReader(tiffFile);
    GridCoverage2D cov = reader.read(null);
    Raster tiffRaster = cov.getRenderedImage().getData();

    // convert lat/lon gps coordinates to tiff x/y coordinates
    double lat = 37.75497;
    double lng = -122.44580;
    CoordinateReferenceSystem wgs84 = DefaultGeographicCRS.WGS84;
    GridGeometry2D gg = cov.getGridGeometry();
    DirectPosition2D posWorld = new DirectPosition2D(wgs84, lng, lat); // longitude supplied first
    GridCoordinates2D posGrid = gg.worldToGrid(posWorld);

    // sample tiff data with at pixel coordinate
    double[] rasterData = new double[1];
    tiffRaster.getPixel(posGrid.x, posGrid.y, rasterData);

    System.out.println(String.format("GeoTIFF data at %s, %s: %s", lat, lng, rasterData[0]));
  }

}
