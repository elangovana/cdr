
function createChart (urlId, chartSectionId, bannerText, seriesNameText ) {

    $.getJSON(

    $(urlId).html(),

    function (data) {

        var highChartData = [];
        $.each(
        data,

        function (
        index,
        itemData) {
            var subarray = [];
            $.each(
            this,

            function (
            k,
            v) {
                subarray.push(v);
            });
            highChartData.push(subarray);
        }

        );
        $(
        	chartSectionId)
            .highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: bannerText
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            },
            series: [{
                type: 'pie',
                name: seriesNameText,
                data: highChartData
            }]
        });

    });

    return false;

}



function createBarChart (urlId, chartSectionId, bannerText, seriesNameText , yNameText) {

    $.getJSON(

    $(urlId).html(),

    function (data) {

        var highChartCategoryData = [];
        var highChartSeriesData = [];
        $.each(
        data,

        function (
        index,
        itemData) {
        	 i=0;
        	 $.each( this, function ( k,v) {
        		 if (i==0) highChartCategoryData.push(v);
        		 if (i==1) 	highChartSeriesData.push(v);
        		 i++;
        	                
        	            });
       
        }

        );
        $(chartSectionId)
            .highcharts({
                chart: {
                    type: 'bar'
                },
                title: {
                    text: bannerText
                },
                subtitle: {
                    text: 'Source: CDR '
                },
                xAxis: {
                    categories: highChartCategoryData,
                    title: {
                        text: null
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: yNameText,
                        align: 'high'
                    },
                    labels: {
                        overflow: 'justify'
                    }
                },
                tooltip: {
                    valueSuffix: ''
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'top',
                    x: -40,
                    y: 100,
                    floating: true,
                    borderWidth: 1,
                    backgroundColor: '#FFFFFF',
                    shadow: true
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: seriesNameText,
                    data: highChartSeriesData
                }]
            });

    });

    return false;

}