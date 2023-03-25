import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {StockComponent} from "./component/stock/stock.component";
import {AuthService} from "./service/auth-service/auth.service";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthService] },
  { path: 'stock/:name', component: StockComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
