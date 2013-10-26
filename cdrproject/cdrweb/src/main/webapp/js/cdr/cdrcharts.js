
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
