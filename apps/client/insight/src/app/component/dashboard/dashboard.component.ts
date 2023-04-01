import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {map, Observable, startWith} from "rxjs";
import {FormControl} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import data from '../../../assets/data/stocks.json'
import {Router} from "@angular/router";
import {StockService} from "../../service/stock-service/stock.service";
import {Headline, HeadlineService} from "../../service/headline-service/headline.service";
import {StockIndex} from "../../model/stockIndex/stockIndex";
import {StockChartService} from "../../service/stock-chart-service/stock-chart.service";
import {StockChart} from "angular-highcharts";

export interface Stock {
  id: string;
  name: string;
  short_name: string;
  country: string;
  sector: string;
  exch: string;
  ccy: string;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @ViewChild('headline-major-title') containerRef: ElementRef;

  stateCtrl = new FormControl();
  filteredStocks: Observable<Stock[]>;
  stocks: Stock[] = data as Stock[];
  indexShowcaseTickers: string[] = ['META', 'AMZN', 'AAPL', 'NFLX', "GOOGL"];
  indexShowcaseList: StockIndex[] = [];
  indexPriceChart: StockChart;
  headlines: Headline[];

  constructor(private http: HttpClient, private router: Router, private stockService: StockService, private headlineService: HeadlineService, private stockChartService: StockChartService) {
    this.filteredStocks = this.stateCtrl.valueChanges.pipe(
      startWith(''),
      map(state => (state ? this._filterStates(state) : this.stocks.slice())),
    );
    this.stockService.getStockData(this.indexShowcaseTickers[0], '1d', '1m').subscribe(closePrice => {
      const stockDataClose = closePrice['data' as keyof typeof closePrice]['Close' as keyof typeof closePrice];
      const stockDataCloseFormatted = [];
      for (let [date, close] of Object.entries(stockDataClose)) {
        stockDataCloseFormatted.push([Date.parse(date), close])
      }
      this.indexPriceChart = this.stockChartService.getLineChart(this.indexShowcaseTickers[0], stockDataCloseFormatted);
    });
  }

  ngOnInit(): void {
    this.indexShowcaseTickers.forEach(ticker => {
      this.stockService.getStockData(ticker, '1mo', '1d').subscribe(stockData => {
        const closePrice = Object.values(stockData['data' as keyof typeof stockData]['Close' as keyof typeof stockData]);
        let index: StockIndex = new StockIndex(ticker, closePrice[closePrice.length - 1] as number, closePrice[closePrice.length - 2] as number);
        this.indexShowcaseList.push(index);
      })
    })
    this.headlineService.getHeadlines().subscribe(v => {
      this.headlines = v as Headline[];
    })
  }

  isDown(stockIndex: StockIndex): boolean {
    return this.getPriceDiff(stockIndex) > 0;
  }

  getPriceDiff(stockIndex: StockIndex): number {
    return stockIndex.closePriceCurrent - stockIndex.closePriceBefore;
  }

  getPriceDiffPercent(stockIndex: StockIndex): number {
    return (1 - (stockIndex.closePriceBefore / stockIndex.closePriceCurrent)) * 100;
  }

  private _filterStates(value: string): Stock[] {
    const filterValue = value.toLowerCase();

    return this.stocks.filter(stock => stock.name.toLowerCase().includes(filterValue));
  }

  updateIndexPriceChart(ticker: string): void {
    this.stockService.getStockData(ticker, '1d', '1m').subscribe(closePriceData => {
      this.indexPriceChart.ref$.subscribe(ch => {
        ch.update({
          title: {
            text: `<span style='vertical-align: bottom; margin-right: 3px'>${ticker}</span>` +
              `<img src='../../../assets/dashboard/${ticker}_logo.png' width='16' height='16' style='display: inline-block; vertical-align: top; margin-bottom: 2px'/>`
          }
        })
        ch.series[0].update({
          type: 'line',
          tooltip: {
            valueDecimals: 2
          },
          color: '#3677a8',
          name: ticker,
          data: this.stockService.getStockDataFormatted(closePriceData, 'Close')
        });
      })
    })
  }


  navigateTo(stockName: string, ticker: string) {
    ticker = ticker.toLowerCase();
    this.router.navigate(['/stock', ticker],
      {
        state: {
          stockName: stockName,
          ticker: ticker
        }
      }).then(
      success => {
        console.log('Successfully routed to: ' + ticker + ' page');
      },
      err => {
        console.log('Error while routing to: ' + ticker);
        console.log('Error message:\n' + err);
      });
  }


}
