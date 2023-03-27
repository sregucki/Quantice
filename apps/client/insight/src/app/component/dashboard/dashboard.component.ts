import {Component} from '@angular/core';
import {map, Observable, startWith} from "rxjs";
import {FormControl} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import data from '../../../assets/data/stocks.json'
import {Router} from "@angular/router";

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
export class DashboardComponent  {

  stateCtrl = new FormControl();
  filteredStocks: Observable<Stock[]>;
  stocks: Stock[] = data as Stock[];

  constructor(private http: HttpClient, private router: Router) {
    this.filteredStocks = this.stateCtrl.valueChanges.pipe(
      startWith(''),
      map(state => (state ? this._filterStates(state) : this.stocks.slice())),
    );
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

  test() {
    console.log('test')
  }

}
