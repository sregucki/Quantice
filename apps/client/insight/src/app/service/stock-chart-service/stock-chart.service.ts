import {Injectable} from '@angular/core';
import {StockChart} from "angular-highcharts";

@Injectable({
  providedIn: 'root'
})
export class StockChartService {

  chart: StockChart;

  constructor() {
  }

  getLineChart(ticker: string, dataAll: any, dataClose: any): StockChart {
    this.chart = new StockChart({
      chart: {
        backgroundColor: 'white'
      },
      rangeSelector: {
        selected: 1
      },
      series: [{
        id: 'line',
        type: 'line',
        tooltip: {
          valueDecimals: 2
        },
        color:
          '#3677a8',
        name: ticker.toUpperCase(),
        data: dataClose
      },
      {
        id: 'candlestick',
        type: 'candlestick',
        tooltip: {
          valueDecimals: 2
        },
        color: '#3677a8',
        name: ticker.toUpperCase(),
        data: dataAll,
        visible: false
      }]
    })
    return this.chart;
  }

  switchToCandle() {
    this.chart.ref$.subscribe(ch => {
      ch.series[0].setVisible(false);
      ch.series[1].setVisible(true);
    });
  }

  switchToLine() {
    this.chart.ref$.subscribe(ch => {
      ch.series[1].setVisible(false);
      ch.series[0].setVisible(true);
    })
  }

}
