import {Injectable} from '@angular/core';
import {StockChart} from "angular-highcharts";

@Injectable({
  providedIn: 'root'
})
export class StockChartService {

  chart: StockChart;
  dataAll: any;
  dataClose: any;
  dataVolume: any;
  ticker: string;
  constructor() {
  }

  getLineChart(ticker: string, dataClose: any): StockChart {
    return new StockChart({
      chart: {
        backgroundColor: 'white'
      },
      rangeSelector: {
        enabled: false
      },
      navigator: {
        enabled: false
      },
      scrollbar: {
        enabled: false
      },
      title: {
        useHTML: true,
        text: `<span style='vertical-align: bottom; margin-right: 3px'>${ticker}</span>` +
          `<img src='../../../assets/dashboard/${ticker}_logo.png' width='16' height='16' style='display: inline-block; vertical-align: top; margin-bottom: 2px'/>`,
        style: {
          fontSize: '14px'
        }
      },
      yAxis: {
        labels: {
          format: '{value}$'
        }
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
      }]
    });
  }

  getChartConsolidated(ticker: string, dataAll: any, dataClose: any, dataVolume: any): StockChart {

    this.ticker = ticker
    this.dataAll = dataAll;
    this.dataClose = dataClose;
    this.dataVolume = dataVolume;

    this.chart = new StockChart({
      chart: {
        backgroundColor: 'white'
      },
      rangeSelector: {
        selected: 0
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
      }]
    })
    return this.chart;
  }

  switchToCandle() {
    this.chart.ref$.subscribe(ch => {
      ch.series[0].update({
        id: 'candlestick',
        type: 'candlestick',
        tooltip: {
          valueDecimals: 2
        },
        color: '#3677a8',
        name: this.ticker.toUpperCase(),
        data: this.dataAll,
      });
    });
  }

  switchToLine() {
    this.chart.ref$.subscribe(ch => {
      ch.series[0].update({
        id: 'line',
        type: 'line',
        tooltip: {
          valueDecimals: 2
        },
        color:
          '#3677a8',
        name: this.ticker.toUpperCase(),
        data: this.dataClose
      });
    });
  }

}
