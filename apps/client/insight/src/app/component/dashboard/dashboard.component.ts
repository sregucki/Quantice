import {Component, NgZone, OnInit} from '@angular/core';
import {map, Observable, startWith} from "rxjs";
import {FormControl} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import data from '../../../assets/data/stocks.json'
import {Router} from "@angular/router";
import {StockService} from "../../service/stock-service/stock.service";

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

  stateCtrl = new FormControl();
  filteredStocks: Observable<Stock[]>;
  stocks: Stock[] = data as Stock[];
  indexShowcaseTickers: string[] = ['META', 'AMZN', 'AAPL', 'NFLX', "GOOGL", "NVDA", "TSLA", "XOM", "JNJ",
  "JPM", "WMT", "CSCO", "PM", "AMD", "ADBE", "ORCL", "UPS"];
  indexShowcasePriceData: Map<string, any> = new Map<string, any>();

  constructor(private http: HttpClient, private router: Router, private stockService: StockService, private ngZone: NgZone) {
    this.filteredStocks = this.stateCtrl.valueChanges.pipe(
      startWith(''),
      map(state => (state ? this._filterStates(state) : this.stocks.slice())),
    );


  }

  ngOnInit(): void {
    this.indexShowcaseTickers.forEach(ticker => {
      this.stockService.getStockData(ticker, '1mo', '1d').subscribe(stockData => {
        const closePrice = Object.values(stockData['data' as keyof typeof stockData]['Close' as keyof typeof stockData]);
        this.indexShowcasePriceData.set(ticker, [closePrice[closePrice.length - 1], closePrice[closePrice.length - 2]]);
      })
    })
  }

  getIndexData(ticker: string): number[] {
    return this.indexShowcasePriceData.get(ticker);
  }

  getPriceDiff(price1: any, price2: any): number {
    return Number(price1) - Number(price2);
  }

  getPriceDiffPercent(price1: any, price2: any): number {
    return (1 - (Number(price2) / Number(price1))) * 100;
  }

  private _filterStates(value: string): Stock[] {
    const filterValue = value.toLowerCase();

    return this.stocks.filter(stock => stock.name.toLowerCase().includes(filterValue));
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
