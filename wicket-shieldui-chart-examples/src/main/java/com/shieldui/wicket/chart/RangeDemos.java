package com.shieldui.wicket.chart;

import com.shieldui.wicket.chart.events.PointSelectEventListener;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;

public class RangeDemos extends WebPage
{
    private static final long serialVersionUID = 1L;
    
    public RangeDemos()
    {
        // add the menu
        add(new MenuPanel("menu"));
        
        // add the two new charts
        final Chart rangeBar = new Chart("rangeBar");
        final Chart rangeSplineArea = new Chart("rangeSplineArea");
        add(rangeBar);
        add(rangeSplineArea);
        
        // setup the rangeBar properties
        rangeBar.getOptions().setTheme(Options.Theme.DARK);
        rangeBar.getOptions().setSeriesType(Options.SeriesType.RANGE_BAR);
        rangeBar.getOptions().getTooltipSettings().setCustomPointText("Low Value: <b>{point.low}</b></br>High Value:<b>{point.high}");
        rangeBar.getOptions().getAxisY().getTitle().setText("Quarter Overview");
        rangeBar.getOptions().getAxisX().setCategoricalValues(Arrays.asList(new String[]{"Q1", "Q2", "Q3", "Q4"}));
        rangeBar.getOptions().getPrimaryHeader().setText("Quarterly Performance");
        rangeBar.getOptions().getExportOptions().setImage(false).setPrint(false);
        rangeBar.getOptions().getSeriesSettings().getRangebar().setEnablePointSelection(true);
        
        // add some data to rangeBar
        Options.DataSeriesItem dataSeriesItem = new Options.DataSeriesItem();
        dataSeriesItem.setData(Arrays.asList(new Object[]{new Object[]{3, 9}, new Object[]{12, 23}, new Object[]{1, 17}, new Object[]{-3, 12}}));
        rangeBar.getOptions().setDataSeries(Arrays.asList(dataSeriesItem));
        
        // handle the pointSelect event for the rangeBar chart
        rangeBar.add(new PointSelectEventListener() {
            @Override
            protected void handleEvent(AjaxRequestTarget target, Object event) {
                HashMap<String, Object> point = (HashMap<String, Object>) ((HashMap<String, Object>) event).get("point");
                int pointIndex = (Integer) point.get("x");
                Object[] data = null;
                
                switch (pointIndex)
                {
                    case 0:
                        data = new Object[]{new Object[]{3, 6}, new Object[]{4, 6}, new Object[]{5, 9}};
                        rangeSplineArea.getOptions().getAxisX().setCategoricalValues(Arrays.asList(new String[]{"Jan", "Feb", "Mar"}));
                        break;
                    case 1:
                        data = new Object[]{new Object[]{12, 6}, new Object[]{14, 23}, new Object[]{17, 20}};
                        rangeSplineArea.getOptions().getAxisX().setCategoricalValues(Arrays.asList(new String[]{"Apr", "May", "Jun"}));
                        break;
                    case 2:
                        data = new Object[]{new Object[]{1, 6}, new Object[]{8, 17}, new Object[]{3, 10}};
                        rangeSplineArea.getOptions().getAxisX().setCategoricalValues(Arrays.asList(new String[]{"Jul", "Aug", "Sep"}));
                        break;
                    case 3:
                        data = new Object[]{new Object[]{4, 8}, new Object[]{0, 12}, new Object[]{-3, 10}};
                        rangeSplineArea.getOptions().getAxisX().setCategoricalValues(Arrays.asList(new String[]{"Oct", "Nov", "Dec"}));
                        break;
                    default:
                        break;
                }
                
                Options.DataSeriesItem dsi = new Options.DataSeriesItem();
                dsi.setData(Arrays.asList(data));
                rangeSplineArea.getOptions().setDataSeries(Arrays.asList(dsi));
                
                rangeSplineArea.getOptions().getPrimaryHeader().setText(point.get("name").toString());
                
                rangeSplineArea.reInitialize(target);
            }
        });
        
        // setup the rangeSplineArea properties;
        // the other data-specific properties will be updated 
        // in the pointSelect event handler above
        rangeSplineArea.getOptions().setTheme(Options.Theme.DARK);
        rangeSplineArea.getOptions().setSeriesType(Options.SeriesType.RANGE_SPLINE_AREA);
    }
}