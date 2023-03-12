import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {StockComponent} from "./component/stock/stock.component";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'stock/:name', component: StockComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
